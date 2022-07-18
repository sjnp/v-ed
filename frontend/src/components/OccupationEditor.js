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

const OccupationEditor = ({ defaultOccupation }) => {

    const apiPrivate = useApiPrivate()
    const alertMessage = useAlertMessage()

    const maxLength = 255

    const [ occupation, setOccupation ] = useState(defaultOccupation || '')
    const [ message, setMessage ] = useState(`(${occupation?.length}/${maxLength})`)
    const [ error, setError ] = useState(false)
    const [ saving, setSaving ] = useState(false)
    const [ isEdit, setIsEdit ] = useState(false)

    const handleChangeOccupation = (event) => {
        if (event.target.value.length <= maxLength) {
            setOccupation(event.target.value)
            setMessage(`(${event.target.value.length}/${maxLength})`)
            setError(false)
        } else {
            setMessage(`(${occupation.length}/${maxLength}) limit ${maxLength} character`)
            setError(true)
        }
    }

    const handleBlurOccupation = () => {
        setMessage(`(${occupation.length}/${maxLength})`)
        setError(false)
    }

    const handleClickEditOccupation = () => {
        setIsEdit(true)
    }

    const handleClickSaveOccupation = async () => {
        setSaving(true)
        const payload = {
            occupation: occupation
        }

        const response = await apiPrivate.put(URL_UPDATE_PROFILE, payload)
        if (response.status === 204) {
            setIsEdit(false)
            alertMessage.show('success', 'Update occupation successful')
        }
        setSaving(false)
    }

    return (
        <Grid container direction='row' alignItems='center' justifyContent='center' >
            <Grid item xs={3}>
                <Typography>Occupation</Typography>
            </Grid>
            <Grid item xs={7} container >
            {
                isEdit ?
                <Grid item xs={12} container direction='row' alignItems='center' justifyContent='center'>
                    <Grid item xs={12}>
                        <TextField
                            label='Occupation'
                            type='text'
                            size='small'
                            fullWidth
                            multiline
                            disabled={saving}
                            value={occupation}
                            onChange={handleChangeOccupation}
                            onBlur={handleBlurOccupation}
                            helperText={message}
                            error={error}
                        />
                    </Grid>
                </Grid>
                :
                <Grid item xs={12} container direction='column'>
                    <Grid item xs={12}>
                        <Typography color='gray'>
                            {occupation || 'N/A'}
                        </Typography>
                    </Grid>
                </Grid>
            }
            </Grid>
            <Grid item xs={2} container alignItems='right' justifyContent='right'>
            {
                isEdit ?
                <LoadingButton variant='contained' onClick={handleClickSaveOccupation} loading={saving}> 
                    Save
                </LoadingButton>
                :
                <IconButton color='primary' onClick={handleClickEditOccupation}>
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

export default OccupationEditor