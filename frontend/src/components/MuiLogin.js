import { useState, useEffect } from 'react';
import useAuth from '../hooks/useAuth';
import { Link as RouterLink, useNavigate, useLocation } from 'react-router-dom';

import axios from '../api/axios';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Container from '@mui/material/Container';
import { Alert, Box, Button, CssBaseline, Grid, Link, Paper, TextField, Typography } from '@mui/material';
import { Login } from '@mui/icons-material';
const theme = createTheme();

const LOGIN_URL = '/api/login';
const USERNAME_REGEX = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

const MuiLogin = () => {
  const { setAuth } = useAuth();

  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || "/student";

  const [username, setUsername] = useState('');
  const [errorUsername, setErrorUsername] = useState(false);
  const [usernameDidBlur, setUsernameDidBlur] = useState(false);
  const [usernameHelperText, setUsernameHelperText] = useState('');

  const [password, setPassword] = useState('');
  const [errorPassword, setErrorPassword] = useState(false);
  const [passwordDidBlur, setPasswordDidBlur] = useState(false);
  const [passwordHelperText, setPasswordHelperText] = useState('');

  const [loginErrorMsg, setLoginErrorMsg] = useState('');

  useEffect(() => {
    setLoginErrorMsg('');
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
        const response = await axios.post(LOGIN_URL,
          JSON.stringify({ username, password }),
          {
            headers: { 'Content-Type': 'application/json' },
            withCredentials: true
          }
        );
        console.log(JSON.stringify(response?.data));
        const access_token = response?.data?.access_token;
        const roles = response?.data?.roles;
        setAuth({ username, password, roles, access_token });
        setUsername('');
        setPassword('');
        navigate(from, { replace: true });
      } catch (err) {
        if (!err?.response) {
          setLoginErrorMsg('No Server Response');
        } else if (err.response?.status === 400) {
          setLoginErrorMsg('Missing Username or Password');
        } else if (err.response?.status === 401) {
          setLoginErrorMsg('Unauthorized');
        } else if (err.response?.status === 403) {
          setLoginErrorMsg('Wrong Username or Password');
        } else {
          setLoginErrorMsg('Login Failed');
        }
      }
    }
  }

  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Paper elevation={3}>
          <Box
            sx={{
              marginTop: 12,
              marginLeft: 3,
              marginRight: 3,
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center'
            }}
          >
            <Typography
              marginTop={3}
              component='h1'
              variant='h5'
            >
              Sign In
            </Typography>
            <Box
              component="form"
              onSubmit={handleSubmit}
              noValidate
              sx={{
                marginTop: 1
              }}
            >
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
                sx={{
                  marginTop: 3
                }}
                required
                fullWidth
                id='password'
                label='Password'
                name='password'
                type='password'
                error={errorPassword}
                helperText={passwordHelperText}
                value={password}
                onChange={handlePasswordChange}
                onBlur={handlePasswordBlur}
              />
              {loginErrorMsg
                ? (
                  <Alert sx={{ marginTop: 3 }} severity='error'>
                    {loginErrorMsg}
                  </Alert>
                ) : null
              }
              <Button
                type='submit'
                fullWidth
                variant='contained'
                size='large'
                sx={{
                  marginTop: 6,
                  marginBottom: 2
                }}
                startIcon={<Login />}
              >
                sign in
              </Button>
            </Box>
            <Grid
              container
              justifyContent="center"
              marginTop={3}
              marginBottom={3}
              spacing={1}
            >
              <Typography>
                Not have an account yet?&ensp;
              </Typography>
              <Link component={RouterLink} to="/register">
                Sign up 
              </Link>
            </Grid>
          </Box>


        </Paper>
      </Container>
    </ThemeProvider>


  )
}

export default MuiLogin