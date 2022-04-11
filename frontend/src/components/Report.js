import React, { useState } from 'react'

// component
import ReportModal from './ReportModal'

// Material UI
import Box from '@mui/material/Box'
import IconButton from '@mui/material/IconButton'
import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
import Typography from '@mui/material/Typography'
// import Modal from '@mui/material/Modal'
// import ClickAwayListener from '@mui/material/ClickAwayListener'
// import Container from '@mui/material/Container'


// icon
import MoreVertIcon from '@mui/icons-material/MoreVert';
import FlagIcon from '@mui/icons-material/Flag';

const Report = () => {
    
    const [ anchorEl, setAnchorEl ] = useState(null);
    
    const handleClickMoreVertIcon = (event) => {
        setAnchorEl(event.currentTarget)
    }

    const handleCloseMenu = () => {
        setAnchorEl(null)
    }

    const handleClickMenu = () => {
        setOpenReportModel(true)
        setAnchorEl(null)
    }

    const [ openReportModal, setOpenReportModel ] = useState(false)
    
    return (
        <Box>

            <IconButton onClick={handleClickMoreVertIcon}>
                <MoreVertIcon />
            </IconButton>

            <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleCloseMenu}>
                <MenuItem onClick={handleClickMenu}>
                    <FlagIcon />
                    <Typography sx={{ ml: 1 }}>
                        Report
                    </Typography>
                </MenuItem>
            </Menu>
            {/* <Modal open={openModal}> */}
                {/* <div> */}
                {/* <ClickAwayListener onClickAway={handleCloseReportModal}> */}
                    
                {/* </ClickAwayListener> */}
            {/* </div> */}
            {/* </Modal> */}
            <ReportModal open={openReportModal} handleOpen={setOpenReportModel} />
        </Box>
    )
}

export default Report