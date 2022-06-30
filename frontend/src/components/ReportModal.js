import React, { useState } from 'react'
import { useSelector } from 'react-redux'

// component
import AlertMessage from './AlertMessage'

// Material UI component
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import Radio from '@mui/material/Radio'
import RadioGroup from '@mui/material/RadioGroup'
import FormControlLabel from '@mui/material/FormControlLabel'
import FormControl from '@mui/material/FormControl'
import Dialog from '@mui/material/Dialog'
import DialogActions from '@mui/material/DialogActions'
import DialogContent from '@mui/material/DialogContent'
import DialogTitle from '@mui/material/DialogTitle'
import LoadingButton from '@mui/lab/LoadingButton'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_CREATE_REPORT } from '../utils/url';

const ReportModal = ({ type, contentId, open, onClose }) => {

    const axiosPrivate = useAxiosPrivate()

    const reasons = useSelector(state => state.reasonReport.value.reasonReports)

    const [ reasonReportId, setReasonReportId ] = useState(null)
    const [ loading, setLoading ] = useState(false)
    const [ openAlertMessage, setOpenAlertMessage ] = useState(false)
    const [ typeAlertMessage, setTypeAlertMessage ] = useState('info')
    const [ messageAlertMessage, setMessageAlertMessage ] = useState('')

    const handleCloseMyself = () => {
        onClose()
        setReasonReportId(null)
    }

    const handleChangeReason = (event) => {
        setReasonReportId(event.target.value)
    }

    const handleCloseAlertMessage = (event, reason) => {
        if (reason === 'clickaway') return
        setOpenAlertMessage(false)
    }

    const handleSendReport = async () => {
        if (reasonReportId === null ) {
            setTypeAlertMessage('warning')
            setMessageAlertMessage('Reason report is required')
            setOpenAlertMessage(true)
            return
        }

        setLoading(true)
        const payload = {
            contentId: contentId,
            reasonReportId: reasonReportId,
            reportType: type
        }
        const response = await apiPrivate.post(axiosPrivate, URL_CREATE_REPORT, payload)
        setLoading(false)

        if (response.status === 201) {
            handleCloseMyself()
            handleCloseAlertMessage()
            setTypeAlertMessage('success')
            setMessageAlertMessage('Report successful')
            setOpenAlertMessage(true)
        } else {
            setTypeAlertMessage('error')
            setMessageAlertMessage(response.message)
            setOpenAlertMessage(true)
        }
    }
    
    return (
        <Box>
            <Dialog open={open} onClose={handleCloseMyself}>
                <DialogTitle>Report</DialogTitle>
                <DialogContent>
                    <FormControl>
                        <RadioGroup onChange={handleChangeReason}>
                        {
                            reasons.map((reason, index) => (
                                <FormControlLabel 
                                    key={index} 
                                    control={<Radio />}
                                    value={reason?.id} 
                                    label={reason?.description}
                                />
                            ))
                        }
                        </RadioGroup>
                    </FormControl>
                </DialogContent>
                <DialogActions sx={{ mr: 1, mb: 1 }}>
                    <Button variant='outlined' color='primary' disabled={loading} onClick={handleCloseMyself}>
                        Cancel
                    </Button>
                    <LoadingButton variant='contained' color='primary' loading={loading} onClick={handleSendReport}>
                        Send
                    </LoadingButton>
                </DialogActions>
            </Dialog>
            <AlertMessage
                open={openAlertMessage} 
                type={typeAlertMessage}
                message={messageAlertMessage}
                onClose={handleCloseAlertMessage}
            />
        </Box>
    )
}

export default ReportModal