import React, { useState } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Alert,
  Box,
  Typography,
  Chip,
} from '@mui/material';
import { Delete as DeleteIcon } from '@mui/icons-material';
import { deleteUser, DeleteUserResponse } from '../api/sqlite-api/users-api';

interface User {
  userid: number;
  username: string;
  hashedPassword: string;
  permission: string;
  createdAt: string | null;
  updatedAt: string | null;
}

interface DeleteUserConfirmationModalProps {
  open: boolean;
  onClose: () => void;
  onUserDeleted: () => void;
  user: User | null;
}

const DeleteUserConfirmationModal: React.FC<DeleteUserConfirmationModalProps> = ({ 
  open, 
  onClose, 
  onUserDeleted, 
  user 
}) => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleDelete = async () => {
    if (!user) return;

    setLoading(true);
    setError(null);

    try {
      const response = await deleteUser(user.userid);
      
      if (response.success) {
        onClose();
        onUserDeleted();
      } else {
        setError(response.message);
      }
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to delete user');
    } finally {
      setLoading(false);
    }
  };

  const handleClose = () => {
    if (!loading) {
      setError(null);
      onClose();
    }
  };

  if (!user) return null;

  const getPermissionChip = (permission: string) => {
    const color = permission === "admin" ? "error" : "default";
    return (
      <Chip
        label={permission.toUpperCase()}
        color={color}
        size="small"
        variant="outlined"
      />
    );
  };

  return (
    <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
      <DialogTitle>
        <Box display="flex" alignItems="center" gap={1}>
          <DeleteIcon color="error" />
          <Typography variant="h6" component="div">
            Delete User Confirmation
          </Typography>
        </Box>
      </DialogTitle>
      
      <DialogContent>
        <Box sx={{ pt: 2 }}>
          {error && (
            <Alert severity="error" sx={{ mb: 2 }}>
              {error}
            </Alert>
          )}

          <Typography variant="body1" sx={{ mb: 2 }}>
            Are you sure you want to delete this user? This action cannot be undone.
          </Typography>

          <Box sx={{ 
            p: 2, 
            border: '1px solid', 
            borderColor: 'divider', 
            borderRadius: 1,
            backgroundColor: 'background.paper'
          }}>
            <Typography variant="subtitle2" gutterBottom>
              User Details:
            </Typography>
            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 1 }}>
              <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
                <Typography variant="body2" color="text.secondary">ID:</Typography>
                <Typography variant="body2">{user.userid}</Typography>
              </Box>
              <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
                <Typography variant="body2" color="text.secondary">Username:</Typography>
                <Typography variant="body2" fontWeight="medium">{user.username}</Typography>
              </Box>
              <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
                <Typography variant="body2" color="text.secondary">Permission:</Typography>
                {getPermissionChip(user.permission)}
              </Box>
            </Box>
          </Box>

          {user.permission === 'admin' && (
            <Alert severity="warning" sx={{ mt: 2 }}>
              <Typography variant="body2">
                <strong>Warning:</strong> You are about to delete an admin user. 
                Make sure there are other admin users in the system to maintain system access.
              </Typography>
            </Alert>
          )}
        </Box>
      </DialogContent>

      <DialogActions sx={{ p: 3 }}>
        <Button 
          onClick={handleClose} 
          disabled={loading}
          color="inherit"
        >
          Cancel
        </Button>
        <Button 
          onClick={handleDelete} 
          variant="contained"
          disabled={loading}
          color="error"
          startIcon={<DeleteIcon />}
        >
          {loading ? 'Deleting...' : 'Delete User'}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default DeleteUserConfirmationModal;
