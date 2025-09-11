import React, { useState } from 'react';
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
} from '@mui/material';
import { createUser, CreateUserRequest } from '../api/sqlite-api/users-api';

interface AddUserModalProps {
  open: boolean;
  onClose: () => void;
  onUserCreated: () => void;
}

const AddUserModal: React.FC<AddUserModalProps> = ({ open, onClose, onUserCreated }) => {
  const [formData, setFormData] = useState<CreateUserRequest>({
    username: '',
    hashedPassword: '',
    permission: 'user',
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState(false);

  const handleInputChange = (field: keyof CreateUserRequest) => (
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
    if (!formData.hashedPassword.trim()) {
      setError('Password is required');
      return;
    }
    if (formData.username.length < 3) {
      setError('Username must be at least 3 characters long');
      return;
    }
    if (formData.hashedPassword.length < 6) {
      setError('Password must be at least 6 characters long');
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const response = await createUser(formData);
      
      if (response.success) {
        setSuccess(true);
        setTimeout(() => {
          setSuccess(false);
          handleClose();
          onUserCreated();
        }, 1500);
      } else {
        setError(response.message);
      }
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to create user');
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
      onClose();
    }
  };

  return (
    <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
      <DialogTitle>
        <Typography variant="h6" component="div">
          Add New User
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
              User created successfully!
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

          <TextField
            margin="dense"
            label="Password"
            type="password"
            fullWidth
            variant="outlined"
            value={formData.hashedPassword}
            onChange={handleInputChange('hashedPassword')}
            disabled={loading}
            helperText="Password must be at least 6 characters long"
            sx={{ mb: 2 }}
          />

          <FormControl fullWidth sx={{ mb: 2 }}>
            <InputLabel>Permission Level</InputLabel>
            <Select
              value={formData.permission}
              onChange={handleInputChange('permission')}
              disabled={loading}
              label="Permission Level"
            >
              <MenuItem value="user">User</MenuItem>
              <MenuItem value="admin">Admin</MenuItem>
            </Select>
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
          {loading ? 'Creating...' : 'Create User'}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default AddUserModal;
