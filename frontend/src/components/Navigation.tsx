import { Link, useLocation } from "react-router-dom";
import {
  Dashboard,
  PointOfSale,
  Inventory,
  Receipt,
  Assessment,
} from "@mui/icons-material";
import { useAuth } from "../contexts/AuthContext";
import { PERMISSIONS, hasPermission } from "../types/permissions";

export const Navigation = () => {
  const location = useLocation();
  const { user } = useAuth();

  const allTabs = [
    {
      id: "accounts",
      label: "Accounts",
      icon: Assessment,
      url: "/accounts",
      permission: PERMISSIONS.ADMIN, // Admin only
    },
    {
      id: "dashboard",
      label: "Dashboard",
      icon: Dashboard,
      url: "/dashboard",
      permission: PERMISSIONS.USER, // Available to all users
    },
    {
      id: "pos",
      label: "POS",
      icon: PointOfSale,
      url: "/pos",
      permission: PERMISSIONS.USER, // Available to all users
    },
    {
      id: "products",
      label: "Products",
      icon: Inventory,
      url: "/products",
      permission: PERMISSIONS.USER, // Available to all users
    },
    {
      id: "orders",
      label: "Orders",
      icon: Receipt,
      url: "/orders",
      permission: PERMISSIONS.USER, // Available to all users
    },
    {
      id: "reports",
      label: "Reports",
      icon: Assessment,
      url: "/reports",
      permission: PERMISSIONS.USER, // Available to all users
    },
  ];

  // Filter tabs based on user permission
  const tabs = allTabs.filter((tab) => {
    if (!user) return false;
    return hasPermission(user.permission, tab.permission);
  });

  return (
    <nav className="bg-gray-50 border-b border-gray-200 px-4 py-2">
      <div className="max-w-7xl mx-auto">
        <div className="flex space-x-1">
          {tabs.map((tab) => {
            const isActive =
              location.pathname === tab.url ||
              (tab.url === "/dashboard" && location.pathname === "/");
            const IconComponent = tab.icon;

            return (
              <Link
                to={tab.url}
                key={tab.id}
                className={`flex items-center space-x-2 px-4 py-2 rounded-lg text-sm font-medium transition-all duration-200 ${
                  isActive
                    ? "bg-blue-100 text-blue-700 shadow-sm"
                    : "text-gray-600 hover:bg-gray-100 hover:text-gray-800"
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
