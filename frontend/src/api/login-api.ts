import { API_BASE_URL } from "./pos-api"

export const loginRequest = async (username: string, password: string) => {
    try {
      const response = await fetch(`${API_BASE_URL}/users/authenticate?username=${username}&password=${password}`)
  
      if (!response.ok) {
        throw new Error(`Error: ${response.statusText}`)
      }
  
      const result = await response.json()
      return result // Return the fetched result
    } catch (error) {
      console.error("Error fetching data:", error)
      throw error // Rethrow the error for further handling
    }
  }