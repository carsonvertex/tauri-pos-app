import { useState, useEffect } from 'react';
import type { BackendStatus } from '../types';

export const useTauri = () => {
  const [backendStatus, setBackendStatus] = useState<BackendStatus>({ running: false });
  const [tauriAvailable, setTauriAvailable] = useState(false);

  useEffect(() => {
    // Check if Tauri is available
    if (typeof window !== 'undefined' && window.__TAURI__) {
      setTauriAvailable(true);
      
      // Check backend status when component mounts
      checkBackendStatus();
    } else {
      console.log('Tauri not available, running in browser mode');
      // Set default status for browser mode
      setBackendStatus({ running: false, port: undefined });
    }
  }, []);

  const checkBackendStatus = async () => {
    if (!tauriAvailable) return;
    
    try {
      // Only try to invoke if Tauri is available
      if (typeof window !== 'undefined' && window.__TAURI__) {
        const { invoke } = await import('@tauri-apps/api/tauri');
        const status = await invoke('get_backend_status');
        setBackendStatus(status as BackendStatus);
      }
    } catch (error) {
      console.error('Failed to get backend status:', error);
      // Set default status for development
      setBackendStatus({ running: false, port: undefined });
    }
  };

  const startBackend = async () => {
    if (!tauriAvailable) return;
    
    try {
      if (typeof window !== 'undefined' && window.__TAURI__) {
        const { invoke } = await import('@tauri-apps/api/tauri');
        const status = await invoke('start_backend');
        setBackendStatus(status as BackendStatus);
      }
    } catch (error) {
      console.error('Failed to start backend:', error);
    }
  };

  const stopBackend = async () => {
    if (!tauriAvailable) return;
    
    try {
      if (typeof window !== 'undefined' && window.__TAURI__) {
        const { invoke } = await import('@tauri-apps/api/tauri');
        const status = await invoke('stop_backend');
        setBackendStatus(status as BackendStatus);
      }
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
