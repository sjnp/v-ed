import { Button, ClickAwayListener, Container, Grid, Modal } from "@mui/material";
import { useState } from "react";
import SignInForm from "./SignInForm";
import SignUpForm from "./SignUpForm";
import SuccessAlertBox from "./SuccessAlertBox"

const SignInSignUpModals = () => {
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
                  <SignInForm />
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
                  <SignUpForm success={onRegisterSuccess} />
                </Container>
              </ClickAwayListener>
            </div>
          </Modal>
          <Modal open={registerSuccess} onClose={toggleRegisterSuccess}>
            <div>
              <ClickAwayListener onClickAway={toggleRegisterSuccess}>
                <Container component="main" maxWidth="xs">
                  <SuccessAlertBox
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

export default SignInSignUpModals;