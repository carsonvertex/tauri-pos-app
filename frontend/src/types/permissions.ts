// Permission constants
export const PERMISSIONS = {
  ADMIN: 'admin',
  USER: 'user',
} as const;

export type Permission = typeof PERMISSIONS[keyof typeof PERMISSIONS];

// Permission levels for hierarchy
export const PERMISSION_LEVELS = {
  [PERMISSIONS.USER]: 1,
  [PERMISSIONS.ADMIN]: 2,
} as const;

// Helper function to check if user has required permission
export const hasPermission = (userPermission: string, requiredPermission: Permission): boolean => {
  const userLevel = PERMISSION_LEVELS[userPermission as Permission] || 0;
  const requiredLevel = PERMISSION_LEVELS[requiredPermission];
  return userLevel >= requiredLevel;
};

// Helper function to check if user is admin
export const isAdmin = (permission: string): boolean => {
  return permission === PERMISSIONS.ADMIN;
};
