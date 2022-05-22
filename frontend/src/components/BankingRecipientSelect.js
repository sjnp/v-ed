import {useEffect,useState} from 'react';
import { List, ListItemButton, ListItemIcon, ListItemText, SvgIcon, Icon, Stack } from '@mui/material';
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';


const logoUrl = "https://raw.githubusercontent.com/omise/banks-logo/master/th/";
// const bankJson = fetch("https://raw.githubusercontent.com/omise/banks-logo/master/banks.json").then(response => response.json()).then((jsonData) => {return jsonData})

export default function BankingRecipientSelect({ handleEvent, bankSelect }) {
  const [bankJson, setBankJson] = useState([]);

  useEffect(async () => {
    try {
      let bankData = await fetch('https://raw.githubusercontent.com/omise/banks-logo/master/banks.json');
      let bankDataJson = await bankData.json();
      const codeArray = ['bbl','kbank','ktb','tmb','scb','citi','cimb','uob','bay','gsb','tbank','kk'];
      const bankBrandMerge = codeArray.map(brand => { 
        bankDataJson.th[brand]['brand'] = brand 
        return bankDataJson.th[brand]
      })
      console.log(bankBrandMerge);
      setBankJson(bankBrandMerge);
     } catch(error) {
      console.error(error);
    }
  }, [])

  const handleBankSelect = (event) => {
    handleEvent(event);
  };

  const dropdown = (
    bankJson.map((item, index) => (
      <MenuItem value={item.brand} key={index}>
        <Box style={{ display: 'flex', alignItems: 'center' }}>
          {/* <InboxIcon /> */}
          <Stack alignItems='center' justifyContent='center' style= {{backgroundColor: item.color , width : 24 , height : 24 , borderRadius : '3px' }}>
            <img src={logoUrl + item.brand + ".svg" } style={{ width : 20 , height : 20 }} />
          </Stack>
          <Box sx={{marginLeft : 2}} >{item.nice_name}</Box>
        </Box>
      </MenuItem>
    ))
  );

  return (
    // <Box >
      <FormControl 
        sx={{ 
          marginTop: 3,
          // minWidth: 870 
        }}
        fullWidth
      >
        <InputLabel
          // id="demo-simple-select-label"
        >Banking
        </InputLabel>
        <Select 
          // labelId="demo-simple-select-label"
          // id="demo-simple-select"
          size=''
          value={bankSelect}
          label="Banking"
          name="bankBrand"
          onChange={handleBankSelect}
        >
          {dropdown}
        </Select>
      </FormControl>

    // </Box>
  );
}