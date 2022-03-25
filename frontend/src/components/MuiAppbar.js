import { AppBar, Button, ClickAwayListener, Container, Grid, Modal, Toolbar, Typography, MenuItem, Tooltip, IconButton, Avatar, Menu } from "@mui/material"
import { useState } from "react";
import { useSelector,useDispatch } from "react-redux";
import MuiLogin from "./MuiLogin";
import { useNavigate } from "react-router-dom";
import useLogout from "../hooks/useLogout";

const MuiAppbar = () => {
  const navigate = useNavigate();
  const logout = useLogout();
  const username = useSelector((state) => state.auth.value.username);
  const roles = useSelector((state) => state.auth.value.roles);  
  const settings = roles.includes('INSTRUCTOR') ? ['Student', 'Instructor', 'Account Settings', 'Logout'] : ['Student', 'Account Settings', 'Logout'] ;

  const [openSignIn, setOpenSignIn] = useState(false);
  const handleOpenSignIn = () => setOpenSignIn(true);
  const handleCloseSignIn = () => {
    setOpenSignIn(false);
  }
  const [anchorElUser, setAnchorElUser] = useState(null);
  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };
  
  console.log(username)

  const signOut = async () => {
    await logout();
  }

  const handleCloseUserMenu = (event) => {
    setAnchorElUser(null);
    switch (event.currentTarget.dataset.myValue) {
      case 'Student'          : navigate('/student'); 
                                break;
      case 'Instructor'       : navigate('/instructor'); 
                                break;
      case 'Account Settings' : navigate('/account-manage'); 
                                break;
      case 'Logout'           : signOut();
                                navigate('/home'); 
                                break;
      default : break;
    }
  };

  
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
              {username
                ? <Grid item xs={9}>
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

                  : (<Grid item xs={9}>
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
                  </Grid>)}
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