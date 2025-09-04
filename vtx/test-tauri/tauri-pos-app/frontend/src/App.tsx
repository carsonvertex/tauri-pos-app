import { useState } from 'react';
import { useTauri } from './hooks/useTauri';
import { Header, Navigation } from './components';
import { Dashboard, POS, Products, Orders, Reports } from './pages';
import './App.css';

function App() {
  const [activeTab, setActiveTab] = useState('dashboard');
  const { backendStatus, tauriAvailable, startBackend, stopBackend } = useTauri();

  const renderActivePage = () => {
    switch (activeTab) {
      case 'dashboard':
        return <Dashboard />;
      case 'pos':
        return <POS />;
      case 'products':
        return <Products />;
      case 'orders':
        return <Orders />;
      case 'reports':
        return <Reports />;
      default:
        return <Dashboard />;
    }
  };

  return (
    <div className="app">
      <Header
        backendStatus={backendStatus}
        tauriAvailable={tauriAvailable}
        onStartBackend={startBackend}
        onStopBackend={stopBackend}
      />
      
      <Navigation 
        activeTab={activeTab} 
        onTabChange={setActiveTab} 
      />

      <main className="app-main">
        {renderActivePage()}
      </main>
    </div>
  );
}

export default App;
