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

export const Dashboard: React.FC = () => {
  const stats = [
    {
      title: 'Total Sales',
      value: '$12,450.00',
      icon: <AttachMoney sx={{ fontSize: 40, color: 'success.main' }} />,
      color: 'success'
    },
    {
      title: 'Orders Today',
      value: '24',
      icon: <ShoppingCart sx={{ fontSize: 40, color: 'primary.main' }} />,
      color: 'primary'
    },
    {
      title: 'Products',
      value: '156',
      icon: <Inventory sx={{ fontSize: 40, color: 'info.main' }} />,
      color: 'info'
    },
    {
      title: 'Low Stock',
      value: '8',
      icon: <Warning sx={{ fontSize: 40, color: 'warning.main' }} />,
      color: 'warning'
    }
  ];

  return (
    <Container maxWidth="xl" sx={{ py: 4 }}>
      <Typography variant="h4" component="h1" gutterBottom sx={{ mb: 4 }}>
        Dashboard
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
        {stats.map((stat, index) => (
          <Card
            key={index}
            sx={{
              height: '100%',
              display: 'flex',
              flexDirection: 'column',
              transition: 'transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out',
              '&:hover': {
                transform: 'translateY(-4px)',
                boxShadow: '0 8px 25px rgba(0,0,0,0.15)'
              }
            }}
          >
            <CardContent sx={{ flexGrow: 1, p: 3 }}>
              <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                {stat.icon}
                <Typography
                  variant="h6"
                  component="h3"
                  sx={{
                    ml: 1,
                    fontSize: '0.875rem',
                    fontWeight: 500,
                    color: 'text.secondary',
                    textTransform: 'uppercase',
                    letterSpacing: '0.5px'
                  }}
                >
                  {stat.title}
                </Typography>
              </Box>
              <Typography
                variant="h3"
                component="p"
                sx={{
                  fontWeight: 'bold',
                  color: stat.color === 'warning' ? 'warning.main' : 'text.primary'
                }}
              >
                {stat.value}
              </Typography>
            </CardContent>
          </Card>
        ))}
      </Box>
    </Container>
  );
};
