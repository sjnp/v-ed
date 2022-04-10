import { AppBar, Container, Grid, Toolbar, Typography } from "@mui/material"
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { Navigate } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import useRefreshToken from "../hooks/useRefreshToken"
import SignInSignUpModals from "./SignInSignUpModals";
import UserMenu from "./UserMenu";

import SearchBox from "./SearchBox";

import { Box, Button, Link } from "@mui/material";

const AppBarSearchHeader = () => {

  const [isLoading, setIsLoading] = useState(true);

  const refresh = useRefreshToken();

  const username = useSelector((state) => state.auth.value.username);
  const persist = useSelector((state) => state.auth.value.persist);

  const navigate = useNavigate()
  const handleClickLogoVEd = () => navigate('/')

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
              <Grid item xs={3.5}>
                <Button varient="text" onClick={handleClickLogoVEd}>
                  <Typography variant="h6" color="text.primary">
                    V-Ed
                  </Typography>
                </Button>
              </Grid>
              <Grid item xs={5} >
                <SearchBox />
              </Grid>
              <Grid item xs={3.5}>
                {
                  !persist
                    ? username
                      ? <UserMenu />
                      : <SignInSignUpModals />
                    : isLoading
                      ? <Grid
                        container
                        alignItems='center'
                        justifyContent='flex-end'
                      >
                        <p>Loading...</p>
                      </Grid>
                      : username
                        ? <UserMenu />
                        : <SignInSignUpModals />
                }
              </Grid>
            </Grid>
          </Toolbar>
        </Container>
      </AppBar>
      {/* Fixed Replacement see: https://mui.com/components/app-bar/#fixed-placement */}
      <Toolbar />
    </>
  )
}

export default AppBarSearchHeader;