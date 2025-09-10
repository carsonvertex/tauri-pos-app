import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

const ProtectedRoutes = () => {
  const { isAuthenticated, isLoading } = useAuth();
  console.log('🔒 ProtectedRoutes: isLoading =', isLoading, 'isAuthenticated =', isAuthenticated);
  
  // Show loading while checking authentication
  if (isLoading) {
    return <div>Loading...</div>;
  }
  
  return isAuthenticated ? <Outlet /> : <Navigate to="/login" />;
};

export default ProtectedRoutes;
