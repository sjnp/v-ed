import React, { useState, useEffect } from 'react'

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
import { URL_GET_PROFILE } from '../utils/url'

const OccupationEditor = () => {

    const apiPrivate = useApiPrivate()
    const alertMessage = useAlertMessage()

    const maxLength = 255

    const [ occupation, setOccupation ] = useState('')
    const [ message, setMessage ] = useState(`(0/${maxLength})`)
    const [ error, setError ] = useState(false)
    const [ loading, setLoading ] = useState(true)
    const [ saving, setSaving ] = useState(false)
    const [ isEdit, setIsEdit ] = useState(false)

    useEffect(async () => {
        const response = await apiPrivate.get(URL_GET_PROFILE)
        if (response.status === 200) {
            setOccupation(response.data.occupation)
        }
        setLoading(false)
    }, [])

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

    const handleClickSaveOccupation = () => {
        setSaving(true)

        setTimeout(() => {
            setSaving(false)
            setIsEdit(false)
            alertMessage.show('success', 'Update occupation successful')
        }, 3000)
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
                <IconButton color='primary' onClick={handleClickEditOccupation} disabled={loading}>
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