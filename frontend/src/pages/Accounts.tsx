import React from 'react';
import {
  Box,
  Card,
  CardContent,
  Typography,
  Container
} from '@mui/material';
import {
  AttachMoney,
  ShoppingCart,
  Inventory,
  Warning
} from '@mui/icons-material';

export const Accounts: React.FC = () => {
 
  return (
    <Container maxWidth="xl" sx={{ py: 4 }}>
      <Typography variant="h4" component="h1" gutterBottom sx={{ mb: 4 }}>
        Accounts
      </Typography>
      
      <Box
        sx={{
          display: 'grid',
          gridTemplateColumns: {
            xs: '1fr',
            sm: 'repeat(2, 1fr)',
            md: 'repeat(4, 1fr)'
          },
          gap: 3
        }}
      >
       
      </Box>
    </Container>
  );
};
