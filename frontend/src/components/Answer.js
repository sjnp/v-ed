import React, { useState } from 'react'
import { useParams } from 'react-router-dom'
import axios from '../api/axios'

// Material UI component
import Paper from '@mui/material/Paper'
import Grid from '@mui/material/Grid'
import Divider from '@mui/material/Divider'
import Input from '@mui/material/Input'
import IconButton from '@mui/material/IconButton'
import Typography from '@mui/material/Typography'
import Box from '@mui/material/Box'
import Chip from '@mui/material/Chip'
import CircularProgress from '@mui/material/CircularProgress'
import LoadingButton from '@mui/lab/LoadingButton'

// Material UI icon
import UploadFileIcon from '@mui/icons-material/UploadFile'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_UPLOAD_ANSWER_URL } from '../utils/url'
import { URL_CREATE_ANSWER } from '../utils/url'

// utils
import { uploadUtility } from '../utils/uploadUtility'

const Answer = ({ assignment, answer, noIndex, instructorComment }) => {

    const { courseId, chapterIndex } = useParams()
    const axiosPrivate = useAxiosPrivate()

    const [ file, setFile ] = useState(answer ? { name: answer.fileName } : null)
    const [ state, setState ] = useState(answer ? 'Finish' : 'Start')
    const [ uploading, setUploading ] = useState(false)
    const [ progressUploading, setProgressUploading ] = useState(0)

    const handleChangeFile = (event) => {
        setFile(event.target.files[0])
        setState('Ready')
    }

    const handleClickUploadFile = async () => {
        setUploading(true)
        setState('Link getting')

        const url = URL_GET_UPLOAD_ANSWER_URL
            .replace('{courseId}', courseId)
            .replace('{chapterIndex}', chapterIndex)
            .replace('{noIndex}', noIndex)
            .replace('{fileName}', file.name)

        let response = await apiPrivate.get(axiosPrivate, url)
        if (response.status !== 200) {
            alert('Error get link my server')
            setUploading(false)
            setState('Ready')
            return
        }
        
        setState('Uploading...')
        const preauthenticatedUrl = response.data
        const multipartUploadUri = await uploadUtility.createMultipartUploadUri(preauthenticatedUrl)

        const chunks = uploadUtility.splitFile(file)
        let percent = 0
        for (let i = 0; i < chunks.length; ++i) {
            percent += 100 / chunks.length
            let uploadUrl = multipartUploadUri + (i+1)
            await axios.put(uploadUrl, chunks[i])
                .then(() => setProgressUploading(percent))
                .catch(() => console.log(`item ${i+1} fail`))
        }

        response = await uploadUtility.commit(multipartUploadUri)
        if (response.status === 200) {
            setState('Saving...')

            const createAnswerUrl = URL_CREATE_ANSWER.replace('{courseId}', courseId)
            const payload = {
                courseId: parseInt(courseId),
                chapterIndex: parseInt(chapterIndex),
                noIndex: noIndex,
                fileName: file.name
            }
            response = await apiPrivate.post(axiosPrivate, createAnswerUrl, payload)
            if (response.status === 201) {
                setState('Finish')
            }
        }
    }

    return (
        <Paper sx={{ width: '80%', m: 'auto' }}>
            <Grid container pt={2} pl={2} pr={2} pb={1}>
                <Grid item xs={12}>
                    <Typography fontWeight='bold'>
                        {`${parseInt(noIndex) + 1}. ${assignment}`}
                    </Typography>
                </Grid>
                <Grid item xs={12} mt={1}>
                {
                    state === 'Finish' ?
                    <Chip variant='outlined' label={file?.name} sx={{ mb: 1 }} />
                    :
                    null
                }
                </Grid>
                <Grid item xs={12}>
                {
                    state === 'Finish' ?
                    null
                    :
                    <Divider sx={{ mt: 1, mb: 1 }} />
                }
                </Grid>
                <Grid item xs={1}>
                {
                    state === 'Start' || state === 'Ready' ?
                    <label>
                        <Input type='file' onChange={handleChangeFile} sx={{ display: 'none' }} />
                        <IconButton component='span'><UploadFileIcon /></IconButton>
                    </label>
                    :
                    state === 'Link getting' ?
                    <IconButton disabled={true}><UploadFileIcon /></IconButton>
                    :
                    state === 'Uploading...' ?
                    <Box pt={1} pl={1}>
                        <CircularProgress variant='determinate' size={25} value={progressUploading} />
                    </Box>
                    :
                    state === 'Saving...' ?
                    <Box pt={1} pl={1}>
                        <CircularProgress size={25} />
                    </Box>
                    :
                    null
                }
                </Grid>
                <Grid item xs={9}>
                {
                    state === 'Start' || state === 'Ready' || state === 'Link getting' ?
                    <Typography noWrap pt={1}>{file?.name}</Typography>
                    :
                    state === 'Uploading...' ?
                    <Typography pt={1}>{`${Math.round(progressUploading)}% ${state}`}</Typography>
                    :
                    state === 'Saving...' ?
                    <Typography pt={1}>{state}</Typography>
                    :
                    null
                }
                </Grid>
                <Grid item xs={2}>
                {
                    state === 'Ready' || state === 'Link getting' ?
                    <LoadingButton variant='contained' fullWidth loading={uploading} onClick={handleClickUploadFile}>
                        Send
                    </LoadingButton>
                    :
                    null
                }
                </Grid>
                <Grid item xs={12}>
                {
                    instructorComment ?
                    <Box bgcolor='#f3f3f3' p={1} mb={1} borderRadius={1}>{instructorComment}</Box>
                    :
                    null
                }
                </Grid>
            </Grid>
        </Paper>
    )
}

export default Answer