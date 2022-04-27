import React, { useState } from 'react'
import { useParams } from 'react-router-dom'
import axios from "axios"

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// component
import LinearProgressWithLabel from "./LinearProgressWithLabel"

// Material UI component
import Paper from '@mui/material/Paper'
import Typography from '@mui/material/Typography'
import Divider from '@mui/material/Divider'
import Button from '@mui/material/Button'
import Grid from '@mui/material/Grid'
import Input from '@mui/material/Input'
import Chip from '@mui/material/Chip'

// Material UI icon
import UploadFileIcon from '@mui/icons-material/UploadFile'
import CancelIcon from '@mui/icons-material/Cancel'

// utils
import { uploadUtility } from "../utils/uploadUtility"

// url
import { URL_ASSIGNMENT_UPLOAD_ANSWER } from '../utils/url'
import { URL_ASSIGNMENT_ANSWER_SAVE } from '../utils/url'

const AssignmentAnswer = ({ chapterNo, no, question, commentInstructor }) => {

    const axiosPrivate = useAxiosPrivate()

    const { courseId } = useParams()

    const [ answerId, setAnswerId ] = useState(null)
    const [ fileName, setFileName ] = useState('')
    const [ progress, setProgress ] = useState(0)
    const [ isUpload, setIsUpload ] = useState(false)
    const [ isComplete, setIsComplete ] = useState(false)

    const handleUploadAnswer = async (event) => {
        const file = event.target.files[0]
        setFileName(file.name)
        setIsUpload(true)

        const parUrl = await axiosPrivate.post(
            URL_ASSIGNMENT_UPLOAD_ANSWER, 
            {
                fileName: file.name,
                courseId: courseId,
                chapterNo: chapterNo,
                no: no
            }
        )
        .then(res => res.data)
        .catch(err => err.response)

        const multipartUploadUri = await uploadUtility.createMultipartUploadUri(parUrl)
        const chunks = uploadUtility.splitFile(file)

        let percent = 0
        for (let i = 0; i < chunks.length; ++i) {
            percent += 100 / chunks.length
            await axios.put(multipartUploadUri + (i + 1), chunks[i])
                .then(() => setProgress(percent))
                .catch(() => console.log('item', i+1, 'fail'))
        }

        await uploadUtility.commit(multipartUploadUri)
        
        const startIndex = multipartUploadUri.indexOf('answer_sid')
        const endIndex = multipartUploadUri.indexOf('/id/')
        const fileNameForSave = multipartUploadUri.substring(startIndex, endIndex)

        const response = await axiosPrivate.post(
            URL_ASSIGNMENT_ANSWER_SAVE,
            {
                chapter: chapterNo,
                no: no,
                fileName: fileNameForSave
            }
        )
        .then(res => res)
        .catch(err => err.response)

        if (response.status === 201) {
            setIsUpload(false)
            setIsComplete(true)
            setAnswerId(response.data)
            event.target.value = null
        } else {
            alert(`save ${fileNameForSave} fail`)
        }
    }

    const handleCancelAnswer = async () => {
        if (window.confirm('Delete sure ?')) {
            if (answerId) {
                const response = await axiosPrivate.delete('/api/assignment/answer/' + answerId)
                    .then(res => res)
                    .catch(err => err.response)
                if (response.status === 204) {
                    setIsComplete(false)
                    setAnswerId(null)
                }
            }
        } 
    }

    return (
        <Paper sx={{ padding: 2, width: '80%', margin: 2 }}>
            <Grid container>
                <Grid item xs={12}>
                    <Typography variant='h6'>{no}. {question}</Typography>
                </Grid>
                <Grid item xs={12} sx={{ mb: 3, mt: 3 }}>
                    <Divider />
                </Grid>
                <Grid item xs={4} >
                    <label hidden={isComplete}>
                        <Input type='file' sx={{ display: 'none' }} onChange={handleUploadAnswer} />
                        <Button component='span' variant='contained' startIcon={<UploadFileIcon />}>
                            Upload answer
                        </Button>
                    </label>
                    <label hidden={!isComplete}>
                        <Chip
                            label={fileName}
                            variant='outlined'
                            onDelete={handleCancelAnswer}
                            deleteIcon={<CancelIcon titleAccess='answer cancel' />}
                        />
                    </label>
                </Grid>
                <Grid item xs={8}>
                    <div hidden={!isUpload} >
                        <LinearProgressWithLabel value={progress} fileName={fileName} />
                    </div>
                </Grid>
            </Grid>
        </Paper>
    )
}

export default AssignmentAnswer