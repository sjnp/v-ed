import { Avatar,Box,CssBaseline,Divider,List,ListItem,ListItemIcon,ListItemText,Paper,Toolbar,Typography,TextField,FormControlLabel,Checkbox,Alert,Button,Grid } from '@mui/material';
import { useSelector } from "react-redux";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { useState } from "react";
import BankingRecipientSelect from "./BankingRecipientSelect";

const drawerWidth = 720;

const AccountManageInstructor = () => {
  const axiosPrivate = useAxiosPrivate();
  const username = useSelector((state) => state.auth.value.username);
  const [finance, setFinance] = useState({
    bankBrand: '',
    bankAccountName: '',
    bankAccountNumber: '',
    recipientName: username,
    type: 'individual'
  });

  const handleTextField = (event) => {
    setFinance({ ...finance, [event.target.name]: event.target.value })
    console.log(event.target)
    console.log(finance)
  }

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      console.log('In')
      // const test = await axiosPrivate.get('http://localhost:8080/api/instructors/test');
      const response = await axiosPrivate.post('/api/students/active-instrustor', finance );
      // console.log(test)
      console.log(response.data)
    } catch (err) {
      console.error(err);
      // navigate('/', { state: { from: location }, replace: true });
    }
  }
  
  return (
    <Paper 
      elevation={2} 
      sx={{ 
        width: drawerWidth, 
        flexShrink: 0 
      }}
    >
      <Box
        component="form"
        onSubmit={handleSubmit}
        // noValidate
        sx={{
          margin: 5
        }}
      >
        <Typography 
          // ml={3} 
          component='h1' 
          variant='h5'
        >
          Instructor
        </Typography>
        <BankingRecipientSelect handleEvent={handleTextField} />
        <TextField
          margin='normal'
          sx={{
            marginTop: 3
          }}
          // required
          fullWidth
          id='bankAccountName'
          label='Bank Account Name'
          name='bankAccountName'
          type='text'
          // error={errorPassword}
          // helperText={passwordHelperText}
          // value={password}
          onChange={handleTextField}
          // onBlur={handlePasswordBlur}
        />
        <TextField
          margin='normal'
          sx={{
            marginTop: 3
          }}
          // required
          fullWidth
          id='bankAccountNumber'
          label='Bank Account Number'
          name='bankAccountNumber'
          type='text'
          // error={errorPassword}
          // helperText={passwordHelperText}
          // value={password}
          onChange={handleTextField}
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