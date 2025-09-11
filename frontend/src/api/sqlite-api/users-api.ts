import { API_BASE_URL } from "./pos-api"

export interface CreateUserRequest {
  username: string;
  hashedPassword: string;
  permission: string;
}

export interface CreateUserResponse {
  success: boolean;
  message: string;
  user?: any;
}

export const getAllUsers = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/users`)

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

export const createUser = async (userData: CreateUserRequest): Promise<CreateUserResponse> => {
  try {
    const response = await fetch(`${API_BASE_URL}/users`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    });

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}));
      throw new Error(errorData.message || `Error: ${response.statusText}`);
    }

    const result = await response.json();
    return {
      success: true,
      message: 'User created successfully',
      user: result
    };
  } catch (error) {
    console.error("Error creating user:", error);
    return {
      success: false,
      message: error instanceof Error ? error.message : 'Failed to create user'
    };
  }
}