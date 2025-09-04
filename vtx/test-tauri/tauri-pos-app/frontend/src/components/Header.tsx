import React from 'react';
import { BackendStatus } from '../types';

interface HeaderProps {
  backendStatus: BackendStatus;
  tauriAvailable: boolean;
  onStartBackend: () => void;
  onStopBackend: () => void;
}

export const Header: React.FC<HeaderProps> = ({
  backendStatus,
  tauriAvailable,
  onStartBackend,
  onStopBackend
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
        {tauriAvailable && (
          <div className="backend-controls">
            <button 
              onClick={onStartBackend}
              disabled={backendStatus.running}
              className="btn btn-primary"
            >
              Start Backend
            </button>
            <button 
              onClick={onStopBackend}
              disabled={!backendStatus.running}
              className="btn btn-danger"
            >
              Stop Backend
            </button>
          </div>
        )}
        {!tauriAvailable && (
          <div className="backend-controls">
            <span style={{ color: '#ffa500' }}>⚠️ Running in browser mode</span>
          </div>
        )}
      </div>
    </header>
  );
};
