import React, { useState } from 'react'

// component
import ReportModal from './ReportModal'

// Material UI component
import Box from '@mui/material/Box'
import IconButton from '@mui/material/IconButton'
import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
import Typography from '@mui/material/Typography'

// icon
import MoreVertIcon from '@mui/icons-material/MoreVert';
import FlagIcon from '@mui/icons-material/Flag';

const Report = ({ type, contentId }) => {

    const [ report, setReport ] = useState(null)
    const [ reportModal, setReportModal ] = useState(false)

    const handleOpenReport = (event) => {
        setReport(event.currentTarget)
    }

    const handleCloseReport = () => {
        setReport(null)
    }

    const handleOpenReportModal = () => {
        setReport(null)
        setReportModal(true)
    }

    const handleCloseReportModal = () => {
        setReportModal(false)
    }
    
    return (
        <Box>
            <IconButton onClick={handleOpenReport}>
                <MoreVertIcon />
            </IconButton>
            <Menu anchorEl={report} open={Boolean(report)} onClose={handleCloseReport}>
                <MenuItem onClick={handleOpenReportModal}>
                    <FlagIcon />
                    <Typography ml={1}>Report</Typography>
                </MenuItem>
            </Menu>
            <ReportModal
                open={reportModal} 
                onClose={handleCloseReportModal}
                type={type}
                contentId={contentId}
            />
        </Box>
    )
}

export default Report