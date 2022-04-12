import * as React from 'react';
import { List, ListItemButton, ListItemIcon, ListItemText, SvgIcon, Icon } from '@mui/material';
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

// import { BAYIcon , BBLIcon , KTBIcon , SCBIcon } from '../icon';
import KTBIcon from '../icon/KTBIcon.svg';
import SCBIcon from '../icon/SCBIcon.svg';
import BAYIcon from '../icon/BAYIcon.svg';
import BBLIcon from '../icon/BBLIcon.svg';

export default function BasicSelect() {
  const [bankSelect, setBankSelect] = React.useState('');

  const handleBankSelect = (event) => {
    console.log(event.target.value)
    setBankSelect(event.target.value);
  };

  const bankingData = [
    {
      text: 'Krung Thai Bank',
      icon: KTBIcon
    },
    {
      text: 'Siam Commercial Bank',
      icon: SCBIcon
    },
    {
      text: 'Bank of Ayudhya',
      icon: BAYIcon
    },
    {
      text: 'Bangkok Bank',
      icon: BBLIcon
    }
  ]

  const dropdown = (
    bankingData.map((item, index) => (
      // <MenuItem value={item.text} key={index}>
      //       <img src={item.icon} />
      //   <ListItemText
      //     sx={{marginLeft : 2}} 
      //     primary={item.text}
      //   />
      // </MenuItem>
      <MenuItem value={item.text} key={index}>
        <Box style={{ display: 'flex', alignItems: 'center' }}>
          {/* <InboxIcon /> */}
          <img src={item.icon} />
          <Box sx={{marginLeft : 2}} >{item.text}</Box>
        </Box>
      </MenuItem>
    ))
  );

  return (
    // <Box >
      <FormControl 
        sx={{ minWidth: 870 }}
        // fullWidth
      >
        <InputLabel
          // id="demo-simple-select-label"
        >Banking
        </InputLabel>
        <Select
          // labelId="demo-simple-select-label"
          // id="demo-simple-select"
          value={bankSelect}
          label="Banking"
          onChange={handleBankSelect}
        >
          {dropdown}
        </Select>
      </FormControl>

    // </Box>
  );
}