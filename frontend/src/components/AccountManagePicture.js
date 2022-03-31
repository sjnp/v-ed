import { Avatar,Box,CssBaseline,Divider,List,ListItem,ListItemIcon,ListItemText,Paper,Toolbar,Typography,TextField,FormControlLabel,Checkbox,Alert,Button,Grid } from '@mui/material';
import { useSelector } from "react-redux";

const drawerWidth = 720;

const AccountManagePicture = () => {
  const username = useSelector((state) => state.auth.value.username);

  return (
    <Paper 
      elevation={2} 
      sx={{ 
        width: drawerWidth, 
        flexShrink: 0 
      }}
    >
      <Box sx={{m:5}}>
        <Typography 
          // ml={3} 
          component='h1' 
          variant='h5'
        >
          Profile Picture
        </Typography>
        {/* <TextField
          margin='normal'
          sx={{
            marginTop: 3
          }}
          // required
          fullWidth
          id='First Name'
          label='First Name'
          name='First Name'
          type='text'
          // error={errorPassword}
          // helperText={passwordHelperText}
          // value={password}
          // onChange={handlePasswordChange}
          // onBlur={handlePasswordBlur}
        />
        <TextField
          margin='normal'
          sx={{
            marginTop: 3
          }}
          // required
          fullWidth
          id='Last Name'
          label='Last Name'
          name='Last Name'
          type='text'
          // error={errorPassword}
          // helperText={passwordHelperText}
          // value={password}
          // onChange={handlePasswordChange}
          // onBlur={handlePasswordBlur}
        />
        <TextField
          margin='normal'
          sx={{
            marginTop: 3
          }}
          // required
          fullWidth
          id='Occupation'
          label='Occupation'
          name='Occupation'
          type='text'
          // error={errorPassword}
          // helperText={passwordHelperText}
          // value={password}
          // onChange={handlePasswordChange}
          // onBlur={handlePasswordBlur}
        />
        <TextField
          margin='normal'
          sx={{
            marginTop: 3
          }}
          // required
          fullWidth
          id='Biography'
          label='Biography'
          name='Biography'
          type='text'
          // error={errorPassword}
          // helperText={passwordHelperText}
          // value={password}
          // onChange={handlePasswordChange}
          // onBlur={handlePasswordBlur}
        /> */}
    <Button
      type='submit'
      fullWidth
      variant='contained'
      size='large'
      sx={{
        marginTop: 6,
        marginBottom: 2
      }}
      // startIcon={<Login />}
    >
      Upload Picture
    </Button>
      </Box>


    </Paper>
  );
}

export default AccountManagePicture;