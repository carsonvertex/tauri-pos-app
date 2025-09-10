import React, { useState } from "react";
import {
  Box,
  Card,
  CardContent,
  TextField,
  Button,
  Typography,
  Container,
  Stack,
  Alert,
} from "@mui/material";
import { Login as LoginIcon, ShoppingCart } from "@mui/icons-material";
import { loginRequest } from "../api/login-api";
import { useAuth } from "../contexts/AuthContext";
import { useNavigate } from "react-router-dom";

export const LoginPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");

    try {
      // Send plain password to backend for proper bcrypt comparison
      const result = await loginRequest(username, password);
      console.log("Login result:", result);
      
      // Store JWT token and user data
      login(result.token, {
        userId: result.userId,
        username: result.username,
        permission: result.permission,
      });
      
      // Redirect to dashboard or main app
      navigate('/dashboard');
    } catch (error: any) {
      console.error("Login error:", error);
      setError(error.message || "An error occurred during login");
    }
  };

  const handleReset = () => {
    setUsername("");
    setPassword("");
    setError("");
  };

  return (
    <Box
      sx={{
        minHeight: "100vh",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        background: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)",
        padding: 2,
      }}
    >
      <Container maxWidth="sm">
        <Card
          sx={{
            borderRadius: 3,
            boxShadow: "0 20px 40px rgba(0, 0, 0, 0.1)",
            overflow: "hidden",
          }}
        >
          <CardContent sx={{ p: 4 }}>
            <Box sx={{ textAlign: "center", mb: 4 }}>
              <ShoppingCart
                sx={{
                  fontSize: 60,
                  color: "primary.main",
                  mb: 2,
                }}
              />
              <Typography variant="h4" component="h1" gutterBottom>
                Tauri POS
              </Typography>
              <Typography variant="h6" color="text.secondary">
                Sign in to your account
              </Typography>
            </Box>

            {error && (
              <Alert severity="error" sx={{ mb: 2 }}>
                {error}
              </Alert>
            )}

            <Box component="form" onSubmit={handleSubmit}>
              <Stack spacing={3}>
                <TextField
                  fullWidth
                  label="Username"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  placeholder="Enter your username"
                  required
                  variant="outlined"
                />

                <TextField
                  fullWidth
                  label="Password"
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  placeholder="Enter your password"
                  required
                  variant="outlined"
                />

                <Stack direction="row" spacing={2}>
                  <Button
                    type="submit"
                    variant="contained"
                    size="large"
                    startIcon={<LoginIcon />}
                    sx={{ flex: 1 }}
                  >
                    Sign In
                  </Button>

                  <Button
                    type="button"
                    variant="outlined"
                    size="large"
                    onClick={handleReset}
                    sx={{ flex: 1 }}
                  >
                    Reset
                  </Button>
                </Stack>
              </Stack>
            </Box>
          </CardContent>
        </Card>
      </Container>
    </Box>
  );
};
