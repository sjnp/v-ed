import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { Outlet } from "react-router-dom";
// import useAuth from "../hooks/useAuth";
import useRefreshToken from "../hooks/useRefreshToken";

const PersistLogin = () => {
  const [isLoading, setIsLoading] = useState(true);
  const refresh = useRefreshToken();
  // const { auth, persist } = useAuth();
  const access_token = useSelector((state) => state.auth.value.access_token);
  const persist = useSelector((state) => state.auth.value.persist);

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
    !access_token ? verifyRefreshToken() : setIsLoading(false);

    return () => isMounted = false;
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  // useEffect(() => {
  //   console.log(`isLoading: ${isLoading}`);
  //   console.log(`access_token: ${JSON.stringify(auth?.access_token)}`);
  // }, [isLoading])

  return (
    <>
      {!persist
        ? <Outlet />
        : isLoading
          ? <p>Loading...</p>
          : <Outlet />
      }
    </>
  )
}

export default PersistLogin;