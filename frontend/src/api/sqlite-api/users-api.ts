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

export interface UpdateUserRequest {
  username: string;
  hashedPassword?: string;
  permission: string;
}

export interface UpdateUserResponse {
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

export const updateUser = async (userId: number, userData: UpdateUserRequest): Promise<UpdateUserResponse> => {
  try {
    const response = await fetch(`${API_BASE_URL}/users/${userId}`, {
      method: 'PUT',
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
      message: 'User updated successfully',
      user: result
    };
  } catch (error) {
    console.error("Error updating user:", error);
    return {
      success: false,
      message: error instanceof Error ? error.message : 'Failed to update user'
    };
  }
}

export const getAdminCount = async (): Promise<number> => {
  try {
    const response = await fetch(`${API_BASE_URL}/users/admin-count`);

    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`);
    }

    const result = await response.json();
    return result;
  } catch (error) {
    console.error("Error fetching admin count:", error);
    throw error;
  }
}

export interface DeleteUserResponse {
  success: boolean;
  message: string;
}

export const deleteUser = async (userId: number): Promise<DeleteUserResponse> => {
  try {
    const response = await fetch(`${API_BASE_URL}/users/${userId}`, {
      method: 'DELETE',
    });

    if (!response.ok) {
      const errorData = await response.json().catch(() => ({}));
      throw new Error(errorData.message || `Error: ${response.statusText}`);
    }

    return {
      success: true,
      message: 'User deleted successfully'
    };
  } catch (error) {
    console.error("Error deleting user:", error);
    return {
      success: false,
      message: error instanceof Error ? error.message : 'Failed to delete user'
    };
  }
}