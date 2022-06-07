import { Box, Paper ,Stack , Typography, TextField, Button } from '@mui/material';
import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import BankingRecipientSelect from "./BankingRecipientSelect";
import AccountManageInstructorSkeleton from "./AccountManageInstructorSkeleton"
import moment from "moment"

const logoUrl = "https://raw.githubusercontent.com/omise/banks-logo/master/th/";

const AccountManageInstructorReciept = ({drawerWidth,handleAddRecipent}) => {
  const axiosPrivate = useAxiosPrivate();
  const username = useSelector((state) => state.auth.value.username);
  const [bankData, setbankData] = useState();
  const [finance, setFinance] = useState({
    bankBrand: '',
    bankAccountName: '',
    bankAccountNumber: '',
    activatedTime: ''
  });

  useEffect(async () => {
    
    try {
      const [bankData, accountResponse] = await Promise.all([
        fetch('https://raw.githubusercontent.com/omise/banks-logo/master/banks.json'), 
        axiosPrivate.get('http://localhost:8080/api/instructors/finance/getAccount')
      ])
      const timeCreate = new Date(accountResponse.data.created_at)
      const timeCreateFormat = moment(timeCreate).format("DD/MM/YYYY | kk:mm:ss")
      setFinance({...finance,
        bankBrand : accountResponse.data.bank_code,
        bankAccountName : accountResponse.data.name,
        bankAccountNumber : "******" + accountResponse.data.last_digits,
        activatedTime: timeCreateFormat
      });
      // console.log(timeCreateFormat);
      // console.log(finance);

      const bankDataJson = await bankData.json();
      const bankDataSelect = bankDataJson.th[accountResponse.data.bank_code]
      // console.log(bankDataSelect);
      setbankData(bankDataSelect);
     } catch(error) {
      console.error(error);
    }
  }, [])

  const handleTextField = (event) => {
    setFinance({ ...finance, [event.target.name]: event.target.value })
    console.log(finance)
  }

  // const handleSubmit = async (event) => {
  //   // event.preventDefault(); 
  //   // try {
  //   //   console.log('In')
  //   //   // const test = await axiosPrivate.get('http://localhost:8080/api/instructors/getFinance');
  //   //   // const json = test.data
  //   //   // console.log(json)
  //   //   const response = await axiosPrivate.post('/api/students/active-instrustor', finance );
  //   //   console.log(response.data)
  //   // } catch (err) {
  //   //   console.error(err);
  //   //   // navigate('/', { state: { from: location }, replace: true });
  //   // }
  // }
  
  return (
    !bankData ? 
    <AccountManageInstructorSkeleton/>
    :
    <Paper
      elevation={2}
      sx={{
        width: drawerWidth,
        flexShrink: 0
      }}
    >
      <Box
        component="form"
        // onSubmit={handleSubmit}
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
          Reciept Account
        </Typography>
        <Box sx={{ marginTop: 5 , marginX: 5 }}>
          <Box sx={{ display: 'flex', alignItems: 'center' }}>
          <Stack alignItems='center' justifyContent='center' sx= {{backgroundColor: bankData.color , width : 80 , height : 80 , borderRadius : '3px' }}>
            <img src={logoUrl + finance.bankBrand + ".svg" } style={{ width : "80%" , height : "80%" }} />
          </Stack>
          <Stack justifyContent='center'>
          <Typography component='h1' variant='h4' sx={{color: bankData.color ,marginLeft : 3}} >{finance.bankAccountNumber}</Typography>
          <Typography component='h6' variant='body2' sx={{marginLeft : 3}} >{bankData.nice_name}</Typography>
          </Stack>
          </Box>
        
        <TextField
          margin='normal'
          sx={{
            marginTop: 5
          }}
          fullWidth
          disabled
          id='bankAccountName'
          label='Bank Account Name'
          name='bankAccountName'
          type='text'
          value={finance.bankAccountName}
          onChange={handleTextField}
          // onBlur={handlePasswordBlur}
        />
        <TextField
          margin='normal'
          sx={{
            marginTop: 5
          }}
          // required
          fullWidth
          disabled
          id='activatedTime'
          label='Activated Time'
          name='activatedTime'
          type='text'
          value={finance.activatedTime}
          // value={moment(finance.activatedTime).format("DD/MM/YYYY | kk:mm:ss")}
          onChange={handleTextField}
          // onBlur={handlePasswordBlur}
        />
        <Button
          fullWidth
          variant='contained'
          size='large'
          value='change'
          sx={{
            marginTop: 6,
            marginBottom: 2
          }}
          onClick= {handleAddRecipent}
        // startIcon={<Login />}
        >
          Change Receipt Account
        </Button>
        </Box>
      </Box>


    </Paper>
  );
}

export default AccountManageInstructorReciept;