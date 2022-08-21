import React from 'react'

// Material UI component
import Box from '@mui/material/Box'
import Stack from '@mui/material/Stack'
import InputLabel from '@mui/material/InputLabel'
import MenuItem from '@mui/material/MenuItem'
import FormControl from '@mui/material/FormControl'
import Select from '@mui/material/Select'

// custom hook
import useBanks from '../hooks/useBanks'

const SelectBank = ({ select, onChange, error, onSaving }) => {

    const banks = useBanks()

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
                banks.map((bank, index) => (
                    <MenuItem value={bank.name} key={index}>
                        <Box display='flex' alignItems='center'>
                            <Stack
                                alignItems='center'
                                justifyContent='center'
                                bgcolor={bank.color}
                                width={24}
                                height={24}
                                borderRadius={1}
                            >
                                <img src={bank.imageUrl} style={{ width: 20, height: 20 }} />
                            </Stack>
                            <Box ml={2}>{bank.nice_name}</Box>
                        </Box>
                    </MenuItem>
                ))
            }
            </Select>
        </FormControl>
    )
}

export default SelectBank