import { useSelector } from "react-redux";
import { useLocation, Navigate, Outlet } from "react-router-dom";
// import useAuth from "../hooks/useAuth";

const RequireAuth = ({ allowRoles }) => {
  // const { auth } = useAuth();
  const roles = useSelector((state) => state.auth.roles);
  const username = useSelector((state) => state.auth.username);

  const location = useLocation();


  return (
    roles.find(role => allowRoles?.includes(role))
      ? <Outlet />
      : username
        ? <Navigate to="/unauthorized" state={{ from: location }} replace />
        : <Navigate to="/" state={{ from: location }} replace />
  );
}

export default RequireAuth;