import React, { useState } from 'react'

// component
import AlertMessage from './AlertMessage'

// Material UI component
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import IconButton from '@mui/material/IconButton'
import TextField from '@mui/material/TextField'
import LoadingButton from '@mui/lab/LoadingButton'

// Material UI icon
import EditIcon from '@mui/icons-material/Edit';

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'
import useAlertMessage from '../hooks/useAlertMessage'

// url
import { URL_UPDATE_PROFILE } from '../utils/url'

// utils
import { validation } from '../utils/validation'

const NameEditor = ({ defaultFirstname, defaultLastname }) => {

    const apiPrivate = useApiPrivate()
    const alertMessage = useAlertMessage()

    const [ firstname, setFirstname ] = useState(defaultFirstname || '')
    const [ lastname, setLastname ] = useState(defaultLastname || '')
    const [ messageFirstname, setMessageFirstname ] = useState('')
    const [ messageLastname, setMessageLastname ] = useState('')
    const [ errorFirstname, setErrorFirstname ] = useState(false)
    const [ errorLastname, setErrorLastname ] = useState(false)
    const [ saving, setSaving ] = useState(false)
    const [ isEdit, setIsEdit ] = useState(false)

    const handleChangeName = (event, setName) => {
        setName(event.target.value)
    }

    const handleClickEditName = () => {
        setIsEdit(true)
    }

    const verifyName = () => {
        let flagFirstname = false
        let validateFirstname = validation.validateFirstname(firstname)
        if (validateFirstname !== '') {
            setMessageFirstname(validateFirstname)
            setErrorFirstname(true)
            flagFirstname = true
        }

        let flagLastname = false
        let validateLastname = validation.validateLastname(lastname)
        if (validateLastname !== '') {
            setMessageLastname(validateLastname)
            setErrorLastname(true)
            flagLastname = true
        }

        return flagFirstname || flagLastname
    }

    const handleClickSaveName = async () => {
        setMessageFirstname('')
        setMessageLastname('')
        setErrorFirstname(false)
        setErrorLastname(false)

        const isInvalid = verifyName()
        if (isInvalid === true) return

        setSaving(true)
        const payload = {
            firstname: firstname,
            lastname: lastname,
        }

        const response = await apiPrivate.put(URL_UPDATE_PROFILE, payload)
        if (response.status === 204) {
            setIsEdit(false)
            alertMessage.show('success', 'Update name successful')
        }
        setSaving(false)
    }

    return (
        <Grid container direction='row' alignItems='center' justifyContent='center' >
            <Grid item xs={3}>
                <Typography>Name</Typography>
            </Grid>
            <Grid item xs={7} container >
            {
                isEdit ?
                <Grid item xs={12} container direction='row' alignItems='center' justifyContent='center' spacing={1}>
                    <Grid item xs={6}>
                        <TextField
                            id='firstname'
                            label='First name'
                            type='text'
                            size='small'
                            required
                            fullWidth
                            disabled={saving}
                            value={firstname}
                            onChange={(event) => handleChangeName(event, setFirstname)}
                            helperText={messageFirstname}
                            error={errorFirstname}
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            id='lastname'
                            label='Last name'
                            type='text'
                            size='small'
                            required
                            fullWidth
                            disabled={saving}
                            value={lastname}
                            onChange={(event) => handleChangeName(event, setLastname)}
                            helperText={messageLastname}
                            error={errorLastname}
                        />
                    </Grid>
                </Grid>
                :
                <Typography color='gray'>
                    {firstname} {lastname}
                </Typography>
            }
            </Grid>
            <Grid item xs={2} container alignItems='right' justifyContent='right'>
            {
                isEdit ?
                <LoadingButton variant='contained' onClick={handleClickSaveName} loading={saving}>
                    Save
                </LoadingButton>
                :
                <IconButton color='primary' onClick={handleClickEditName}>
                    <EditIcon />
                </IconButton>
            }
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

export default NameEditor