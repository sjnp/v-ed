import { AppBar, Button, ClickAwayListener, Container, Grid, Modal, Toolbar, Typography } from "@mui/material"
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import MuiLogin from "./MuiLogin";
import useRefreshToken from "../hooks/useRefreshToken"
import useLogout from "../hooks/useLogout";
import { useNavigate } from "react-router-dom";

const UserInfo = () => {
  const logout = useLogout();
  const navigate = useNavigate();

  const signOut = async (e) => {
    await logout();
    navigate('/logout');
  }

  return (
    <Grid item xs={9}>
      <Grid
        container
        alignItems='center'
        justifyContent='flex-end'
        spacing={2}
      >
        <Grid item>
          <Typography>Hello!</Typography>
        </Grid>
        <Grid item>
          <Button
            onClick={signOut}
          >
            Sign Out
          </Button>
        </Grid>
      </Grid>
    </Grid>
  )
}
const SignInSignOut = () => {
  const [openSignIn, setOpenSignIn] = useState(false);
  const handleOpenSignIn = () => setOpenSignIn(true);
  const handleCloseSignIn = () => {
    setOpenSignIn(false);
  }
  return (
    <Grid item xs={9}>
      <Grid
        container
        alignItems='center'
        justifyContent='flex-end'
        spacing={2}
      >
        <Grid item>
          <Button onClick={handleOpenSignIn}>Sign In</Button>
          <Modal
            open={openSignIn}
            onClose={handleCloseSignIn}
          >
            <div>
              <ClickAwayListener onClickAway={handleCloseSignIn}>
                <Container component="main" maxWidth="xs">
                  <MuiLogin />
                </Container>
              </ClickAwayListener>
            </div>
          </Modal>
        </Grid>
        <Grid item>
          <Button variant="contained">Sign Up</Button>
        </Grid>

      </Grid>
    </Grid>
  )
}
const MuiAppbar = () => {

  const [isLoading, setIsLoading] = useState(true);


  const refresh = useRefreshToken();

  const username = useSelector((state) => state.auth.username);
  const persist = useSelector((state) => state.auth.persist);

  useEffect(() => {
    console.log("hey use effect on mui appbar")
  }, [username])

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
                    ? <UserInfo />
                    : <SignInSignOut />
                  : isLoading
                    ? <p>Loading...</p>
                    : username
                      ? <UserInfo />
                      : <SignInSignOut />

              }
            </Grid>

          </Toolbar>

        </Container>
      </AppBar>

      {/* Fixd Replacement see: https://mui.com/components/app-bar/#fixed-placement */}
      <Toolbar />
    </>
  )
}

export default MuiAppbar;