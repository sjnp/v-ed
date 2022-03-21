import { useLocation, Navigate, Outlet } from "react-router-dom";
import useAuth from "./hooks/useAuth";

const RequireAuth = ({ allowRoles }) => {
  const { auth } = useAuth();
  const location = useLocation();

  return (
    auth?.roles?.find(role => allowRoles?.includes(role))
      ? <Outlet />
      : auth?.username
        ? <Navigate to="/unauthorized" state={{ from: location }} replace />
        : <Navigate to="/login" state={{ from: location }} replace />
  );
}

export default RequireAuth;