import { AppBar, Container, Grid, Toolbar, Typography } from "@mui/material"
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import useRefreshToken from "../hooks/useRefreshToken"
import SignInSignUpModals from "./SignInSignUpModals";
import UserMenu from "./UserMenu";

const AppBarHeader = () => {

  const [isLoading, setIsLoading] = useState(true);

  const refresh = useRefreshToken();

  const username = useSelector((state) => state.auth.value.username);
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
    !username ? verifyRefreshToken() : setIsLoading(false);

    return () => isMounted = false;
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  return (
    <>
      <AppBar
        color="inherit"
        elevation={1}
      >
        <Container maxWidth="lg">
          <Toolbar>

            <Grid
              container
              alignItems='center'
              spacing={2}
            >
              <Grid item xs={3}>
                <Typography
                  variant="h6"
                >
                  V-Ed
                </Typography>
              </Grid>
              {
                !persist
                  ? username
                    ? <UserMenu />
                    : <SignInSignUpModals />
                  : isLoading
                    ? <p>Loading...</p>
                    : username
                      ? <UserMenu />
                      : <SignInSignUpModals />
              }
            </Grid>

          </Toolbar>

        </Container>
      </AppBar>

      {/* Fixed Replacement see: https://mui.com/components/app-bar/#fixed-placement */}
      <Toolbar />
    </>
  )
}

export default AppBarHeader;