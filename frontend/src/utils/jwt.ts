import jwt from 'jsonwebtoken';

// JWT Secret - In production, this should be stored in environment variables
const JWT_SECRET = 'your-super-secret-jwt-key-change-this-in-production';
const JWT_EXPIRES_IN = '24h'; // Token expires in 24 hours

export interface JWTPayload {
  userId: number;
  username: string;
  permission: string;
  iat?: number;
  exp?: number;
}

 
export const generateToken = (userId: number, username: string, permission: string): string => {
  const payload: Omit<JWTPayload, 'iat' | 'exp'> = {
    userId,
    username,
    permission,
  };

  return jwt.sign(payload, JWT_SECRET, { expiresIn: JWT_EXPIRES_IN });
};

 
export const verifyToken = (token: string): JWTPayload | null => {
  try {
    const decoded = jwt.verify(token, JWT_SECRET) as JWTPayload;
    return decoded;
  } catch (error) {
    console.error('JWT verification failed:', error);
    return null;
  }
};

 
export const isTokenExpired = (token: string): boolean => {
  try {
    const decoded = jwt.decode(token) as JWTPayload;
    if (!decoded || !decoded.exp) return true;
    
    const currentTime = Math.floor(Date.now() / 1000);
    return decoded.exp < currentTime;
  } catch (error) {
    return true;
  }
};

 
export const extractTokenFromHeader = (authHeader: string): string | null => {
  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    return null;
  }
  return authHeader.substring(7); // Remove "Bearer " prefix
};
