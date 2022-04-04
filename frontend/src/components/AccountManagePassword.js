import { Avatar,Box,CssBaseline,Divider,List,ListItem,ListItemIcon,ListItemText,Paper,Toolbar,Typography,TextField,FormControlLabel,Checkbox,Alert,Button,Grid } from '@mui/material';
import { useSelector } from "react-redux";

const drawerWidth = 720;

const AccountManagePassword = () => {
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
          Change Password
        </Typography>
        <TextField
          margin='normal'
          sx={{
            marginTop: 3
          }}
          // required
          fullWidth
          id='Current Password'
          label='Current Password'
          name='Current Password'
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
          id='New Password'
          label='New Password'
          name='New Password'
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
          id='Re-Enter New Password'
          label='Re-Enter New Password'
          name='Re-Enter New Password'
          type='text'
          // error={errorPassword}
          // helperText={passwordHelperText}
          // value={password}
          // onChange={handlePasswordChange}
          // onBlur={handlePasswordBlur}
        />
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
      Change Password
    </Button>
      </Box>


    </Paper>
  );
}

export default AccountManagePassword;