import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'

// Material UI component
import Paper from '@mui/material/Paper'
import Typography from '@mui/material/Typography'
import Divider from '@mui/material/Divider'
import Grid from '@mui/material/Grid'
import Input from '@mui/material/Input'
import Chip from '@mui/material/Chip'
import IconButton from '@mui/material/IconButton'
import LoadingButton from '@mui/lab/LoadingButton'

// Material UI icon
import UploadFileIcon from '@mui/icons-material/UploadFile'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_UPLOAD_ANSWER_URL } from '../utils/url'

const Answer = ({ question, index }) => {

    const { courseId, chapterIndex } = useParams()

    const axiosPrivate = useAxiosPrivate()

    const questionText = `${Number(index) + 1}. ${question}`

    const [ fileName, setFileName ] = useState('')
    
    const [ uploading, setUploading ] = useState(false)

    const handleChangeUploadFile = (event) => {
        setFileName(event.target.files[0].name)
    }

    const handleUploadAnswer = async () => {
        setUploading(true)
        setTimeout(() => setUploading(false), 3000)

        // TODO : send file name to server for get preauthenticated
        const url = URL_GET_UPLOAD_ANSWER_URL
            .replace('{courseId}', courseId)
            .replace('{chapterIndex}', chapterIndex)
            .replace('{noIndex}', index)
            .replace('{fileName}', fileName)
        
        const response = await apiPrivate.get(axiosPrivate, url)

        if (response.status === 200) {
            alert(response.data)
        }


        // TODO : using preauthenticated upload file to brucket

        // TODO : Success or fail case, send upload response to server
        //        when success case : save file name into database
        //        when fail case : server send request for delete file on brucket
    }

    return (
        <Paper sx={{ width: '80%', m: 'auto'}}>
            <Grid container pt={2} pl={2} pr={2} pb={1}>
                <Grid item xs={12}>
                    <Typography fontWeight='bold'>{questionText}</Typography>
                </Grid>
                <Grid item xs={12} pt={2} pb={1}>
                    <Divider />
                </Grid>
                <Grid item xs={1}>
                    <label>
                        <Input type='file' sx={{ display: 'none' }} onChange={handleChangeUploadFile} />
                        <IconButton color='default' component='span' title='Upload file'>
                            <UploadFileIcon />
                        </IconButton>
                    </label>
                </Grid>
                <Grid item xs={9} pt={1} pl={1} pr={1}>
                {
                    fileName ?
                    <Chip variant='outlined' size='small' label={fileName} sx={{ pl: 0.5, pr: 0.5 }} />
                    :
                    null
                }
                </Grid>
                <Grid item xs={2}>
                {
                    fileName ?
                    <LoadingButton variant='contained' loading={uploading} onClick={handleUploadAnswer}>
                        Send
                    </LoadingButton>
                    :
                    null
                }     
                </Grid>
            </Grid>
        </Paper>
    )
}

export default Answer