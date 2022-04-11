import React, { useState } from 'react'

// component
import AlertMessage from './AlertMessage'

// Material UI
import Paper from '@mui/material/Paper'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import Divider from '@mui/material/Divider';
import Typography from '@mui/material/Typography';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import Modal from '@mui/material/Modal'
import ClickAwayListener from '@mui/material/ClickAwayListener'
import Container from '@mui/material/Container'

const ReportModal = ({ open, handleOpen }) => {

    const topicReports = [
        { value: 1, label: 'Report 1' },
        { value: 2, label: 'Report 2' },
        { value: 3, label: 'Report 3' }
    ]

    const [ reportValue, setReportValue ] = useState(0)
    const handleChangeValueReport = (event) => {
        setReportValue(event.target.value)
    }

    const handleClickSendReport = () => {

        if (reportValue === 0) {
            setAlertMessage({
                open: true,
                type: 'warning',
                text: 'Please, select report value',
                labelButton: 'OK'
            })
            return
        }

        const report = () => 'success'
        const result = report()

        if (result === 'success') {
            setAlertMessage({
                open: true,
                type: 'success',
                text: 'Report successful',
                labelButton: 'Done'
            })
        } else {
            setAlertMessage({
                open: true,
                type: 'error',
                text: 'Report fail, please try again',
                labelButton: 'Agree'
            })
        }
        handleOpen(false)
        setReportValue(0)
    }

    const handleClickCancel = () => {
        handleOpen(false)
        setReportValue(0)
    }

    const [ alertMessage, setAlertMessage ] = useState({
        open: false,
        type: '',
        text: '',
        labelButton: ''
    })
    
    return (
        <Box>
        <Modal open={open}>
            <Container component="main" maxWidth="xs">

                <Paper sx={{ mt: 10, p: 2 }}>

                    <Box>
                        <Typography variant='h6'>
                            Report
                        </Typography>
                    </Box>

                    <Divider sx={{ mt: 1, mb: 1 }} />

                    <Box>
                        <FormControl>
                            <RadioGroup onChange={handleChangeValueReport}>
                            {
                                topicReports.map((topicReport, index) => (
                                    <FormControlLabel
                                        key={index}
                                        value={topicReport.value}
                                        control={<Radio />}
                                        label={topicReport.label} 
                                    />
                                ))
                            }
                            </RadioGroup>
                        </FormControl>
   
                    </Box>

                    <Divider sx={{ mt: 1, mb: 1 }} />

                    <Box>
                        <Button variant='contained' color="secondary" sx={{ m:1 }} onClick={handleClickCancel}>
                            Cancel
                        </Button>
                        <Button variant='contained' color='primary' sx={{ m:1 }} onClick={handleClickSendReport}>
                            Send report
                        </Button>
                    </Box>

                </Paper>
            </Container>

            
            
        </Modal>
        <AlertMessage
            open={alertMessage.open}
            type={alertMessage.type}
            text={alertMessage.text}
            labelButton={alertMessage.labelButton}
            handleOpen={setAlertMessage}
        />
      </Box>  
    )
}

export default ReportModal