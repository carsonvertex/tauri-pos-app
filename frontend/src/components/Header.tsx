import React from "react";
import { BackendStatus, SyncStatusSummary } from "../types";
import { OfflineStatus } from "./OfflineStatus";
import { useAuth } from "../contexts/AuthContext";
import {
  Logout,
  Refresh,
  Warning,
  CheckCircle,
  Error,
  Sync,
} from "@mui/icons-material";
import { Chip } from "@mui/material";

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
  const { user, logout } = useAuth();

  const handleLogout = () => {
    logout();
    window.location.href = "/login";
  };

  return (
    <header className="bg-white shadow-lg border-b border-gray-200 px-4 py-12">
      <div className="grid grid-cols-12 px-12 bg-zinc-500">
        {/* Left Section - App Title */}
        <div className="col-span-4 mx-4">
          <div className="flex items-center space-x-2">
            <div className="w-8 h-8 bg-gradient-to-br from-blue-500 to-purple-600 rounded-lg flex items-center justify-center">
              <span className="text-white font-bold text-lg">🛒</span>
            </div>
            <h1 className="text-xl font-bold text-gray-800">Tauri POS</h1>
          </div>
        </div>

        {/* Center Section - Status Indicators */}
        <div className="col-span-4 flex gap-2 justify-center">
          {/* Backend Status */}
          <div className="flex items-center space-x-2">
            <div
              className={`flex items-center space-x-1 px-3 py-1 rounded-full text-sm font-medium ${
                backendStatus.running
                  ? "bg-green-100 text-green-800"
                  : "bg-red-100 text-red-800"
              }`}
            >
              {backendStatus.running ? (
                <CheckCircle className="w-4 h-4" />
              ) : (
                <Error className="w-4 h-4" />
              )}
              <span>
                Backend: {backendStatus.running ? "Running" : "Stopped"}
              </span>
              {backendStatus.port && (
                <span className="text-xs opacity-75">
                  ({backendStatus.port})
                </span>
              )}
            </div>

            {/* Reconnect/Restart Button */}
            {tauriAvailable ? (
              <button
                onClick={() => {
                  console.log("Restart button clicked!");
                  onManualReconnect();
                }}
                disabled={isRestarting}
                className={`flex items-center space-x-1 px-3 py-1 rounded-lg text-sm font-medium transition-colors ${
                  isRestarting
                    ? "bg-gray-100 text-gray-500 cursor-not-allowed"
                    : "bg-blue-100 text-blue-700 hover:bg-blue-200"
                }`}
                title="Restart backend server"
              >
                <Refresh
                  className={`w-4 h-4 ${isRestarting ? "animate-spin" : ""}`}
                />
                <span>{isRestarting ? "Restarting..." : "Restart"}</span>
              </button>
            ) : (
              !backendStatus.running && (
                <button
                  onClick={() => {
                    console.log("Reconnect button clicked!");
                    onManualReconnect();
                  }}
                  className="flex items-center space-x-1 px-3 py-1 rounded-lg text-sm font-medium bg-orange-100 text-orange-700 hover:bg-orange-200 transition-colors"
                  title="Check backend connection"
                >
                  <Refresh className="w-4 h-4" />
                  <span>Reconnect</span>
                </button>
              )
            )}

            {/* Browser Mode Warning */}
            {!tauriAvailable && (
              <div className="flex items-center space-x-1 px-3 py-1 rounded-lg bg-yellow-100 text-yellow-800 text-sm font-medium">
                <Warning className="w-4 h-4" />
                <span>Browser Mode</span>
              </div>
            )}
          </div>

          {/* Sync Status */}
          <div className="flex items-center space-x-2">
            <OfflineStatus
              isOnline={isOnline}
              syncStatus={syncStatus}
              onForceSync={onForceSync}
            />
            {/* Manual Sync button when backend is running and online */}
            {backendStatus.running && isOnline && (
              <button
                onClick={onManualSync}
                className="flex items-center space-x-1 px-3 py-1 rounded-lg text-sm font-medium bg-green-100 text-green-700 hover:bg-green-200 transition-colors"
                title="Manually sync data"
              >
                <Sync className="w-4 h-4" />
                <span>Sync</span>
              </button>
            )}
          </div>
        </div>

        {/* Right Section - User Info and Logout */}
        <div className="col-span-4 flex justify-end">
          {user && (
            <div className="flex items-center space-x-3">
              <div className="flex items-center space-x-2 px-3 py-1 bg-gray-100 rounded-lg">
                <div className="w-6 h-6 bg-gradient-to-br from-purple-500 to-pink-500 rounded-full flex items-center justify-center">
                  <span className="text-white text-xs font-bold">
                    {user.username.charAt(0).toUpperCase()}
                  </span>
                </div>
                <div className="flex flex-col">
                  <span className="text-sm font-medium text-gray-800">
                    {user.username}
                  </span>
                  <span className="text-xs text-gray-500 capitalize">
                    {user.permission}
                  </span>
                </div>
              </div>
              <button
                onClick={handleLogout}
                className="flex items-center space-x-1 px-3 py-1 rounded-lg text-sm font-medium bg-red-100 text-red-700 hover:bg-red-200 transition-colors"
                title="Logout"
              >
                <Logout className="w-4 h-4" />
                <span>Logout</span>
              </button>
            </div>
          )}
        </div>
      </div>
    </header>
  );
};
