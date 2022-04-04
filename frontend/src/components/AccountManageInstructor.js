import { Avatar,Box,CssBaseline,Divider,List,ListItem,ListItemIcon,ListItemText,Paper,Toolbar,Typography,TextField,FormControlLabel,Checkbox,Alert,Button,Grid } from '@mui/material';
import { useSelector } from "react-redux";

const drawerWidth = 720;

const AccountManageInstructor = () => {
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
          Instructor
        </Typography>
        <TextField
          margin='normal'
          sx={{
            marginTop: 3
          }}
          // required
          fullWidth
          id='Bank Account Name'
          label='Bank Account Name'
          name='Bank Account Name'
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
          id='Bank Account Number'
          label='Bank Account Number'
          name='Bank Account Number'
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
          id='Recipient Name'
          label='Recipient Name'
          name='Recipient Name'
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
          id='Recipient Tax ID'
          label='Recipient Tax ID'
          name='Recipient Tax ID'
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
      Verify and Activate Instructor
    </Button>
      </Box>


    </Paper>
  );
}

export default AccountManageInstructor;