import React, { useState } from "react"

// Material UI component
import  Button from "@mui/material/Button"
import  Container from "@mui/material/Container"
import  Grid from "@mui/material/Grid"
import  Modal from "@mui/material/Modal"

// component
import SignInForm from "./SignInForm"
import SignUpForm from "./SignUpForm"
import SuccessAlertBox from "./SuccessAlertBox"

const SignInSignUpModals = () => {

  const [ openSignIn, setOpenSignIn ] = useState(false)
  const [ openSignUp, setOpenSignUp ] = useState(false)

  const [ openSignUpSuccess, setOpenSignUpSuccess ] = useState(false)

  const handleSignUpSuccess = () => {
    setOpenSignUp(false)
    setOpenSignUpSuccess(true)
  }

  const handClickSignUpLoginForm = () => {
    setOpenSignIn(false)
    setOpenSignUp(true)
  }

  return (
    <Grid container alignItems='center' justifyContent='flex-end' spacing={2}>
      <Grid item>
        <Button onClick={() => setOpenSignIn(true)}>Sign In</Button>
        <Modal open={openSignIn} onClose={() => setOpenSignIn(false)}>
          <Container component="main" maxWidth="xs">
            <SignInForm onSignIn={handClickSignUpLoginForm} />
          </Container>
        </Modal>
      </Grid>
      <Grid item>
        <Button onClick={() => setOpenSignUp(true)} variant="contained">Sign Up</Button>
        <Modal open={openSignUp}>
          <Container component="main" maxWidth="xs">
            <SignUpForm onSuccess={handleSignUpSuccess} onClose={() => setOpenSignUp(false)} />
          </Container>
        </Modal>
        <Modal open={openSignUpSuccess} onClose={() => setOpenSignUpSuccess(false)}>
          <Container component="main" maxWidth="xs">
            <SuccessAlertBox handleClick={() => setOpenSignUpSuccess(false)} text='Register successful' />
          </Container>
        </Modal>
      </Grid>
    </Grid>
  )
}

export default SignInSignUpModals;