import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { decodeToken, isTokenExpired } from '../utils/jwt';

interface User {
  userId: number;
  username: string;
  permission: string;
}

interface AuthContextType {
  user: User | null;
  token: string | null;
  login: (token: string, user: User) => void;
  logout: () => void;
  isAuthenticated: boolean;
  isLoading: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  // Check for existing token on app load
  useEffect(() => {
    // Add a small delay to ensure localStorage is fully available
    const checkAuthToken = () => {
      try {
        const storedToken = localStorage.getItem('authToken');
        console.log('🔍 AuthContext: Checking stored token:', storedToken ? 'Token exists' : 'No token');
        
        if (storedToken) {
          const isExpired = isTokenExpired(storedToken);
          console.log('🔍 AuthContext: Token expired?', isExpired);
          
          if (!isExpired) {
            const decodedToken = decodeToken(storedToken);
            console.log('🔍 AuthContext: Decoded token:', decodedToken);
            
            if (decodedToken) {
              setToken(storedToken);
              setUser({
                userId: decodedToken.userId,
                username: decodedToken.username,
                permission: decodedToken.permission,
              });
              console.log('✅ AuthContext: User authenticated successfully');
            } else {
              console.log('❌ AuthContext: Token decode failed, removing token');
              localStorage.removeItem('authToken');
            }
          } else {
            console.log('❌ AuthContext: Token expired, removing token');
            localStorage.removeItem('authToken');
          }
        }
      } catch (error) {
        console.error('❌ AuthContext: Error checking auth token:', error);
      }
    };

    // Use setTimeout to ensure localStorage is ready
    const timeoutId = setTimeout(() => {
      checkAuthToken();
      setIsLoading(false);
    }, 100);
    
    return () => clearTimeout(timeoutId);
  }, []);

  const login = (newToken: string, userData: User) => {
    setToken(newToken);
    setUser(userData);
    localStorage.setItem('authToken', newToken);
  };

  const logout = () => {
    setToken(null);
    setUser(null);
    localStorage.removeItem('authToken');
  };

  const isAuthenticated = user !== null && token !== null && !isTokenExpired(token);

  const value: AuthContextType = {
    user,
    token,
    login,
    logout,
    isAuthenticated,
    isLoading,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};
