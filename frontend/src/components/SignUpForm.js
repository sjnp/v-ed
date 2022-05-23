import React, { useState } from "react"

// Material UI component
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import Paper from '@mui/material/Paper'
import TextField from '@mui/material/TextField'
import Typography from '@mui/material/Typography'
import IconButton from '@mui/material/IconButton'
import InputAdornment from '@mui/material/InputAdornment'
import Grid from '@mui/material/Grid'

// Material UI icon
import VisibilityOff from "@mui/icons-material/VisibilityOff"
import Visibility from "@mui/icons-material/Visibility"
import CloseIcon from "@mui/icons-material/Close"

// utils
import { validation } from '../utils/validation'

// service
import service from '../services/service'

const SignUpForm = ({ onSuccess, onClose }) => {

  const [register, setRegister] = useState({
    email: '',
    password: '',
    confirmPassword: '',
    firstname: '',
    lastname: ''
  })

  const [messageError, setMessageError] = useState({
    email: '',
    password: '',
    confirmPassword: '',
    firstname: '',
    lastname: ''
  })

  const [error, setError] = useState({
    email: false,
    password: false,
    confirmPassword: false,
    firstname: false,
    lastname: false
  })

  const [ showPassword, setShowPassword ] = useState(false)
  const [ showConfirmPassword, setShowConfirmPassword ] = useState(false)

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
      student: {
        firstName: register.firstname,
        lastName: register.lastname,
      }
    }

    const result = await service.register(payLoad)

    if (result.status === 201) {
      onSuccess()
    } else if (result.status === 409) {
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
    <Paper elevation={3} sx={{ mt: 3 }}>
      <Grid container pt={3}>
        <Grid item xs={2}></Grid>
        <Grid item xs={8}>
          <Grid container direction="row" alignItems="center" justifyContent="center">
            <Typography variant='h5'>Register</Typography>
          </Grid>
        </Grid>
        <Grid item xs={2}>
          <IconButton onClick={onClose} title='Close sign up form'>
            <CloseIcon />
          </IconButton>
        </Grid>
      </Grid>
      <Box ml={3} mr={3} display='flex' flexDirection='column' alignItems='center'>
        <Box component="form" onSubmit={onRegister} noValidate sx={{ mt: 1 }}>
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
            type={showPassword ? 'text' : 'password'}
            error={error.password}
            helperText={messageError.password}
            value={register.password}
            onChange={onChange}
            InputProps={{
              endAdornment: (
                <InputAdornment position="end">
                  <IconButton onClick={() => setShowPassword(!showPassword)} edge='end'>
                    { showPassword ? <VisibilityOff /> : <Visibility /> }
                  </IconButton>
                </InputAdornment>
              )
            }}
          />
          <TextField
            margin='normal'
            autoComplete='off'
            required
            fullWidth
            id='confirmPassword'
            label='Confrim Password'
            name='confirmPassword'
            type={showConfirmPassword ? 'text' : 'password'}
            error={error.confirmPassword}
            helperText={messageError.confirmPassword}
            value={register.confirmPassword}
            onChange={onChange}
            InputProps={{
              endAdornment: (
                <InputAdornment position="end">
                  <IconButton onClick={() => setShowConfirmPassword(!showConfirmPassword)} edge='end'>
                    { showConfirmPassword ? <VisibilityOff /> : <Visibility /> }
                  </IconButton>
                </InputAdornment>
              )
            }}
          />
          <Button type='submit' fullWidth variant='contained' sx={{ mt: 6, mb: 2 }}>
            Register
          </Button>
        </Box>
      </Box>
    </Paper>
  )
}

export default SignUpForm;