import { useState, useEffect, useRef } from 'react';
import { invoke } from '@tauri-apps/api';
import type { BackendStatus } from '../types';

export const useTauri = () => {
  const [backendStatus, setBackendStatus] = useState<BackendStatus>({ running: false });
  const [tauriAvailable, setTauriAvailable] = useState(false);
  const [isOnline, setIsOnline] = useState(navigator.onLine);
  const [syncStatus, setSyncStatus] = useState<any | null>(null);
  const [isRestarting, setIsRestarting] = useState(false);
  const backendStatusRef = useRef<BackendStatus>({ running: false });
  const intervalRef = useRef<number | null>(null);

  useEffect(() => {
    // Check if Tauri is available
    if (typeof window !== 'undefined' && window.__TAURI__) {
      setTauriAvailable(true);
    }
    
    // Set up online/offline listeners
    const handleOnline = () => setIsOnline(true);
    const handleOffline = () => setIsOnline(false);
    
    window.addEventListener('online', handleOnline);
    window.addEventListener('offline', handleOffline);
    
    // Check backend status when component mounts
    checkBackendStatus();
    checkSyncStatus();
    
    return () => {
      if (intervalRef.current) {
        clearInterval(intervalRef.current);
      }
      window.removeEventListener('online', handleOnline);
      window.removeEventListener('offline', handleOffline);
    };
  }, []);

  const checkBackendStatus = async () => {
    try {
      console.log('Checking backend status...');
      // Check backend status by making HTTP request to Spring Boot
      const response = await fetch('http://localhost:8080/api/pos/health', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      
      console.log('Backend health check response:', response.status, response.statusText);
      
      if (response.ok) {
        const newStatus = { running: true, port: 8080 };
        setBackendStatus(newStatus);
        backendStatusRef.current = newStatus;
        console.log('Backend is running on port 8080');
      } else {
        // Try alternative endpoints if health endpoint doesn't exist
        console.log('Health endpoint failed, trying root endpoint...');
        const rootResponse = await fetch('http://localhost:8080/', {
          method: 'GET',
        });
        
        if (rootResponse.ok || rootResponse.status === 404 || rootResponse.status === 403) {
          // Server is running but endpoint doesn't exist or is protected
          const newStatus = { running: true, port: 8080 };
          setBackendStatus(newStatus);
          backendStatusRef.current = newStatus;
          console.log('Backend is running on port 8080 (detected via root endpoint)');
        } else {
          const newStatus = { running: false, port: undefined };
          setBackendStatus(newStatus);
          backendStatusRef.current = newStatus;
          console.log('Backend is not responding');
        }
      }
    } catch (error) {
      console.error('Failed to get backend status:', error);
      // Set default status for development
      const newStatus = { running: false, port: undefined };
      setBackendStatus(newStatus);
      backendStatusRef.current = newStatus;
    }
  };

  const checkSyncStatus = async () => {
    try {
      console.log('Checking sync status...');
      const response = await fetch('http://localhost:8080/api/offline/sync/status', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      
      console.log('Sync status response:', response.status, response.statusText);
      
      if (response.ok) {
        const status = await response.json();
        setSyncStatus(status);
        console.log('Sync status updated:', status);
      } else {
        // If sync endpoint fails, create a default status
        console.log('Sync endpoint not available, using default status');
        setSyncStatus({
          pendingProducts: 0,
          pendingOrders: 0,
          failedProducts: 0,
          failedOrders: 0,
          totalPending: 0,
          totalFailed: 0,
          totalSynced: 0
        });
      }
    } catch (error) {
      console.error('Failed to get sync status:', error);
      // Set default status on error
      setSyncStatus({
        pendingProducts: 0,
        pendingOrders: 0,
        failedProducts: 0,
        failedOrders: 0,
        totalPending: 0,
        totalFailed: 0,
        totalSynced: 0
      });
    }
  };

  const startBackend = async () => {
    try {
      // For now, just check the status since we can't start backend from frontend
      await checkBackendStatus();
    } catch (error) {
      console.error('Failed to start backend:', error);
    }
  };

  const stopBackend = async () => {
    try {
      // For now, just check the status since we can't stop backend from frontend
      await checkBackendStatus();
    } catch (error) {
      console.error('Failed to stop backend:', error);
    }
  };

  const forceSync = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/offline/sync/force', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      
      if (response.ok) {
        await checkSyncStatus();
        return true;
      }
      return false;
    } catch (error) {
      console.error('Failed to force sync:', error);
      return false;
    }
  };

  const manualSync = async () => {
    try {
      // First refresh sync status
      await checkSyncStatus();
      
      // Then perform sync if there are pending items
      if (syncStatus && (syncStatus.totalPending > 0 || syncStatus.totalFailed > 0)) {
        return await forceSync();
      }
      
      return true;
    } catch (error) {
      console.error('Failed to perform manual sync:', error);
      return false;
    }
  };

  const restartBackend = async () => {
    if (!tauriAvailable) {
      console.warn('Tauri not available, cannot restart backend');
      return false;
    }

    console.log('Starting backend restart process...');
    setIsRestarting(true);
    try {
      console.log('Stopping backend...');
      // First stop the backend if it's running
      await invoke('stop_backend');
      
      // Wait a moment for the process to fully stop
      console.log('Waiting for backend to stop...');
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      // Then start it again
      console.log('Starting backend...');
      const result = await invoke('start_backend') as BackendStatus;
      console.log('Backend start result:', result);
      
      // Update the status
      setBackendStatus(result);
      backendStatusRef.current = result;
      
      // Wait a bit for the backend to fully start, then check sync status
      if (result.running) {
        console.log('Backend started successfully, will check sync status in 2 seconds...');
        setTimeout(async () => {
          await checkSyncStatus();
        }, 2000);
      }
      
      return result.running;
    } catch (error) {
      console.error('Failed to restart backend:', error);
      return false;
    } finally {
      setIsRestarting(false);
    }
  };

  const manualReconnect = async () => {
    console.log('Manual reconnect clicked!', { tauriAvailable, backendStatus: backendStatusRef.current });
    
    if (tauriAvailable) {
      console.log('Tauri available, attempting to restart backend...');
      // If Tauri is available, restart the backend
      const success = await restartBackend();
      if (success) {
        console.log('Backend restarted successfully');
      } else {
        console.log('Failed to restart backend, falling back to status check');
        await checkBackendStatus();
      }
    } else {
      console.log('Tauri not available, checking backend status...');
      // Fallback to just checking status if Tauri is not available
      await checkBackendStatus();
    }
    
    // Always check sync status after reconnect attempt
    console.log('Checking sync status after reconnect...');
    await checkSyncStatus();
    
    // Provide user feedback
    if (backendStatusRef.current.running) {
      console.log('✅ Backend connection successful!');
    } else {
      console.log('❌ Backend connection failed. Please check if the server is running.');
    }
  };

  return {
    backendStatus,
    tauriAvailable,
    isOnline,
    syncStatus,
    isRestarting,
    startBackend,
    stopBackend,
    restartBackend,
    checkBackendStatus,
    forceSync,
    manualSync,
    manualReconnect
  };
};
