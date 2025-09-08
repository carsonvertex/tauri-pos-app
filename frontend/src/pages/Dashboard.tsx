import React from 'react';

export const Dashboard: React.FC = () => {
  return (
    <div className="dashboard">
      <h2>Dashboard</h2>
      <div className="stats-grid">
        <div className="stat-card">
          <h3>Total Sales</h3>
          <p className="stat-value">$12,450.00</p>
        </div>
        <div className="stat-card">
          <h3>Orders Today</h3>
          <p className="stat-value">24</p>
        </div>
        <div className="stat-card">
          <h3>Products</h3>
          <p className="stat-value">156</p>
        </div>
        <div className="stat-card">
          <h3>Low Stock</h3>
          <p className="stat-value">8</p>
        </div>
      </div>
    </div>
  );
};
