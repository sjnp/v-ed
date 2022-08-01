import React, { useState } from "react"

// Material UI component
import  Button from "@mui/material/Button"
import  Container from "@mui/material/Container"
import  Grid from "@mui/material/Grid"
import  Modal from "@mui/material/Modal"

// component
import SignInForm from "./SignInForm"
import SignUpForm from "./SignUpForm"
import AlertMessage from "./AlertMessage"

// custom hook
import useAlertMessage from '../hooks/useAlertMessage'

const SignInSignUpModals = () => {

  const alertMessage = useAlertMessage()

  const [ openSignIn, setOpenSignIn ] = useState(false)
  const [ openSignUp, setOpenSignUp ] = useState(false)

  const handleSignUpSuccess = () => {
    setOpenSignUp(false)
    alertMessage.show('success', 'Register successful')
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
            <SignInForm onSignIn={handClickSignUpLoginForm} onSignUp={handClickSignUpLoginForm} />
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
        <AlertMessage
          open={alertMessage.getOpen()} 
          type={alertMessage.getType()}
          message={alertMessage.getMessage()}
          onClose={alertMessage.close}
        />
      </Grid>
    </Grid>
  )
}

export default SignInSignUpModals;