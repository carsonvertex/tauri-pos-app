import React from 'react';

export const Products: React.FC = () => {
  return (
    <div className="products">
      <h2>Product Management</h2>
      <button className="btn btn-primary">Add New Product</button>
      <div className="product-table">
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Category</th>
              <th>Price</th>
              <th>Stock</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Laptop</td>
              <td>Electronics</td>
              <td>$999.99</td>
              <td>10</td>
              <td>
                <button className="btn btn-small">Edit</button>
                <button className="btn btn-small btn-danger">Delete</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};
