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
import { URL_UPDATE_NEW_PASSWORD } from '../utils/url'

// utils
import { validation } from '../utils/validation'

const NewPassword = () => {

    const navigate = useNavigate()
    const apiPrivate = useApiPrivate()
    const alertMessage = useAlertMessage()
    
    const [ password, setPassword ] = useState('')
    const [ messagePassword, setMessagePassword ] = useState('')
    const [ errorPassword, setErrorPassword ] = useState(false)
    const [ confirmPassword, setConfirmPassword ] = useState('')
    const [ messageConfirmPassword, setMessageConfirmPassword ] = useState('')
    const [ errorConfirmPassword, setErrorConfirmPassword ] = useState(false)
    const [ showPassword, setShowPassword ] = useState(false)
    const [ saving, setSaving ] = useState(false)

    const handleChangePassword = (event) => {
        if (event.target.name === 'new_password') {
            setPassword(event.target.value)
            verifyMessageAndError(event.target.name, event.target.value)

            if (event.target.value === confirmPassword) {
                setMessageConfirmPassword('')
                setErrorConfirmPassword(false)
            }

        } else if (event.target.name === 'confirm_new_password') {
            setConfirmPassword(event.target.value)
            verifyMessageAndError(event.target.name, password, event.target.value)
        }
    }

    const verifyMessageAndError = (name, password, confirmPassword = null) => {
        let msg = ''
        let err = null
        if (name === 'new_password') {
            msg = validation.validatePassword(password)
            err = msg === '' ? false : true
            setMessagePassword(msg)
            setErrorPassword(err)
        } else if (name === 'confirm_new_password') {
            msg = validation.validateConfirmPassword(password, confirmPassword)
            err = msg === '' ? false : true
            setMessageConfirmPassword(msg)
            setErrorConfirmPassword(err)
        }
        return err
    }

    const handleChangeShowPassword = () => {
        setShowPassword(!showPassword)
    }

    const handleClickChangeNewPassword = async (event) => {
        event.preventDefault()

        let invalidPassword = verifyMessageAndError('new_password', password)
        let invalidConfirmPassword = verifyMessageAndError('confirm_new_password', password, confirmPassword)
        if (invalidPassword || invalidConfirmPassword) return
        if (password !== confirmPassword) return

        setSaving(true)
        const payload = {
            newPassword: password
        }
        const response = await apiPrivate.put(URL_UPDATE_NEW_PASSWORD, payload)

        if (response.status === 204) {
            navigate('/account-manage/profile')
        } else {
            alertMessage.show('error', response.message)
        }
    }
    
    return (
        <Grid container border='solid 1px #d3d3d3' borderRadius={3} p={3} spacing={2} component='form'>
            <Grid item xs={12}>
                <TextField
                    name='new_password'
                    label='New password'
                    type={showPassword ? 'text' : 'password'}
                    size='small'
                    required
                    fullWidth
                    value={password}
                    onChange={handleChangePassword}
                    helperText={messagePassword}
                    error={errorPassword}
                />
            </Grid>
            <Grid item xs={12}>
                <TextField
                    name='confirm_new_password'
                    label='Confirm new password'
                    type={showPassword ? 'text' : 'password'}
                    size='small'
                    required
                    fullWidth
                    value={confirmPassword}
                    onChange={handleChangePassword}
                    helperText={messageConfirmPassword}
                    error={errorConfirmPassword}
                />
            </Grid>
            <Grid item xs={12} container direction='row' alignItems='center'>
                <Checkbox size='small' checked={showPassword} onChange={handleChangeShowPassword} />
                <Typography>Show password</Typography>
            </Grid>
            <Grid item xs={12} container direction='row' justifyContent='center'>
                <LoadingButton variant='contained' type='submit' loading={saving} onClick={handleClickChangeNewPassword}>
                    Change new password
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

export default NewPassword