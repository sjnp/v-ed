import { useEffect, useState } from "react";
import { Outlet } from "react-router-dom";
import useAuth from "../hooks/useAuth";
import useRefreshToken from "../hooks/useRefreshToken";

const PersistLogin = () => {
  const [isLoading, setIsLoading] = useState(true);
  const refresh = useRefreshToken();
  const { auth } = useAuth();

  useEffect(() => {
    let isMounted = true;
    const verifyRefreshToken = async () => {
      try {
        await refresh();
      } catch (err) {
        console.error(err);
      } finally {
        isMounted && setIsLoading(false);
      }
    }
    !auth?.access_token ? verifyRefreshToken() : setIsLoading(false);

    return () => isMounted = false;
  }, [])

  useEffect(() => {
    console.log(`isLoading: ${isLoading}`);
    console.log(`access_token: ${JSON.stringify(auth?.access_token)}`);
  }, [isLoading])

  return (
    <>
      {isLoading
        ? <p>Loading...</p>
        : <Outlet />
      }
    </>
  )
}

export default PersistLogin;