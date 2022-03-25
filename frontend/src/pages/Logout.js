import { useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import useLogout from "../hooks/useLogout";

const Logout = () => {

  const navigate = useNavigate();
  const username = useSelector((state) => state.auth.username);

  useEffect(() => {
    console.log(username);

    console.log("u are in logout page now");
    navigate('/');
  });

  return (
    <>

    </>
  )
}

export default Logout;