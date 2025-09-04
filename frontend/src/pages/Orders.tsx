import React from 'react';

export const Orders: React.FC = () => {
  return (
    <div className="orders">
      <h2>Order Management</h2>
      <div className="order-table">
        <table>
          <thead>
            <tr>
              <th>Order #</th>
              <th>Customer</th>
              <th>Date</th>
              <th>Total</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>ORD-12345</td>
              <td>John Doe</td>
              <td>2024-01-15</td>
              <td>$1,029.98</td>
              <td>Completed</td>
              <td>
                <button className="btn btn-small">View</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};
