import React, { useState } from 'react'

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
import Modal from '@mui/material/Modal'
import Container from '@mui/material/Container'
import Alert from '@mui/material/Alert'
import AlertTitle from '@mui/material/AlertTitle'

const ReportModal = ({ type, open, handleOpen }) => {

    const getTopicReport = (type) => {

        const allTopicReport = [
            { value: 1, text: 'ข้อความสแปม' },
            { value: 2, text: 'ละเมิดสิทธิของฉัน' },
            { value: 3, text: 'ข้อความเท็จ ไม่ตรงกับข้อเท็จจริง' },
            { value: 4, text: 'ใช้ถ้อยคำที่รุนแรง หยาบคาย' },
            { value: 5, text: 'ความเห็นนอกบริบท ไม่เกี่ยวข้องกับคำถาม' },
            { value: 6, text: 'เนื้อหารุนแรง และน่ารังเกียจ' },
            { value: 7, text: 'xxx' },
            { value: 8, text: 'xxx' },
            { value: 9, text: 'xxx' },
        ]

        let topicReport = []

        if (type === 'review') {
            topicReport.push(allTopicReport[0])
            topicReport.push(allTopicReport[1])
            topicReport.push(allTopicReport[2])
            topicReport.push(allTopicReport[3])
        } else if (type === 'comment') {
            topicReport.push(allTopicReport[0])
            topicReport.push(allTopicReport[1])
            topicReport.push(allTopicReport[2])
            topicReport.push(allTopicReport[3])
            topicReport.push(allTopicReport[4])
        } else if (type === 'question') {
            topicReport.push(allTopicReport[0])
            topicReport.push(allTopicReport[1])
            topicReport.push(allTopicReport[2])
            topicReport.push(allTopicReport[3])
            topicReport.push(allTopicReport[5])
        }
        
        return topicReport
    }

    const topicReports = getTopicReport(type)

    const [ reportValue, setReportValue ] = useState('')

    const initAlertMessage = {
        open: false,
        type: 'info',
        text: '',
        labelButton: ''
    }

    const [ alertMessage, setAlertMessage ] = useState(initAlertMessage)

    const handleClickSendReport = () => {

        if (reportValue === '') {
            setAlertMessage({
                open: true,
                type: 'warning',
                text: 'Please, select report topic.',
                labelButton: 'OK'
            })
            return
        }

        const result = 'successx'

        if (result === 'success') {
            setAlertMessage({
                open: true,
                type: 'success',
                text: reportValue + '\nReport successful ',
                labelButton: 'Done'
            })
        } else {
            setAlertMessage({
                open: true,
                type: 'error',
                text: reportValue + '\nReport fail, please try again',
                labelButton: 'Try again'
            })
        }
        handleOpen(false)
        setReportValue('')
    }

    const handleClickCancel = () => {
        handleOpen(false)
        setReportValue(0)
    }
    
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
                            <RadioGroup onChange={(event) => setReportValue(event.target.value)}>
                            {
                                topicReports.map((topicReport, index) => (
                                    <FormControlLabel
                                        key={index}
                                        value={topicReport.text}
                                        control={<Radio />}
                                        label={topicReport.text} 
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
        <Modal open={alertMessage.open} sx={{ width: 300, m: 'auto' }}>
            <Paper sx={{ marginTop: 25, padding: 2 }}>
                <Alert severity={alertMessage.type} sx={{ margin: 1, marginBottom: 2 }} >
                    <AlertTitle>{alertMessage.type}</AlertTitle>
                    {alertMessage.text}
                </Alert>
                <Box textAlign="center">
                    <Button
                        type="button" 
                        variant="contained" 
                        color={alertMessage.type}
                        onClick={() => setAlertMessage(initAlertMessage)} 
                    >
                        {alertMessage.labelButton}
                    </Button>
                </Box>
            </Paper>
        </Modal>
      </Box>  
    )
}

export default ReportModal