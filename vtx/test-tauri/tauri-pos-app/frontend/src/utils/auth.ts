import { comparePassword } from './bcrypt-hash';

/**
 * Authenticate a user by comparing their password with the stored hash
 * This is a frontend utility - in a real app, this would be done on the backend
 * @param username - Username to authenticate
 * @param password - Plain text password
 * @param storedHash - Hashed password from database
 * @returns Promise<boolean> - True if authentication successful
 */
export const authenticateUser = async (
  _username: string, 
  password: string, 
  storedHash: string
): Promise<boolean> => {
  try {
    return await comparePassword(password, storedHash);
  } catch (error) {
    console.error('Authentication error:', error);
    return false;
  }
};

/**
 * Example usage with the admin user from the database
 * In a real app, you would fetch the user data from your backend API
 */
export const authenticateAdmin = async (password: string): Promise<boolean> => {
  // This is the hash stored in the database for the admin user
  const adminHash = '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8';
  return await comparePassword(password, adminHash);
};