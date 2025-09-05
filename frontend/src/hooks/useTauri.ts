import { useState, useEffect } from 'react';
import type { BackendStatus } from '../types';

export const useTauri = () => {
  const [backendStatus, setBackendStatus] = useState<BackendStatus>({ running: false });
  const [tauriAvailable, setTauriAvailable] = useState(false);
  const [isOnline, setIsOnline] = useState(navigator.onLine);
  const [syncStatus, setSyncStatus] = useState<any | null>(null);

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
    
    // Set up periodic status check every 5 seconds
    const interval = setInterval(() => {
      checkBackendStatus();
      checkSyncStatus();
    }, 5000);
    
    return () => {
      clearInterval(interval);
      window.removeEventListener('online', handleOnline);
      window.removeEventListener('offline', handleOffline);
    };
  }, []);

  const checkBackendStatus = async () => {
    try {
      // Check backend status by making HTTP request to Spring Boot
      const response = await fetch('http://localhost:8080/api/pos/health', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      
      if (response.ok) {
        setBackendStatus({ running: true, port: 8080 });
      } else {
        setBackendStatus({ running: false, port: undefined });
      }
    } catch (error) {
      console.error('Failed to get backend status:', error);
      // Set default status for development
      setBackendStatus({ running: false, port: undefined });
    }
  };

  const checkSyncStatus = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/offline/sync/status', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      
      if (response.ok) {
        const status = await response.json();
        setSyncStatus(status);
      }
    } catch (error) {
      console.error('Failed to get sync status:', error);
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

  return {
    backendStatus,
    tauriAvailable,
    isOnline,
    syncStatus,
    startBackend,
    stopBackend,
    checkBackendStatus,
    forceSync
  };
};
