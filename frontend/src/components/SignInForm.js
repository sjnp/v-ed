import React,  { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link as RouterLink, useNavigate, useLocation } from 'react-router-dom';

// Material UI component
import IconButton from '@mui/material/IconButton'
import InputAdornment from '@mui/material/InputAdornment'
import Alert from '@mui/material/Alert'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import Checkbox from '@mui/material/Checkbox'
import FormControlLabel from '@mui/material/FormControlLabel'
import Grid from '@mui/material/Grid'
import Link from '@mui/material/Link'
import Paper from '@mui/material/Paper'
import TextField from '@mui/material/TextField'
import Typography from '@mui/material/Typography'

// Matetail UI icon
import Login from '@mui/icons-material/Login'
import Visibility from '@mui/icons-material/Visibility'
import VisibilityOff from '@mui/icons-material/VisibilityOff'

// custom api
import axios from '../api/axios';

// feature slice
import { setAuth, setPersist } from '../features/authSlice';

// url
import { URL_LOGIN } from '../utils/url'

// regex
const USERNAME_REGEX = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

const SignInForm = () => {
  
  const persist = useSelector((state) => state.auth.value.persist);
  const dispatch = useDispatch();

  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || "/";

  const [username, setUsername] = useState('');
  const [errorUsername, setErrorUsername] = useState(false);
  const [usernameDidBlur, setUsernameDidBlur] = useState(false);
  const [usernameHelperText, setUsernameHelperText] = useState('');

  const [password, setPassword] = useState('');
  const [errorPassword, setErrorPassword] = useState(false);
  const [passwordDidBlur, setPasswordDidBlur] = useState(false);
  const [passwordHelperText, setPasswordHelperText] = useState('');

  const [showPassword, setShowPassword] = useState(false)

  const [loginErrorMsg, setLoginErrorMsg] = useState('');

  useEffect(() => {
    let isMounted = true;
    isMounted && setLoginErrorMsg('');

    return () => isMounted = false;
  }, [username, password])

  const setRequiredUsernameMsg = () => {
    setErrorUsername(true);
    setUsernameHelperText('Required');
  }

  const setValidUsernameMsg = () => {
    setErrorUsername(false);
    setUsernameHelperText('');
  }

  const setInvalidUsernameMsg = () => {
    setErrorUsername(true);
    setUsernameHelperText('Invalid e-mail address')
  }

  const setRequiredPasswordMsg = () => {
    setErrorPassword(true);
    setPasswordHelperText('Required');
  }

  const setValidPasswordMsg = () => {
    setErrorPassword(false);
    setPasswordHelperText('');
  }

  const handleUsernameChange = (event) => {
    const usernameInput = event.target.value;
    setUsername(usernameInput);
    if (usernameDidBlur) {
      if (!usernameInput) {
        setRequiredUsernameMsg();
      } else {
        const result = USERNAME_REGEX.test(usernameInput);
        if (result) {
          setValidUsernameMsg();
        } else {
          setInvalidUsernameMsg();
        }
      }
    }
  }

  const handleUsernameBlur = (event) => {
    const usernameInput = event.target.value;
    if (usernameDidBlur && !usernameInput) {
      setRequiredUsernameMsg();
    } else if (usernameInput) {
      const result = USERNAME_REGEX.test(username);
      if (!result) {
        setInvalidUsernameMsg();
      }
    }
    setUsernameDidBlur(true);
  }

  const handlePasswordChange = (event) => {
    const passwordInput = event.target.value;
    setPassword(passwordInput);
    if (passwordDidBlur) {
      if (!passwordInput) {
        setRequiredPasswordMsg();
      } else {
        setValidPasswordMsg();
      }
    }
  }

  const handlePasswordBlur = (event) => {
    const passwordInput = event.target.value;
    if (passwordDidBlur && !passwordInput) {
      setRequiredPasswordMsg();
    }
    setPasswordDidBlur(true);
  }

  const togglePersist = () => {
    dispatch(setPersist(!persist));
  }

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (!username || !password || usernameHelperText) {
      setPasswordDidBlur(true);
      if (!username) {
        setRequiredUsernameMsg();
      } else if (usernameHelperText) {
        setInvalidUsernameMsg();
      }
      if (!password) {
        setRequiredPasswordMsg();
      }
    } else {
      try {
        const response = await axios.post(URL_LOGIN,
          JSON.stringify({ username, password }),
          {
            headers: { 'Content-Type': 'application/json' },
            withCredentials: true
          }
        );
        const access_token = response?.data?.access_token;
        const roles = response?.data?.roles;

        dispatch(setAuth({ username, roles, access_token}))
        setUsername('');
        setPassword('');
        navigate(from, { replace: true });
      } catch (err) {
        if (!err?.response) {
          setLoginErrorMsg('No Server Response');
        } else if (err.response?.status === 400) {
          setLoginErrorMsg('Missing Username or Password');
        } else if (err.response?.status === 401) {
          setLoginErrorMsg('Wrong Username or Password');
        } else if (err.response?.status === 403) {
          setLoginErrorMsg('Wrong Username or Password');
        } else {
          setLoginErrorMsg('Login Failed');
        }
      }
    }
  }

  return (
    <Paper elevation={3}>
      <Box sx={{ mt: 12, ml: 3, mr: 3, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <Typography marginTop={3} component='h1' variant='h5'>Sign In</Typography>
        <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
          <TextField
            margin='normal'
            autoComplete='off'
            autoFocus
            required
            fullWidth
            id='username'
            label='Email Address'
            name='email'
            type='email'
            error={errorUsername}
            helperText={usernameHelperText}
            value={username}
            onChange={handleUsernameChange}
            onBlur={handleUsernameBlur}
          />
          <TextField
            margin='normal'
            sx={{ marginTop: 3 }}
            required
            fullWidth
            id='password'
            label='Password'
            name='password'
            type={showPassword ? 'text' : 'password'}
            error={errorPassword}
            helperText={passwordHelperText}
            value={password}
            onChange={handlePasswordChange}
            onBlur={handlePasswordBlur}
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
          <FormControlLabel
            sx={{ mt: 2 }}
            control={<Checkbox value="persist" id='persist' onChange={togglePersist} checked={persist} /> }
            label="Remember this device"
          />
          { loginErrorMsg ? <Alert sx={{ mt: 3 }} severity='error'>{loginErrorMsg}</Alert> : null }
          <Button
            type='submit' 
            fullWidth 
            variant='contained' 
            size='large' 
            sx={{ mt: 6, mb: 2 }}
            startIcon={<Login />}
          >
            sign in
          </Button>
        </Box>
        <Grid container justifyContent="center" marginTop={3} marginBottom={3} spacing={1}>
          <Typography>Not have an account yet?</Typography>
          <Link component={RouterLink} to="/register">Sign up</Link>
        </Grid>
      </Box>
    </Paper>
  )
}

export default SignInForm; 