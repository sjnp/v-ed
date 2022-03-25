import { useDispatch, useSelector } from "react-redux";
import { reset } from "../features/authSlice";
import axios from "../api/axios";
// import useAuth from "./useAuth"

const useLogout = () => {
  // const { setAuth } = useAuth();
  const dispatch = useDispatch();
  const username = useSelector((state) => state.auth.username);

  const logout = async () => {
    // setAuth({});
    dispatch(reset());
    console.log(username);
    try {
      const response = await axios.get('/api/token/clear', {
        withCredentials: true
      });

    } catch (err) {
      console.error(err);
    }
  }

  return logout;
}

export default useLogout;