import React, { useState } from "react";
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Container from '@mui/material/Container';
import { Box, Button, CssBaseline, Paper, TextField, Typography } from '@mui/material';
import { URL_REGISTER } from '../utils/url'
import { validation } from '../utils/validation'
import axios from "../api/axios";

const Register = () => {

  const theme = createTheme();

  const initRegister = {
    email: '',
    password: '',
    confirmPassword: '',
    firstname: '',
    lastname: ''
  }
  
  const initRegister2 = {
    email: false,
    password: false,
    confirmPassword: false,
    firstname: false,
    lastname: false
  }

  const [ register, setRegister ] = useState(initRegister)
  const [ error, setError ] = useState(initRegister2)
  const [ messageError, setMessageError ] = useState(initRegister)

  const onChange = async (event) => {
    handleChange(event.target.name, event.target.value)
  }

  const handleChange = (name, value) => {
    setRegister({ ...register, [name]: value })
    const message = verify(name, value)
    console.log(message)

    const isError = message === '' ? false : true
    setError({ ...error, [name]: isError })
    setMessageError({ ...messageError, [name]: message })
    console.log('error => ', error)
  }

  const verify = (name, value) => {
    if (name === 'firstname') {
      return validation.validateFirstname(value)
    } else if (name === 'lastname') {
      return validation.validateLastname(value)
    } else if (name === 'email') {
      return validation.validateEmail(value)
    } else if (name === 'password') {
      return validation.validatePassword(value)
    } else if (name === 'confirmPassword') {
      return validation.validateConfirmPassword(register.password, value)
    }
  }

  const onRegister = (event) => {
    event.preventDefault()

    if (checkErrorBeforeRegister('firstname')) {
      handleChange('firstname', register.firstname)
      return
    } else if (checkErrorBeforeRegister('lastname')) {
      handleChange('lastname', register.lastname)
      return
    } else if (checkErrorBeforeRegister('email')) {
      handleChange('email', register.email)
      return
    } else if (checkErrorBeforeRegister('password')) {
      handleChange('password', register.password)
      return
    } else if (checkErrorBeforeRegister('confirmPassword')) {
      handleChange('confirmPassword', register.confirmPassword)
      return
    }

    const payLoad = {
      username: register.email,
      password: register.password,
      personalInfo: {
        firstName: register.firstname,
        lastName: register.lastname,
      }
    }

    axios.post(URL_REGISTER, payLoad)
  }

  const checkErrorBeforeRegister = (name) => {
    return register[name] === '' || messageError[name] !== '' ? true : false
  }

  const sxBox = {
    marginTop: 3,
    marginLeft: 3,
    marginRight: 3,
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center'
  }

  const sxButton = { 
    marginTop: 6,
    marginBottom: 2
  }

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Paper elevation={3}>
          <Box sx={sxBox}>
            <Typography marginTop={3} component='h1' variant='h5'>
              Register
            </Typography>
            <Box component="form" onSubmit={onRegister} noValidate sx={{ marginTop: 1 }}>
              <TextField
                margin='normal'
                autoComplete='off'
                autoFocus
                required
                fullWidth
                id='firstname'
                label='First name'
                name='firstname'
                type='text'
                error={error.firstname}
                helperText={messageError.firstname}
                value={register.firstname}
                onChange={onChange}
              />
              <TextField
                margin='normal'
                autoComplete='off'
                autoFocus
                required
                fullWidth
                id='lastname'
                label='Last name'
                name='lastname'
                type='text'
                error={error.lastname}
                helperText={messageError.lastname}
                value={register.lastname}
                onChange={onChange}
              />
              <TextField
                margin='normal'
                autoComplete='off'
                autoFocus
                required
                fullWidth
                id='email'
                label='E-Mail Address'
                name='email'
                type='email'
                error={error.email}
                helperText={messageError.email}
                value={register.email}
                onChange={onChange}
              />
              <TextField
                margin='normal'
                autoComplete='off'
                autoFocus
                required
                fullWidth
                id='password'
                label='Password'
                name='password'
                type='password'
                error={error.password}
                helperText={messageError.password}
                value={register.password}
                onChange={onChange}
              />
              <TextField
                margin='normal'
                autoComplete='off'
                autoFocus
                required
                fullWidth
                id='password'
                label='Confrim Password'
                name='confirmPassword'
                type='password'
                error={error.confirmPassword}
                helperText={messageError.confirmPassword}
                value={register.confirmPassword}
                onChange={onChange}
              />
              <Button type='submit' fullWidth variant='contained'  sx={sxButton}>
                Register
              </Button>
            </Box>
          </Box>
        </Paper>
      </Container>
    </ThemeProvider>
  )
}

export default Register;