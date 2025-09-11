import React from 'react';
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import CircularProgress from "@mui/material/CircularProgress";
import Alert from "@mui/material/Alert";
import Button from "@mui/material/Button";
import RefreshIcon from "@mui/icons-material/Refresh";
import { useQuery } from '@tanstack/react-query';
import { getLocalProductsCount } from '../api/sqlite-api/localProduct-api';
import { getLocalProductBarcodesCount } from '../api/sqlite-api/localProductBarcode-api';
import { getLocalProductDescriptionsCount } from '../api/sqlite-api/localProductDescription-api';

export default function Sync() {
  // TanStack Query hooks for each count
  const { 
    data: productsCount = 0, 
    isLoading: productsLoading, 
    error: productsError,
    refetch: refetchProducts 
  } = useQuery({
    queryKey: ['localProductsCount'],
    queryFn: getLocalProductsCount,
    staleTime: 30000, // 30 seconds
    refetchOnWindowFocus: false,
  });

  const { 
    data: barcodesCount = 0, 
    isLoading: barcodesLoading, 
    error: barcodesError,
    refetch: refetchBarcodes 
  } = useQuery({
    queryKey: ['localProductBarcodesCount'],
    queryFn: getLocalProductBarcodesCount,
    staleTime: 30000, // 30 seconds
    refetchOnWindowFocus: false,
  });

  const { 
    data: descriptionsCount = 0, 
    isLoading: descriptionsLoading, 
    error: descriptionsError,
    refetch: refetchDescriptions 
  } = useQuery({
    queryKey: ['localProductDescriptionsCount'],
    queryFn: getLocalProductDescriptionsCount,
    staleTime: 30000, // 30 seconds
    refetchOnWindowFocus: false,
  });

  const usersCount = 3; // Default users count from database initialization

  // Combined loading state
  const isLoading = productsLoading || barcodesLoading || descriptionsLoading;
  
  // Combined error state
  const error = productsError || barcodesError || descriptionsError;
  const errorMessage = error instanceof Error ? error.message : 'Failed to fetch counts';

  // Refresh all queries
  const refreshAll = () => {
    refetchProducts();
    refetchBarcodes();
    refetchDescriptions();
  };

  const totalRecords = productsCount + barcodesCount + descriptionsCount + usersCount;

  return (
    <div className="w-full p-6 flex flex-col gap-6">
      {/* Header */}
      <div className="flex justify-between items-center mb-6">
        <Typography variant="h4" component="h1" gutterBottom>
          Database Sync Status
        </Typography>
        <Button
          variant="contained"
          startIcon={<RefreshIcon />}
          onClick={refreshAll}
          disabled={isLoading}
        >
          Refresh
        </Button>
      </div>

      {/* Error Alert */}
      {error && (
        <Alert severity="error" className="mb-6">
          {errorMessage}
        </Alert>
      )}

      {/* Grid Container */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 mb-6">
        {/* Local Products */}
        <Card className="h-full">
          <CardContent>
            <Typography color="textSecondary" gutterBottom>
              Local Products
            </Typography>
            <Typography variant="h4" component="div">
              {productsLoading ? <CircularProgress size={24} /> : productsCount}
            </Typography>
            <Typography variant="body2" color="textSecondary">
              Product records in SQLite
            </Typography>
          </CardContent>
        </Card>

        {/* Local Product Barcodes */}
        <Card className="h-full">
          <CardContent>
            <Typography color="textSecondary" gutterBottom>
              Product Barcodes
            </Typography>
            <Typography variant="h4" component="div">
              {barcodesLoading ? <CircularProgress size={24} /> : barcodesCount}
            </Typography>
            <Typography variant="body2" color="textSecondary">
              Barcode records in SQLite
            </Typography>
          </CardContent>
        </Card>

        {/* Local Product Descriptions */}
        <Card className="h-full">
          <CardContent>
            <Typography color="textSecondary" gutterBottom>
              Product Descriptions
            </Typography>
            <Typography variant="h4" component="div">
              {descriptionsLoading ? <CircularProgress size={24} /> : descriptionsCount}
            </Typography>
            <Typography variant="body2" color="textSecondary">
              Description records in SQLite
            </Typography>
          </CardContent>
        </Card>

        {/* Users */}
        <Card className="h-full">
          <CardContent>
            <Typography color="textSecondary" gutterBottom>
              Users
            </Typography>
            <Typography variant="h4" component="div">
              {usersCount}
            </Typography>
            <Typography variant="body2" color="textSecondary">
              User accounts in SQLite
            </Typography>
          </CardContent>
        </Card>
      </div>

      {/* Total Records - Full Width */}
      <div className="grid grid-cols-1 mb-6">
        <Card sx={{ bgcolor: 'primary.main', color: 'primary.contrastText' }}>
          <CardContent>
            <Typography variant="h5" gutterBottom>
              Total Records in SQLite Database
            </Typography>
            <Typography variant="h2" component="div">
              {isLoading ? <CircularProgress size={40} color="inherit" /> : totalRecords}
            </Typography>
            <Typography variant="body1">
              All tables combined
            </Typography>
          </CardContent>
        </Card>
      </div>

      {/* Database Info */}
      <Card className="mt-6">
        <CardContent>
          <Typography variant="h6" gutterBottom>
            Database Information
          </Typography>
          <Typography variant="body2" color="textSecondary">
            • Database Type: SQLite (Local)
          </Typography>
          <Typography variant="body2" color="textSecondary">
            • Location: data/pos_local.db
          </Typography>
          <Typography variant="body2" color="textSecondary">
            • Tables: local_product, local_product_barcode, local_product_description, users
          </Typography>
          <Typography variant="body2" color="textSecondary">
            • Last Updated: {new Date().toLocaleString()}
          </Typography>
        </CardContent>
      </Card>
    </div>
  );
}
