import { AppBar, Button, ClickAwayListener, Container, Grid, Modal, Toolbar, Typography, MenuItem, Tooltip, IconButton, Avatar, Menu } from "@mui/material"
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import MuiLogin from "./MuiLogin";
import Register from "./Register";
import AlertSuccess from "./AlertSuccess";

import useRefreshToken from "../hooks/useRefreshToken"
import useLogout from "../hooks/useLogout";
import { useNavigate } from "react-router-dom";

const UserInfo = () => {
  const logout = useLogout();
  const navigate = useNavigate();

  const username = useSelector((state) => state.auth.value.username);
  const roles = useSelector((state) => state.auth.value.roles);
  const settings = roles.includes('INSTRUCTOR') ? ['Student', 'Instructor', 'Account Settings', 'Logout'] : ['Student', 'Account Settings', 'Logout'];

  const [anchorElUser, setAnchorElUser] = useState(null);
  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const signOut = async (e) => {
    await logout();
    navigate('/');
  }

  const handleCloseUserMenu = (event) => {
    setAnchorElUser(null);
    switch (event.currentTarget.dataset.myValue) {
      case 'Student': navigate('/student');
        break;
      case 'Instructor': navigate('/instructor');
        break;
      case 'Account Settings': navigate('/account-manage');
        break;
      case 'Logout': signOut();
        break;
      default: break;
    }
  };

  return (
    <Grid item xs={9}>
      <Grid
        container
        alignItems='center'
        justifyContent='flex-end'
        spacing={2}
      >
        <Grid item>
          <Tooltip title="Open settings">
            <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
              <Avatar alt={username} src="/static/images/avatar/2.jpg" />
            </IconButton>
          </Tooltip>
          <Menu
            sx={{ mt: '45px' }}
            id="menu-appbar"
            anchorEl={anchorElUser}
            anchorOrigin={{
              vertical: 'top',
              horizontal: 'right',
            }}
            keepMounted
            transformOrigin={{
              vertical: 'top',
              horizontal: 'right',
            }}
            open={Boolean(anchorElUser)}
            onClose={handleCloseUserMenu}
          >
            {settings.map((setting) => (
              <MenuItem key={setting} data-my-value={setting} onClick={handleCloseUserMenu}>
                <Typography textAlign="center">{setting}</Typography>
              </MenuItem>
            ))}
          </Menu>
        </Grid>
      </Grid>
    </Grid>
  )
}

const SignInSignUp = () => {

  const [openSignIn, setOpenSignIn] = useState(false);
  const handleOpenSignIn = () => setOpenSignIn(true);
  const handleCloseSignIn = () => {
    setOpenSignIn(false);
  }

  const [openSignUp, setOpenSignUp] = useState(false)
  const [registerSuccess, setRegisterSuccess] = useState(false)

  const handleToggleOpenSignUp = () => setOpenSignUp(!openSignUp)
  const toggleRegisterSuccess = () => setRegisterSuccess(!registerSuccess)

  const onRegisterSuccess = () => {
    handleToggleOpenSignUp()
    toggleRegisterSuccess()
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
          <Button onClick={handleToggleOpenSignUp} variant="contained">
            Sign Up
          </Button>
          <Modal open={openSignUp} onClose={handleToggleOpenSignUp}>
            <div>
              <ClickAwayListener onClickAway={handleToggleOpenSignUp}>
                <Container component="main" maxWidth="xs">
                  <Register success={onRegisterSuccess} />
                </Container>
              </ClickAwayListener>
            </div>
          </Modal>
        </Grid>
        <Modal open={registerSuccess} onClose={toggleRegisterSuccess}>
          <div>
            <ClickAwayListener onClickAway={toggleRegisterSuccess}>
              <Container component="main" maxWidth="xs">
                <AlertSuccess
                  handleClick={toggleRegisterSuccess}
                  text={"Register successful."}
                  labelButton="Done"
                />
              </Container>
            </ClickAwayListener>
          </div>
        </Modal>
      </Grid>
    </Grid>
  )
}

const MuiAppbar = () => {

  const [isLoading, setIsLoading] = useState(true);


  const refresh = useRefreshToken();

  const username = useSelector((state) => state.auth.value.username);
  const persist = useSelector((state) => state.auth.value.persist);

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
      <AppBar color="inherit" elevation={1}>
        <Container maxWidth="lg">
          <Toolbar>
            <Grid container alignItems='center' spacing={2}>
              <Grid item xs={3}>
                <Typography variant="h6">
                  V-Ed
                </Typography>
              </Grid>
              {
                !persist
                  ? username
                    ? <UserInfo />
                    : <SignInSignUp />
                  : isLoading
                    ? <p>Loading...</p>
                    : username
                      ? <UserInfo />
                      : <SignInSignUp />
              }
            </Grid >
          </Toolbar >
        </Container >
      </AppBar >

      {/* Fixd Replacement see: https://mui.com/components/app-bar/#fixed-placement */}
      < Toolbar />
    </>
  )
}

export default MuiAppbar;