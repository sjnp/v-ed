import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import axios from '../api/axios'

// component
import stringToColor from './stringToColor'
import AlertMessage from './AlertMessage'

// Material UI component
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Avatar from '@mui/material/Avatar'
import Input from '@mui/material/Input'
import LoadingButton from '@mui/lab/LoadingButton'

// Materail UI icon
import AddAPhotoIcon from '@mui/icons-material/AddAPhoto'

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'
import useAlertMessage from '../hooks/useAlertMessage'

// utils
import { uploadUtility } from '../utils/uploadUtility'

// url
import { URL_CREATE_UPLOAD_DISPLAY } from '../utils/url'
import { URL_UPDATE_DISPLAY } from '../utils/url'

// feature slice
import { updatePicture } from '../features/profileSlice'

const DisplayEditor = ({ defaultDisplayUrl }) => {

    const dispatch = useDispatch()
    const apiPrivate = useApiPrivate()
    const alertMessage = useAlertMessage()
    const username = useSelector(state => state.auth.value.username)

    const defaultImageSize = 100

    const [ displayUrl, setDisplayUrl ] = useState(defaultDisplayUrl || '')
    const [ uploading, setUploading ] = useState(false)

    const handleChangeFile = async (event) => {
        const file = event.target.files[0]
        if (file.type !== 'image/jpeg') {
            alertMessage.show('warning', 'Using image jpg/jpeg type only')
            return
        }

        setUploading(true)
        // get upload display url
        const responseGetUploadUrl = await apiPrivate.post(URL_CREATE_UPLOAD_DISPLAY, null)
        if (responseGetUploadUrl.status !== 200) {
            alertMessage.show('error', 'Server not response')
            return
        }
        const preauthenticatedUrl = responseGetUploadUrl.data

        // upload file into bucket & commit
        const multipartUploadUri = await uploadUtility.createMultipartUploadUri(preauthenticatedUrl)
        const chunks = uploadUtility.splitFile(file)
        for (let i = 0; i < chunks.length; ++i) {
            let uploadUrl = multipartUploadUri + (i+1)
            await axios.put(uploadUrl, chunks[i])
                .then(() => console.log('success item', (i+1)))
                .catch(() => console.error('fail item', (i+1)))
        }
        const commitResponse = await uploadUtility.commit(multipartUploadUri)
        if (commitResponse.status !== 200) {
            alertMessage.show('error', 'Server not response')
            return
        }

        // save display url
        const responseSaveDisplay = await apiPrivate.put(URL_UPDATE_DISPLAY, null)
        if (responseSaveDisplay.status === 200) {
            setDisplayUrl(responseSaveDisplay.data)
            alertMessage.show('success', 'Upload display successful')
            dispatch( updatePicture({ uriPicture: responseSaveDisplay.data }) )
        } else {
            alertMessage.show('error', 'Upload display fial, please try again')
        }
        setUploading(false)
    }

    return (
        <Grid container>
            <Grid item xs={3} container direction='column' alignItems='left' justifyContent='center'>
                <Typography>Display</Typography>
            </Grid>
            <Grid item xs={6} container direction='row' alignItems='center' justifyContent='center'>
                <Typography color='gray'>
                    Add new display now
                </Typography>
                <label>
                    <Input 
                        type='file' 
                        onChange={handleChangeFile} 
                        disabled={uploading} 
                        inputProps={{ accept: 'image/jpeg' }}
                        sx={{ display: 'none' }}
                    />
                    <LoadingButton component='span' variant='text' loading={uploading}>
                        <AddAPhotoIcon />
                    </LoadingButton>
                </label>
            </Grid>
            <Grid item xs={3} container alignItems='right' justifyContent='right'>
                <Avatar
                    alt={username}
                    src={displayUrl || '/static/images/avatar/2.jpg'}
                    sx={{ 
                        bgcolor: stringToColor(username),
                        width: defaultImageSize,
                        height: defaultImageSize
                    }}
                />
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

export default DisplayEditor