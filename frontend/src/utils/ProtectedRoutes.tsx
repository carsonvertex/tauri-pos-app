import { Navigate, Outlet } from "react-router-dom";
const ProtectedRoutes = ({ user }: { user: { login: boolean } }) => {
  return user.login ? <Outlet /> : <Navigate to="/login" />;
};

export default ProtectedRoutes;
