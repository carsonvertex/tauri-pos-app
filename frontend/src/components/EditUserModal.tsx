import React, { useState, useEffect } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Alert,
  Box,
  Typography,
  Checkbox,
  FormControlLabel,
} from '@mui/material';
import { updateUser, UpdateUserRequest, getAdminCount } from '../api/sqlite-api/users-api';

interface User {
  userid: number;
  username: string;
  hashedPassword: string;
  permission: string;
  createdAt: string | null;
  updatedAt: string | null;
}

interface EditUserModalProps {
  open: boolean;
  onClose: () => void;
  onUserUpdated: () => void;
  user: User | null;
}

const EditUserModal: React.FC<EditUserModalProps> = ({ open, onClose, onUserUpdated, user }) => {
  const [formData, setFormData] = useState<UpdateUserRequest>({
    username: '',
    hashedPassword: '',
    permission: 'user',
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState(false);
  const [changePassword, setChangePassword] = useState(false);
  const [showCurrentPassword, setShowCurrentPassword] = useState(false);
  const [adminCount, setAdminCount] = useState<number>(0);

  // Update form data when user changes
  useEffect(() => {
    if (user) {
      setFormData({
        username: user.username,
        hashedPassword: '',
        permission: user.permission,
      });
      setChangePassword(false);
      setShowCurrentPassword(false);
      setError(null);
      setSuccess(false);
      
      // Fetch admin count
      const fetchAdminCount = async () => {
        try {
          const count = await getAdminCount();
          setAdminCount(count);
        } catch (error) {
          console.error('Failed to fetch admin count:', error);
        }
      };
      fetchAdminCount();
    }
  }, [user]);

  const handleInputChange = (field: keyof UpdateUserRequest) => (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement> | any
  ) => {
    setFormData(prev => ({
      ...prev,
      [field]: event.target.value,
    }));
    // Clear error when user starts typing
    if (error) setError(null);
  };

  const handleSubmit = async () => {
    // Basic validation
    if (!formData.username.trim()) {
      setError('Username is required');
      return;
    }
    if (formData.username.length < 3) {
      setError('Username must be at least 3 characters long');
      return;
    }
    if (changePassword && formData.hashedPassword.length < 6) {
      setError('Password must be at least 6 characters long');
      return;
    }

    setLoading(true);
    setError(null);

    try {
      // Prepare update data
      const updateData: UpdateUserRequest = {
        username: formData.username,
        permission: formData.permission,
      };

      // Only include password if user wants to change it
      if (changePassword && formData.hashedPassword) {
        updateData.hashedPassword = formData.hashedPassword;
      }

      const response = await updateUser(user!.userid, updateData);
      
      if (response.success) {
        setSuccess(true);
        setTimeout(() => {
          setSuccess(false);
          handleClose();
          onUserUpdated();
        }, 1500);
      } else {
        setError(response.message);
      }
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to update user');
    } finally {
      setLoading(false);
    }
  };

  const handleClose = () => {
    if (!loading) {
      setFormData({
        username: '',
        hashedPassword: '',
        permission: 'user',
      });
      setError(null);
      setSuccess(false);
      setChangePassword(false);
      setShowCurrentPassword(false);
      onClose();
    }
  };

  if (!user) return null;

  return (
    <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
      <DialogTitle>
        <Typography variant="h6" component="div">
          Edit User: {user.username}
        </Typography>
      </DialogTitle>
      
      <DialogContent>
        <Box sx={{ pt: 2 }}>
          {error && (
            <Alert severity="error" sx={{ mb: 2 }}>
              {error}
            </Alert>
          )}
          
          {success && (
            <Alert severity="success" sx={{ mb: 2 }}>
              User updated successfully!
            </Alert>
          )}

          <TextField
            autoFocus
            margin="dense"
            label="Username"
            fullWidth
            variant="outlined"
            value={formData.username}
            onChange={handleInputChange('username')}
            disabled={loading}
            helperText="Username must be unique and at least 3 characters long"
            sx={{ mb: 2 }}
          />

          <FormControlLabel
            control={
              <Checkbox
                checked={showCurrentPassword}
                onChange={(e) => setShowCurrentPassword(e.target.checked)}
                disabled={loading}
              />
            }
            label="Show Current Password Hash"
            sx={{ mb: 1 }}
          />

          {showCurrentPassword && (
            <TextField
              margin="dense"
              label="Current Password Hash"
              fullWidth
              variant="outlined"
              value={user.hashedPassword}
              disabled={true}
              helperText="This is the encrypted password hash stored in the database"
              sx={{ mb: 2 }}
              InputProps={{
                readOnly: true,
              }}
            />
          )}

          <FormControlLabel
            control={
              <Checkbox
                checked={changePassword}
                onChange={(e) => setChangePassword(e.target.checked)}
                disabled={loading}
              />
            }
            label="Change Password"
            sx={{ mb: 2 }}
          />

          {changePassword && (
            <TextField
              margin="dense"
              label="New Password"
              type="password"
              fullWidth
              variant="outlined"
              value={formData.hashedPassword}
              onChange={handleInputChange('hashedPassword')}
              disabled={loading}
              helperText="Password must be at least 6 characters long"
              sx={{ mb: 2 }}
            />
          )}

          <FormControl fullWidth sx={{ mb: 2 }}>
            <InputLabel>Permission Level</InputLabel>
            <Select
              value={formData.permission}
              onChange={handleInputChange('permission')}
              disabled={loading || (user?.permission === 'admin' && adminCount <= 1)}
              label="Permission Level"
            >
              <MenuItem value="user">User</MenuItem>
              <MenuItem value="admin">Admin</MenuItem>
            </Select>
            {user?.permission === 'admin' && adminCount <= 1 && (
              <Typography variant="caption" color="warning.main" sx={{ mt: 1 }}>
                Cannot change permission from admin to user. There must be at least one admin user in the system.
              </Typography>
            )}
          </FormControl>
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
          onClick={handleSubmit} 
          variant="contained"
          disabled={loading}
          color="primary"
        >
          {loading ? 'Updating...' : 'Update User'}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default EditUserModal;
