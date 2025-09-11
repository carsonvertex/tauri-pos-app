import { useState, useEffect, useRef } from 'react';
import { invoke } from '@tauri-apps/api';
import type { BackendStatus } from '../types';

export const useTauri = () => {
  const [backendStatus, setBackendStatus] = useState<BackendStatus>({ running: false });
  const [tauriAvailable, setTauriAvailable] = useState(false);
  const [isOnline, setIsOnline] = useState(navigator.onLine);
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
    isRestarting,
    startBackend,
    stopBackend,
    restartBackend,
    checkBackendStatus,
    manualReconnect
  };
};
