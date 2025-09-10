import { Link, useLocation } from "react-router-dom";
import { 
  Dashboard, 
  PointOfSale, 
  Inventory, 
  Receipt, 
  Assessment 
} from "@mui/icons-material";

export const Navigation = () => {
  const location = useLocation();
  const token = localStorage.getItem("authToken");
  console.log('token', token);
  const tabs = [
    { 
      id: "dashboard", 
      label: "Dashboard", 
      icon: Dashboard, 
      url: "/dashboard" 
    },
    { 
      id: "pos", 
      label: "POS", 
      icon: PointOfSale, 
      url: "/pos" 
    },
    { 
      id: "products", 
      label: "Products", 
      icon: Inventory, 
      url: "/products" 
    },
    { 
      id: "orders", 
      label: "Orders", 
      icon: Receipt, 
      url: "/orders" 
    },
    { 
      id: "reports", 
      label: "Reports", 
      icon: Assessment, 
      url: "/reports" 
    },
    { 
      id: "accounts", 
      label: "Accounts", 
      icon: Assessment, 
      url: "/accounts" 
    },
  ];

  return (
    <nav className="bg-gray-50 border-b border-gray-200 px-4 py-2">
      <div className="max-w-7xl mx-auto">
        <div className="flex space-x-1">
          {tabs.map((tab) => {
            const isActive = location.pathname === tab.url || 
                           (tab.url === "/dashboard" && location.pathname === "/");
            const IconComponent = tab.icon;
            
            return (
              <Link
                to={tab.url}
                key={tab.id}
                className={`flex items-center space-x-2 px-4 py-2 rounded-lg text-sm font-medium transition-all duration-200 ${
                  isActive
                    ? 'bg-blue-100 text-blue-700 shadow-sm'
                    : 'text-gray-600 hover:bg-gray-100 hover:text-gray-800'
                }`}
              >
                <IconComponent className="w-5 h-5" />
                <span>{tab.label}</span>
              </Link>
            );
          })}
        </div>
      </div>
    </nav>
  );
};
