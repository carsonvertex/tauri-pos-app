import { API_BASE_URL } from "./pos-api"

export interface AuthResponse {
  token: string;
  username: string;
  permission: string;
  userId: number;
  message: string;
}

export const loginRequest = async (username: string, password: string): Promise<AuthResponse> => {
    try {
      const response = await fetch(`${API_BASE_URL}/users/authenticate?username=${username}&password=${password}`)
  
      if (!response.ok) {
        const errorData = await response.json().catch(() => ({ message: 'Authentication failed' }));
        throw new Error(errorData.message || `Error: ${response.statusText}`)
      }
  
      const result: AuthResponse = await response.json()
      return result
    } catch (error) {
      console.error("Error fetching data:", error)
      throw error
    }
  }