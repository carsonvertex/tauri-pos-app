import React from 'react';
import { Outlet } from 'react-router-dom';
import { Navigation } from './Navigation';

const ProtectedLayout: React.FC = () => {
  return (
    <>
      <Navigation />
      <Outlet />
    </>
  );
};

export default ProtectedLayout;
