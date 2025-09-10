import { useTauri } from "./hooks/useTauri";
import { Header, Navigation } from "./components";
import { Dashboard, POS, Products, Orders, Reports } from "./pages";
import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { LoginPage } from "./pages/Login";
import ProtectedRoutes from "./utils/ProtectedRoutes";

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
  const user = { login: true };

  return (
    <BrowserRouter>
      <div className="app">
        <Header
          backendStatus={backendStatus}
          tauriAvailable={tauriAvailable}
          isOnline={isOnline}
          syncStatus={syncStatus}
          isRestarting={isRestarting}
          onForceSync={forceSync}
          onManualSync={manualSync}
          onManualReconnect={manualReconnect}
        />
        <Navigation />
        <main className="app-main">
          <Routes>
            <Route path="/login" element={<LoginPage />} />

            <Route element={<ProtectedRoutes user={user} />}>
              <Route path="/" element={<Dashboard />} />
              <Route path="/dashboard" element={<Dashboard />} />
              <Route path="/pos" element={<POS />} />
              <Route path="/products" element={<Products />} />
              <Route path="/orders" element={<Orders />} />

              <Route
                path="/reports"
                element={<Reports /> }
               
              />
            </Route>
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App;
