import { AppBar, Button, ClickAwayListener, Container, Grid, Modal, Toolbar, Typography } from "@mui/material"
import { useState } from "react";
import { useSelector } from "react-redux";
import MuiLogin from "./MuiLogin";
import Register from "./Register";
import AlertSuccess from "./AlertSuccess";


const MuiAppbar = () => {

  const [ openSignIn, setOpenSignIn ] = useState(false)
  const [ openSignUp, setOpenSignUp ] = useState(false)
  const [ registerSuccess, setRegisterSuccess ] = useState(false)

  const handleToggleOpenSignIn = () => setOpenSignIn(!openSignIn)
  const handleToggleOpenSignUp = () => setOpenSignUp(!openSignUp)
  const toggleRegisterSuccess = () => setRegisterSuccess(!registerSuccess)

  const onRegisterSuccess = () => {
    handleToggleOpenSignUp()
    toggleRegisterSuccess()
  }

  const username = useSelector((state) => state.auth.username);

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
                username
                ?
                (
                  <Grid item xs={9}>
                    <Grid container alignItems='center' justifyContent='flex-end' spacing={2}>
                      <Grid item>
                        <Typography>
                          {username}
                        </Typography>
                      </Grid>
                    </Grid>
                  </Grid>
                ) 
                :
                (
                  <Grid item xs={9}>
                    <Grid container alignItems='center' justifyContent='flex-end' spacing={2}>
                      <Grid item>
                        <Button onClick={handleToggleOpenSignIn}>Sign In</Button>
                        <Modal open={openSignIn} onClose={handleToggleOpenSignIn}>
                          <div>
                            <ClickAwayListener onClickAway={handleToggleOpenSignIn}>
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