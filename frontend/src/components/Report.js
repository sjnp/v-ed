import React, { useState } from 'react'

// Material UI
import Box from '@mui/material/Box'
import IconButton from '@mui/material/IconButton'
import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
import Typography from '@mui/material/Typography'

// icon
import MoreVertIcon from '@mui/icons-material/MoreVert';
import FlagIcon from '@mui/icons-material/Flag';

const Report = () => {
    
    const [anchorEl, setAnchorEl] = useState(null);
    
    const open = Boolean(anchorEl);
    
    const handleClick = (event) => {
      setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
        alert('Show Form Report')
    };
    
    return (
        <Box>
            <IconButton onClick={handleClick}>
                <MoreVertIcon />
            </IconButton>

            <Menu
                id="basic-menu"
                anchorEl={anchorEl}
                open={open}
                onClose={handleClose}
                MenuListProps={{
                'aria-labelledby': 'basic-button',
                }}
            >
                <MenuItem onClick={handleClose}>
                    <FlagIcon />
                    <Typography sx={{ ml: 1 }}>
                        Report
                    </Typography>
                </MenuItem>
            </Menu>
        </Box>
    )
}

export default Report