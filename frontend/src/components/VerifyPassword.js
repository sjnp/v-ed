import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'

// component
import AlertMessage from './AlertMessage'

// Material UI component
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import TextField from '@mui/material/TextField'
import Checkbox from '@mui/material/Checkbox'
import LoadingButton from '@mui/lab/LoadingButton'

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'
import useAlertMessage from '../hooks/useAlertMessage'

// url
import { URL_VERIFY_PASSWORD } from '../utils/url'

// utils
import { validation } from '../utils/validation'

const VerifyPassword = () => {

    const navigate = useNavigate()
    const apiPrivate = useApiPrivate()
    const alertMessage =  useAlertMessage()

    const [ password, setPassword ] = useState('')
    const [ message, setMessage ] = useState('')
    const [ error, setError ] = useState(false)
    const [ showPassword, setShowPassword ] = useState(false)
    const [ verifying, setVerifying ] = useState(false)

    const handleChangePassword = (event) => {
        setPassword(event.target.value)
        verifyMessageAndError(event.target.value)
    }

    const verifyMessageAndError = (value) => {
        const msg = validation.validatePassword(value)
        const err = msg === '' ? false : true
        setMessage(msg)
        setError(err)
        return err
    }

    const handleShowPassword = () => {
        setShowPassword(!showPassword)
    }

    const handleClickVerify = async (event) => {
        event.preventDefault()
        if (verifyMessageAndError(password)) return

        setVerifying(true)
        const payload = {
            password: password
        }
        const response = await apiPrivate.post(URL_VERIFY_PASSWORD, payload)

        if (response.status === 204) {
            navigate('/account-manage/profile/new-password')
        } else {
            alertMessage.show('error', response.message)
        }
        setVerifying(false)
    }
    
    return (
        <Grid container border='solid 1px #d3d3d3' borderRadius={3} p={3} spacing={2} component='form'>
            <Grid item xs={12}>
                <TextField
                    label='Current password'
                    type={showPassword ? 'text' : 'password'}
                    size='small'
                    required
                    fullWidth
                    value={password}
                    onChange={handleChangePassword}
                    helperText={message}
                    error={error}
                />
            </Grid>
            <Grid item xs={12} container direction='row' alignItems='center'>
                <Checkbox size='small' checked={showPassword} onChange={handleShowPassword} />
                <Typography>Show password</Typography>
            </Grid>
            <Grid item xs={12} container direction='row' justifyContent='center'>
                <LoadingButton variant='contained' type='submit' loading={verifying} onClick={handleClickVerify}>
                    Verify
                </LoadingButton>
            </Grid>
            <AlertMessage
                open={alertMessage.getOpen()} 
                type={alertMessage.getType()}
                message={alertMessage.getMessage()}
                onClose={alertMessage.close}
            />
        </Grid>
    )
}

export default VerifyPassword