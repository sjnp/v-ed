import { Box, Paper, Typography, TextField, Button } from '@mui/material';
import { useState } from "react";
import { useSelector } from "react-redux";
// import { useDispatch } from "react-redux";
import { setFinanceAccount } from '../features/financeAccountSlice';
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import BankingRecipientSelect from "./BankingRecipientSelect";

const AccountManageInstructorInput = ({drawerWidth,handleAddRecipent,addOrChange}) => {
  const axiosPrivate = useAxiosPrivate();
  // const dispatch = useDispatch();
  const username = useSelector((state) => state.auth.value.username);
  // const finance = useSelector((state) => state.finance.value )
  const [finance, setFinance] = useState({
    bankBrand: '',
    bankAccountName: '',
    bankAccountNumber: '',
    recipientName: username,
    type: 'individual'
  });

  const handleTextField = (event) => {
    setFinance({...finance,
      [event.target.name]: event.target.value 
    })
    // console.log([event.target.name])
    // dispatch(setFinanceAccount(event.target))
    // console.log(finance)
    // console.log(finance.bankBrand)
  }

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      console.log('In')
      const response = await axiosPrivate.post('/api/students/active-instrustor', finance );

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
          { addOrChange === "add" ? "Add Reciept Account" : " Change Reciept Account " }
        </Typography>
        <Box sx={{ marginTop: 5 , marginX: 5 }}>
          <BankingRecipientSelect handleEvent={handleTextField} bankSelect={finance.bankBrand}/>
          <TextField
            margin='normal'
            sx={{
              marginTop: 5
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

          >
            Verify and Activate Instructor
          </Button>
          <Button
            fullWidth
            variant='contained'
            size='large'
            sx={{
              marginTop: 1,
              marginBottom: 2
            }}
            value='cancel'
            onClick={handleAddRecipent}
            color="secondary"
          >
            Cancel
          </Button>
        </Box>
      </Box>


    </Paper>
  );
}

export default AccountManageInstructorInput;