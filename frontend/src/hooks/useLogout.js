import { useDispatch } from "react-redux";
import { reset } from "../features/authSlice";
import axios from "../api/axios";
// import useAuth from "./useAuth"

const useLogout = () => {
  // const { setAuth } = useAuth();
  const dispatch = useDispatch();

  const logout = async () => {
    // setAuth({});
    try {
      const response = await axios.get('/api/token/clear', {
        withCredentials: true
      });

      dispatch(reset());
    } catch (err) {
      console.error(err);
    }
  }

  return logout;
}

export default useLogout;