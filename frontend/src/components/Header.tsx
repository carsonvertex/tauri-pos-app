import React from 'react';
import { BackendStatus, SyncStatusSummary } from '../types';
import { OfflineStatus } from './OfflineStatus';

interface HeaderProps {
  backendStatus: BackendStatus;
  tauriAvailable: boolean;
  isOnline: boolean;
  syncStatus: SyncStatusSummary | null;
  onForceSync: () => Promise<boolean>;
}

export const Header: React.FC<HeaderProps> = ({
  backendStatus,
  tauriAvailable,
  isOnline,
  syncStatus,
  onForceSync
}) => {
  return (
    <header className="app-header">
      <h1>🛒 Tauri POS</h1>
      <div className="backend-status">
        <span className={`status-indicator ${backendStatus.running ? 'running' : 'stopped'}`}>
          {backendStatus.running ? '🟢' : '🔴'}
        </span>
        <span>Backend: {backendStatus.running ? 'Running' : 'Stopped'}</span>
        {backendStatus.port && <span>(Port: {backendStatus.port})</span>}
        {/* Start/Stop buttons hidden for production */}
        {!tauriAvailable && (
          <div className="backend-controls">
            <span style={{ color: '#ffa500' }}>⚠️ Running in browser mode</span>
          </div>
        )}
      </div>
      
      {/* Offline Status */}
      <div className="offline-status">
        <OfflineStatus 
          isOnline={isOnline}
          syncStatus={syncStatus}
          onForceSync={onForceSync}
        />
      </div>
    </header>
  );
};
