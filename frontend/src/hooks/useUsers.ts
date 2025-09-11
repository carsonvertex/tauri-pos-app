import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { 
  getAllUsers, 
  createUser, 
  updateUser, 
  deleteUser, 
  getAdminCount,
  CreateUserRequest,
  UpdateUserRequest,
  CreateUserResponse,
  UpdateUserResponse,
  DeleteUserResponse
} from '../api/sqlite-api/users-api';

// Re-export types for convenience
export type { CreateUserRequest, UpdateUserRequest, CreateUserResponse, UpdateUserResponse, DeleteUserResponse };

// Query keys
export const userKeys = {
  all: ['users'] as const,
  lists: () => [...userKeys.all, 'list'] as const,
  list: (filters: string) => [...userKeys.lists(), { filters }] as const,
  details: () => [...userKeys.all, 'detail'] as const,
  detail: (id: number) => [...userKeys.details(), id] as const,
  adminCount: () => [...userKeys.all, 'adminCount'] as const,
};

// User interface
export interface User {
  userid: number;
  username: string;
  hashedPassword: string;
  permission: string;
  createdAt: string | null;
  updatedAt: string | null;
}

// Query hooks
export const useUsers = () => {
  return useQuery({
    queryKey: userKeys.lists(),
    queryFn: getAllUsers,
    staleTime: 1000 * 60 * 2, // 2 minutes
  });
};

export const useAdminCount = () => {
  return useQuery({
    queryKey: userKeys.adminCount(),
    queryFn: getAdminCount,
    staleTime: 1000 * 60 * 5, // 5 minutes
  });
};

// Mutation hooks
export const useCreateUser = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (userData: CreateUserRequest): Promise<CreateUserResponse> => createUser(userData),
    onSuccess: () => {
      // Invalidate and refetch users list
      queryClient.invalidateQueries({ queryKey: userKeys.lists() });
      queryClient.invalidateQueries({ queryKey: userKeys.adminCount() });
    },
    onError: (error) => {
      console.error('Failed to create user:', error);
    },
  });
};

export const useUpdateUser = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ userId, userData }: { userId: number; userData: UpdateUserRequest }): Promise<UpdateUserResponse> => 
      updateUser(userId, userData),
    onSuccess: () => {
      // Invalidate and refetch users list
      queryClient.invalidateQueries({ queryKey: userKeys.lists() });
      queryClient.invalidateQueries({ queryKey: userKeys.adminCount() });
    },
    onError: (error) => {
      console.error('Failed to update user:', error);
    },
  });
};

export const useDeleteUser = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (userId: number): Promise<DeleteUserResponse> => deleteUser(userId),
    onSuccess: () => {
      // Invalidate and refetch users list
      queryClient.invalidateQueries({ queryKey: userKeys.lists() });
      queryClient.invalidateQueries({ queryKey: userKeys.adminCount() });
    },
    onError: (error) => {
      console.error('Failed to delete user:', error);
    },
  });
};
