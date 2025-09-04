import { useState, useEffect } from 'react';
import type { BackendStatus } from '../types';

export const useTauri = () => {
  const [backendStatus, setBackendStatus] = useState<BackendStatus>({ running: false });
  const [tauriAvailable, setTauriAvailable] = useState(false);

  useEffect(() => {
    // Check if Tauri is available
    if (typeof window !== 'undefined' && window.__TAURI__) {
      setTauriAvailable(true);
    }
    
    // Check backend status when component mounts
    checkBackendStatus();
    
    // Set up periodic status check every 5 seconds
    const interval = setInterval(checkBackendStatus, 5000);
    
    return () => clearInterval(interval);
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

  return {
    backendStatus,
    tauriAvailable,
    startBackend,
    stopBackend,
    checkBackendStatus
  };
};
