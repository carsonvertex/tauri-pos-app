import * as React from "react";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import Button from "@mui/material/Button";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import { hasPermission, PERMISSIONS } from "../types/permissions";
import { useAuth } from "../contexts/AuthContext";
import { Assessment, Receipt, Dashboard, Sync } from "@mui/icons-material";
import { Inventory } from "@mui/icons-material";
import { PointOfSale } from "@mui/icons-material";
import { useNavigate } from "react-router-dom";
import MenuIcon from "@mui/icons-material/Menu";
type Anchor = "top" | "left" | "bottom" | "right";

export default function NavDrawer() {
  const [state, setState] = React.useState({
    top: false,
    left: false,
    bottom: false,
    right: false,
  });
  const { user } = useAuth();
  const navigate = useNavigate();
  const allTabs = [
    {
      id: "sync",
      label: "Sync",
      icon: Sync,
      url: "/sync",
      permission: PERMISSIONS.ADMIN, // Admin only
    },
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

  const tabs = allTabs.filter((tab) => {
    if (!user) return false;
    return hasPermission(user.permission, tab.permission);
  });

  const toggleDrawer =
    (anchor: Anchor, open: boolean) =>
    (event: React.KeyboardEvent | React.MouseEvent) => {
      if (
        event.type === "keydown" &&
        ((event as React.KeyboardEvent).key === "Tab" ||
          (event as React.KeyboardEvent).key === "Shift")
      ) {
        return;
      }

      setState({ ...state, [anchor]: open });
    };

  const list = (anchor: Anchor) => (
    <Box
      sx={{ width: anchor === "top" || anchor === "bottom" ? "auto" : 250 }}
      role="presentation"
      onClick={toggleDrawer(anchor, false)}
      onKeyDown={toggleDrawer(anchor, false)}
    >
      <List>
        {tabs.map((text, index) => (
          <ListItem key={index} disablePadding>
            <ListItemButton
              onClick={() => {
                navigate(text.url);
              }}
            >
              <ListItemIcon>{React.createElement(text.icon)}</ListItemIcon>
              <ListItemText primary={text.label} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </Box>
  );

  return (
    <div className="w-24 cursor-pointer hover:bg-gray-100   h-full flex items-center justify-center">
      <React.Fragment key="left">
        <div
          onClick={toggleDrawer("left", true)}
          className="   h-full w-full flex items-center justify-center"
        >
          <MenuIcon className="w-6 h-6" />
        </div>
        <Drawer
          anchor="left"
          open={state["left"]}
          onClose={toggleDrawer("left", false)}
        >
          {list("left")}
        </Drawer>
      </React.Fragment>
    </div>
  );
}
