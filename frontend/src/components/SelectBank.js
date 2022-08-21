import React, { useEffect, useState } from 'react'

// Material UI component
import Box from '@mui/material/Box'
import Stack from '@mui/material/Stack'
import InputLabel from '@mui/material/InputLabel'
import MenuItem from '@mui/material/MenuItem'
import FormControl from '@mui/material/FormControl'
import Select from '@mui/material/Select'

const SelectBank = ({ select, onChange, error, onSaving }) => {

    const [bankJson, setBankJson] = useState([]);

    const bankLogoUrl = "https://raw.githubusercontent.com/omise/banks-logo/master/th/";

    useEffect(async () => {
        const bankData = await fetch('https://raw.githubusercontent.com/omise/banks-logo/master/banks.json');
        const bankDataJson = await bankData.json();
        const codeArray = ['bbl','kbank','ktb','tmb','scb','citi','cimb','uob','bay','gsb','tbank','kk'];
        const bankBrandMerge = codeArray.map(brand => { 
            bankDataJson.th[brand]['brand'] = brand 
            return bankDataJson.th[brand]
        })
        setBankJson(bankBrandMerge)
    }, [])

    return (
        <FormControl fullWidth >
            <InputLabel >Banking </InputLabel>
            <Select
                label='Banking'
                fullWidth
                disabled={onSaving}
                value={select} 
                onChange={onChange} 
                error={error}
            >
            {
                bankJson.map((item, index) => (
                    <MenuItem value={item.brand} key={index}>
                        <Box display='flex' alignItems='center'>
                            <Stack
                                alignItems='center' 
                                justifyContent='center' 
                                sx={{
                                        backgroundColor: item.color, 
                                        width: 24,
                                        height: 24,
                                        borderRadius: '3px'
                                    }}
                                >
                                <img
                                    src={`${bankLogoUrl}${item.brand}.svg`}
                                    style={{ width: 20, height: 20 }}
                                />
                            </Stack>
                            <Box ml={2}>
                                {item.nice_name}
                            </Box>
                        </Box>
                    </MenuItem>
                ))
            }
            </Select>
        </FormControl>
    )
}

export default SelectBank