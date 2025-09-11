import React, { useState } from "react";
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
  Storage,
} from "@mui/icons-material";
import { Button, Chip, Snackbar, Alert } from "@mui/material";
import NavDrawer from "./NavDrawer";

interface HeaderProps {
  backendStatus: BackendStatus;
  tauriAvailable: boolean;
  isOnline: boolean;
  isRestarting: boolean;
  onManualReconnect: () => Promise<void>;
}

export const Header: React.FC<HeaderProps> = ({
  backendStatus,
  tauriAvailable,
  isOnline,
  isRestarting,
  onManualReconnect,
}) => {
  const { user, logout } = useAuth();
  const [initMessage, setInitMessage] = useState<{ type: 'success' | 'error', text: string } | null>(null);

  const handleLogout = () => {
    logout();
    window.location.href = "/login";
  };

  return (
    <header className="bg-white shadow-lg border-b border-gray-200  ">
      <div className="grid grid-cols-12 px-12 bg-amber-500 h-24 flex items-center">
        {/* Left Section - App Title */}
        <div className="col-span-4 mx-4 gap-2 flex items-center space-x-2">
          <NavDrawer />
          <h1 className="text-xl font-bold text-gray-800">RC Mart POS test</h1>
        </div>

        {/* Center Section - Status Indicators */}
        <div className="col-span-4 flex gap-2 justify-center">
          {/* Backend Status */}
          <div
            className={`flex items-center space-x-1 px-3 py-1 rounded-lg text-sm font-medium ${
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
              <span className="text-xs opacity-75">({backendStatus.port})</span>
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

          {/* Sync Status */}
          <OfflineStatus
            isOnline={isOnline}
          />
   

       
        </div>

        {/* Right Section - User Info and Logout */}
        <div className="col-span-4 flex justify-end">
          {user && (
            <div className="flex items-center  gap-2">
              <div className="flex items-center gap-4 px-3 py-1 bg-gray-100 rounded-lg">
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
              
              <Button
                onClick={handleLogout}
                className="flex items-center space-x-1 px-3 py-1 rounded-lg text-sm font-medium bg-red-100 text-red-700 hover:bg-red-200 transition-colors"
                title="Logout"
              >
                <Logout className="w-4 h-4" />
                <span>Logout</span>
              </Button>
            </div>
          )}
        </div>
      </div>

      {/* Snackbar for database initialization messages */}
      <Snackbar
        open={!!initMessage}
        autoHideDuration={6000}
        onClose={() => setInitMessage(null)}
        anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
      >
        <Alert
          onClose={() => setInitMessage(null)}
          severity={initMessage?.type === 'success' ? 'success' : 'error'}
          sx={{ width: '100%' }}
        >
          {initMessage?.text}
        </Alert>
      </Snackbar>
    </header>
  );
};
