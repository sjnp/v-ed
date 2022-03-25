import React, { useState } from "react";

import { createTheme, ThemeProvider } from '@mui/material/styles';
import Container from '@mui/material/Container';
import { Box, Button, CssBaseline, Paper, TextField, Typography } from '@mui/material';

import { validation } from '../utils/validation'
import service from '../services/service'

const Register = ({ success }) => {

  const theme = createTheme()

  const [ register, setRegister ] = useState({
    email: '',
    password: '',
    confirmPassword: '',
    firstname: '',
    lastname: ''
  })

  const [ messageError, setMessageError ] = useState({
    email: '',
    password: '',
    confirmPassword: '',
    firstname: '',
    lastname: ''
  })

  const [ error, setError ] = useState({
    email: false,
    password: false,
    confirmPassword: false,
    firstname: false,
    lastname: false
  })

  const onChange = async (event) => {
    handleChange(event.target.name, event.target.value)
  }

  const handleChange = (name, value) => {
    setRegister({
      ...register, 
      [name]: value
    })

    const errMsg = verify(name, value) // verify ok errMsg = '', verify fail errMsg = '...'
    
    setError({
      ...error, 
      [name]: errMsg ? true : false
    })
    
    setMessageError({ 
      ...messageError,
      [name]: errMsg
    })
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

  const onRegister = async (event) => {
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

    const result = await service.register(payLoad)

    if (result) {
      success()
    } else {
      setMessageError({
        ...messageError,
        email: `${register.email} duplicate`
      })

      setError({
        ...error,
        email: true
      })
    }
  }

  const checkErrorBeforeRegister = (name) => {
    return register[name] === '' || messageError[name] !== '' ? true : false
  }

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Paper elevation={3}>
          <Box sx={{marginTop: 3, marginLeft: 3, marginRight: 3, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
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
                required
                fullWidth
                id='confirmPassword'
                label='Confrim Password'
                name='confirmPassword'
                type='password'
                error={error.confirmPassword}
                helperText={messageError.confirmPassword}
                value={register.confirmPassword}
                onChange={onChange}
              />
              <Button type='submit' fullWidth variant='contained'  sx={{ marginTop: 6, marginBottom: 2 }}>
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