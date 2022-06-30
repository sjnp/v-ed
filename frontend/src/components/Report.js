import React, { useState } from 'react'
import { useSelector } from 'react-redux'

// component
import ReportModal from './ReportModal'
import SignInForm from './SignInForm'
import SignUpForm from './SignUpForm'
import SuccessAlertBox from './SuccessAlertBox'

// Material UI component
import Box from '@mui/material/Box'
import IconButton from '@mui/material/IconButton'
import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
import Typography from '@mui/material/Typography'
import Container from '@mui/material/Container'
import Modal from '@mui/material/Modal'

// icon
import MoreVertIcon from '@mui/icons-material/MoreVert';
import FlagIcon from '@mui/icons-material/Flag';

const Report = ({ type, contentId }) => {

    const username = useSelector(state => state.auth.value.username)

    const [ report, setReport ] = useState(null)
    const [ reportModal, setReportModal ] = useState(false)

    const [ requiredLogin, setRequiredLogin ] = useState(false)
    const [ callSignUpForm, setCallSignUpForm ] = useState(false)
    const [ openSignUpSuccess, setOpenSignUpSuccess ] = useState(false)

    const handleOpenReport = (event) => {
        setReport(event.currentTarget)
    }

    const handleCloseReport = () => {
        setReport(null)
    }

    const handleOpenReportModal = () => {
        if (!username) {
            setRequiredLogin(true)
            return
        }

        setReport(null)
        setReportModal(true)
    }

    const handleCloseReportModal = () => {
        setReportModal(false)
    }

    const handleClickCallSignUpForm = () => {
        setRequiredLogin(false)
        setCallSignUpForm(true)
    }

    const handleSignUpSuccess = () => {
        setCallSignUpForm(false)
        setOpenSignUpSuccess(true)
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
            <Modal open={requiredLogin} onClose={() => setRequiredLogin(false)}>
                <Container component='main' maxWidth='xs'>
                <SignInForm onLoginSuccess={() => setRequiredLogin(false)} onSignUp={handleClickCallSignUpForm} />
                </Container>
            </Modal>
            <Modal open={callSignUpForm}>
                <Container component='main' maxWidth='xs'>
                <SignUpForm onClose={() => setCallSignUpForm(false)} onSuccess={handleSignUpSuccess} />
                </Container>
            </Modal>
            <Modal open={openSignUpSuccess} onClose={() => setOpenSignUpSuccess(false)}>
                <Container component="main" maxWidth="xs">
                <SuccessAlertBox handleClick={() => setOpenSignUpSuccess(false)} text='Register successful' />
                </Container>
            </Modal>
        </Box>
    )
}

export default Report