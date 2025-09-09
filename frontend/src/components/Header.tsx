import React from "react";
import { BackendStatus, SyncStatusSummary } from "../types";
import { OfflineStatus } from "./OfflineStatus";

interface HeaderProps {
  backendStatus: BackendStatus;
  tauriAvailable: boolean;
  isOnline: boolean;
  syncStatus: SyncStatusSummary | null;
  isRestarting: boolean;
  onForceSync: () => Promise<boolean>;
  onManualSync: () => Promise<boolean>;
  onManualReconnect: () => Promise<void>;
}

export const Header: React.FC<HeaderProps> = ({
  backendStatus,
  tauriAvailable,
  isOnline,
  syncStatus,
  isRestarting,
  onForceSync,
  onManualSync,
  onManualReconnect,
}) => {
  return (
    <header className="app-header">
      <h1>🛒 Tauri POS</h1>
      <div className="backend-status">
        <span
          className={`status-indicator ${
            backendStatus.running ? "running" : "stopped"
          }`}
        >
          {backendStatus.running ? "🟢" : "🔴"}
        </span>
        <span>Backend: {backendStatus.running ? "Running" : "Stopped"}</span>
        {backendStatus.port && <span>(Port: {backendStatus.port})</span>}

        {/* Reconnect/Restart button */}
        {tauriAvailable ? (
          // Always show restart button when Tauri is available
          <button
            className="reconnect-btn"
            onClick={onManualReconnect}
            disabled={isRestarting}
            title="Restart backend server"
          >
            {isRestarting ? "⏳ Restarting..." : "🔄 Restart"}
          </button>
        ) : (
          // Show reconnect button only when backend is down in browser mode
          !backendStatus.running && (
            <button
              className="reconnect-btn"
              onClick={onManualReconnect}
              title="Check backend connection"
            >
              🔄 Reconnect
            </button>
          )
        )}

        {/* Start/Stop buttons hidden for production */}
        {!tauriAvailable && (
          <div className="backend-controls">
            <span style={{ color: "#ffa500" }}>⚠️ Running in browser mode</span>
          </div>
        )}
      </div>

      {/* Offline Status */}
      <div className="offline-status flex flex-row">
        <OfflineStatus
          isOnline={isOnline}
          syncStatus={syncStatus}
          onForceSync={onForceSync}
        />
        {/* Manual Sync button when backend is running and online */}
        {backendStatus.running && isOnline && (
          <button
            className="manual-sync-btn"
            onClick={onManualSync}
            title="Manually sync data"
          >
            🔄 Sync
          </button>
        )}
      </div>
    </header>
  );
};
