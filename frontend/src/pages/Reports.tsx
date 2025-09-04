import React from 'react';

export const Reports: React.FC = () => {
  return (
    <div className="reports">
      <h2>Reports & Analytics</h2>
      <div className="report-grid">
        <div className="report-card">
          <h3>Sales Report</h3>
          <p>Generate sales reports for any date range</p>
          <button className="btn btn-primary">Generate Report</button>
        </div>
        <div className="report-card">
          <h3>Inventory Report</h3>
          <p>View current inventory levels</p>
          <button className="btn btn-primary">View Inventory</button>
        </div>
        <div className="report-card">
          <h3>Customer Report</h3>
          <p>Analyze customer purchasing patterns</p>
          <button className="btn btn-primary">View Report</button>
        </div>
      </div>
    </div>
  );
};
