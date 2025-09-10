import React from "react";
import { useTauri } from "./hooks/useTauri";
import { Header } from "./components";
import { Dashboard, POS, Products, Orders, Reports } from "./pages";
import "./App.css";
import { BrowserRouter, Route, Routes, useLocation } from "react-router-dom";
import { LoginPage } from "./pages/Login";
import ProtectedRoutes from "./utils/ProtectedRoutes";
import { AuthProvider, useAuth } from "./contexts/AuthContext";
import ProtectedLayout from "./components/ProtectedLayout";
import { Accounts } from "./pages/Accounts";

// Component to conditionally render Header
const ConditionalHeader: React.FC<{
  backendStatus: any;
  tauriAvailable: boolean;
  isOnline: boolean;
  syncStatus: any;
  isRestarting: boolean;
  onForceSync: () => Promise<boolean>;
  onManualSync: () => Promise<boolean>;
  onManualReconnect: () => Promise<void>;
}> = (props) => {
  const location = useLocation();
  const { isAuthenticated } = useAuth();
  
  // Show header only on protected routes (not on login page)
  if (location.pathname === '/login' || !isAuthenticated) {
    return null;
  }
  
  return <Header {...props} />;
};

function App() {
  const {
    backendStatus,
    tauriAvailable,
    isOnline,
    syncStatus,
    isRestarting,
    forceSync,
    manualSync,
    manualReconnect,
  } = useTauri();

  return (
    <AuthProvider>
      <BrowserRouter>
        <div className="app">
          <ConditionalHeader
            backendStatus={backendStatus}
            tauriAvailable={tauriAvailable}
            isOnline={isOnline}
            syncStatus={syncStatus}
            isRestarting={isRestarting}
            onForceSync={forceSync}
            onManualSync={manualSync}
            onManualReconnect={manualReconnect}
          />
          <main className="app-main">
            <Routes>
              <Route path="/login" element={<LoginPage />} />

              <Route element={<ProtectedRoutes />}>
                <Route element={<ProtectedLayout />}>
                  <Route path="/" element={<Dashboard />} />
                  <Route path="/accounts" element={<Accounts />} />
                  <Route path="/dashboard" element={<Dashboard />} />
                  <Route path="/pos" element={<POS />} />
                  <Route path="/products" element={<Products />} />
                  <Route path="/orders" element={<Orders />} />
                  <Route path="/reports" element={<Reports />} />
                </Route>
              </Route>
            </Routes>
          </main>
        </div>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
