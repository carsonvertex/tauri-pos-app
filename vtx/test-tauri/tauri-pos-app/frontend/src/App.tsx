import React from "react";
import { useTauri } from "./hooks/useTauri";
import { Header, PermissionRoute } from "./components";
import { Dashboard, POS, Products, Orders, Reports } from "./pages";
import "./App.css";
import { BrowserRouter, Route, Routes, useLocation } from "react-router-dom";
import { LoginPage } from "./pages/Login";
import ProtectedRoutes from "./utils/ProtectedRoutes";
import { AuthProvider, useAuth } from "./contexts/AuthContext";
import ProtectedLayout from "./components/ProtectedLayout";
import { PERMISSIONS } from "./types/permissions";
import Accounts from "./pages/Accounts";
import Sync from "./pages/Sync";
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

// Component to conditionally render Header
const ConditionalHeader: React.FC<{
  backendStatus: any;
  tauriAvailable: boolean;
  isOnline: boolean;
  isRestarting: boolean;
  onManualReconnect: () => Promise<void>;
}> = (props) => {
  const location = useLocation();
  const { isAuthenticated } = useAuth();

  // Show header only on protected routes (not on login page)
  if (location.pathname === "/login" || !isAuthenticated) {
    return null;
  }

  return <Header {...props} />;
};

// Create a client
const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 5 * 60 * 1000, // 5 minutes
      retry: 1,
      refetchOnWindowFocus: false,
    },
  },
});

function App() {
  const {
    backendStatus,
    tauriAvailable,
    isOnline,
    isRestarting,
     manualReconnect,
  } = useTauri();

  return (
    <QueryClientProvider client={queryClient}>
      <AuthProvider>
        <BrowserRouter>
          <div className="app">
            <ConditionalHeader
              backendStatus={backendStatus}
              tauriAvailable={tauriAvailable}
              isOnline={isOnline}
              isRestarting={isRestarting}
              onManualReconnect={manualReconnect}
            />
            <main className="app-main">
              <Routes>
                <Route path="/login" element={<LoginPage />} />

                <Route element={<ProtectedRoutes />}>
                  <Route element={<ProtectedLayout />}>
                    <Route path="/" element={<Dashboard />} />
                    <Route
                      path="/sync"
                      element={
                        <PermissionRoute requiredPermission={PERMISSIONS.ADMIN}>
                          <Sync />
                        </PermissionRoute>
                        }
                    />
                    <Route
                      path="/accounts"
                      element={
                        <PermissionRoute requiredPermission={PERMISSIONS.ADMIN}>
                          <Accounts />
                        </PermissionRoute>
                      }
                    />
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
    </QueryClientProvider>
  );
}

export default App;
