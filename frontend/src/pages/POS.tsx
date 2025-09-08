import React from 'react';

export const POS: React.FC = () => {
  return (
    <div className="pos">
      <h2>Point of Sale</h2>
      <div className="pos-layout">
        <div className="product-grid">
          <h3>Products</h3>
          <div className="product-list">
            <div className="product-item">Laptop - $999.99</div>
            <div className="product-item">Mouse - $29.99</div>
            <div className="product-item">Coffee - $12.99</div>
          </div>
        </div>
        <div className="cart">
          <h3>Cart</h3>
          <div className="cart-items">
            <p>No items in cart</p>
          </div>
          <div className="cart-total">
            <strong>Total: $0.00</strong>
          </div>
          <button className="btn btn-success">Complete Sale</button>
        </div>
      </div>
    </div>
  );
};
