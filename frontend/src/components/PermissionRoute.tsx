import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { Permission, hasPermission } from '../types/permissions';

interface PermissionRouteProps {
  children: React.ReactNode;
  requiredPermission: Permission;
  fallbackPath?: string;
}

const PermissionRoute: React.FC<PermissionRouteProps> = ({ 
  children, 
  requiredPermission, 
  fallbackPath = '/dashboard' 
}) => {
  const { user, isAuthenticated, isLoading } = useAuth();

  // Show loading while checking authentication
  if (isLoading) {
    return <div>Loading...</div>;
  }

  // Redirect to login if not authenticated
  if (!isAuthenticated || !user) {
    return <Navigate to="/login" />;
  }

  // Check if user has required permission
  const hasRequiredPermission = hasPermission(user.permission, requiredPermission);
  
  console.log('🔒 PermissionRoute:', {
    userPermission: user.permission,
    requiredPermission,
    hasRequiredPermission,
  });

  if (!hasRequiredPermission) {
    console.log('❌ PermissionRoute: Access denied, redirecting to', fallbackPath);
    return <Navigate to={fallbackPath} />;
  }

  console.log('✅ PermissionRoute: Access granted');
  return <>{children}</>;
};

export default PermissionRoute;
