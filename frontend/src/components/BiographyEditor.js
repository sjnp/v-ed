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

const BiographyEditor = () => {

    const apiPrivate = useApiPrivate()
    const alertMessage = useAlertMessage()

    const maxLength = 1024

    const [ biography, setBiography ] = useState('')
    const [ message, setMessage ] = useState(`(0/${maxLength})`)
    const [ error, setError ] = useState(false)
    const [ loading, setLoading ] = useState(true)
    const [ saving, setSaving ] = useState(false)
    const [ isEdit, setIsEdit ] = useState(false)
    
    useEffect(async () => {
        const response = await apiPrivate.get(URL_GET_PROFILE)
        if (response.status === 200) {
            setBiography(response.data.biography)
        }
        setLoading(false)
    }, [])

    const handleChangeBiography = (event) => {
        if (event.target.value.length <= maxLength) {
            setBiography(event.target.value)
            setMessage(`(${event.target.value.length}/${maxLength})`)
            setError(false)
        } else {
            setMessage(`(${biography.length}/${maxLength}) limit ${maxLength} character`)
            setError(true)
        }
    }

    const handleBlurBiography = () => {
        setMessage(`(${biography.length}/${maxLength})`)
        setError(false)
    }

    const handleClickEditBiography = () => {
        setIsEdit(true)
    }

    const handleClickSaveBiography = () => {
        setSaving(true)

        setTimeout(() => {
            setSaving(false)
            setIsEdit(false)
            alertMessage.show('success', 'Update biography successful')
        }, 3000)
    }

    return (
        <Grid container direction='row' alignItems='center' justifyContent='center' >
            <Grid item xs={3}>
                <Typography>Biography</Typography>
            </Grid>
            <Grid item xs={7} container >
            {
                isEdit ?
                <Grid item xs={12} container direction='row' alignItems='center' justifyContent='center'>
                    <Grid item xs={12}>
                        <TextField
                            label='Biography'
                            type='text'
                            size='small'
                            fullWidth
                            multiline
                            disabled={saving}
                            value={biography}
                            onChange={handleChangeBiography}
                            onBlur={handleBlurBiography}
                            helperText={message}
                            error={error}
                        />
                    </Grid>
                </Grid>
                :
                <Grid item xs={12} container direction='column'>
                    <Grid item xs={12}>
                        <Typography color='gray'>
                            {biography || 'N/A'}
                        </Typography>
                    </Grid>
                </Grid>
            }
            </Grid>
            <Grid item xs={2} container alignItems='right' justifyContent='right'>
            {
                isEdit ?
                <LoadingButton variant='contained' onClick={handleClickSaveBiography} loading={saving}>
                    Save
                </LoadingButton>
                :
                <IconButton color='primary' onClick={handleClickEditBiography} disabled={loading}>
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

export default BiographyEditor