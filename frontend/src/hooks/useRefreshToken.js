import axios from "../api/axios";
// import useAuth from "./useAuth";
import { setAuth, setTokenAndRoles } from "../features/authSlice";
import { useDispatch } from "react-redux";

const useRefreshToken = () => {
  // const { setAuth } = useAuth();
  const dispatch = useDispatch();

  const refresh = async () => {
    const response = await axios.get('/api/token/refresh', {
      withCredentials: true
    });
    // setAuth(prev => {
    //   return { ...prev,
    //     roles: response.data.roles, 
    //     access_token: response.data.access_token }
    // });
    console.log(response.data);
    dispatch(setAuth({
      username: response.data.username,
      roles: response.data.roles,
      access_token: response.data.access_token
    }))
    return response.data.access_token;
  }

  return refresh;
};

export default useRefreshToken;