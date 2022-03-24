import { AppBar, Button, ClickAwayListener, Container, Grid, Modal, Toolbar, Typography } from "@mui/material"
import { useState } from "react";
import MuiLogin from "./MuiLogin";

const MuiAppbar = () => {
  const [openSignIn, setOpenSignIn] = useState(false);
  const handleOpenSignIn = () => setOpenSignIn(true);
  const handleCloseSignIn = () => {
    setOpenSignIn(false);
  }

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