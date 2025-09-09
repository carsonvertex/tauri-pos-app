import React from 'react';

export const Dashboard: React.FC = () => {
  return (
    <div className="p-6">
      <h2 className="text-3xl font-bold text-gray-900 mb-8">Dashboard</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <div className="bg-white p-6 rounded-xl shadow-soft border border-gray-200 hover:shadow-medium transition-shadow">
          <h3 className="text-sm font-medium text-gray-500 uppercase tracking-wide mb-2">Total Sales</h3>
          <p className="text-3xl font-bold text-gray-900">$12,450.00</p>
        </div>
        <div className="bg-white p-6 rounded-xl shadow-soft border border-gray-200 hover:shadow-medium transition-shadow">
          <h3 className="text-sm font-medium text-gray-500 uppercase tracking-wide mb-2">Orders Today</h3>
          <p className="text-3xl font-bold text-gray-900">24</p>
        </div>
        <div className="bg-white p-6 rounded-xl shadow-soft border border-gray-200 hover:shadow-medium transition-shadow">
          <h3 className="text-sm font-medium text-gray-500 uppercase tracking-wide mb-2">Products</h3>
          <p className="text-3xl font-bold text-gray-900">156</p>
        </div>
        <div className="bg-white p-6 rounded-xl shadow-soft border border-gray-200 hover:shadow-medium transition-shadow">
          <h3 className="text-sm font-medium text-gray-500 uppercase tracking-wide mb-2">Low Stock</h3>
          <p className="text-3xl font-bold text-red-600">8</p>
        </div>
      </div>
    </div>
  );
};
