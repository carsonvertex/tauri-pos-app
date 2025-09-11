import { jwtDecode } from 'jwt-decode';

export interface JWTPayload {
  userId: number;
  username: string;
  permission: string;
  iat?: number;
  exp?: number;
}

// Decode JWT token to read user information
export const decodeToken = (token: string): JWTPayload | null => {
  try {
    const decoded = jwtDecode<JWTPayload>(token);
    console.log('🔍 JWT: Decoded token payload:', decoded);
    if (decoded.exp) {
      const expDate = new Date(decoded.exp * 1000);
      console.log('🔍 JWT: Token expires at:', expDate.toLocaleString());
    }
    return decoded;
  } catch (error) {
    console.error('JWT decode failed:', error);
    return null;
  }
};

// Check if token is expired
export const isTokenExpired = (token: string): boolean => {
  try {
    const decoded = decodeToken(token);
    if (!decoded || !decoded.exp) {
      console.log('🔍 JWT: Token has no expiration or decode failed');
      return true;
    }
    
    const currentTime = Math.floor(Date.now() / 1000);
    const isExpired = decoded.exp < currentTime;
    console.log('🔍 JWT: Token exp:', decoded.exp, 'Current time:', currentTime, 'Expired:', isExpired);
    return isExpired;
  } catch (error) {
    console.log('🔍 JWT: Token validation error:', error);
    return true;
  }
};

// Extract token from Authorization header
export const extractTokenFromHeader = (authHeader: string): string | null => {
  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    return null;
  }
  return authHeader.substring(7); // Remove "Bearer " prefix
};
