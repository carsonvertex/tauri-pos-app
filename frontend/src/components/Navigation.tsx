import { Link } from "react-router-dom";

export const Navigation = () => {
  const tabs = [
    { id: "dashboard", label: " Dashboard", icon: "📊", url: "/dashboard" },
    { id: "pos", label: " POS", icon: "💰", url: "/pos" },
    { id: "products", label: " Products", icon: "📦", url: "/products" },
    { id: "orders", label: " Orders", icon: "📋", url: "/orders" },
    { id: "reports", label: " Reports", icon: "📈", url: "/reports" },
  ];

  return (
    <nav className="app-nav">
      {tabs.map((tab) => (
        <Link to={tab.url} key={tab.id}>
          {tab.icon} {tab.label}
        </Link>
      ))}
    </nav>
  );
};
