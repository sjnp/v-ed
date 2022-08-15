import React, { useState } from 'react'
import { useParams } from 'react-router-dom'

// Material UI component
import Grid from '@mui/material/Grid'
import Box from '@mui/material/Box'
import TextField from '@mui/material/TextField'
import Typography from '@mui/material/Typography'
import LoadingButton from '@mui/lab/LoadingButton'

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'

// url
import { URL_UPDATE_ANSWER_COMMENT_INSTRUCTOR } from '../utils/url'

const InstructorCommentAnswer = ({ answerId, commentInstructor, onCommentSuccess }) => {

    const { courseId } = useParams()
    const apiPrivate = useApiPrivate()

    const maxLength = 1024

    const [ isCommentInstructor, setIsCommentInstructor ] = useState(commentInstructor === null ? false : true)
    const [ comment, setComment ] = useState(commentInstructor || '')
    const [ message, setMessage ] = useState(`(0/${maxLength})`)
    const [ error, setError ] = useState(false)
    const [ saving, setSaving ] = useState(false)

    const handleChangeComment = (event) => {
        if (event.target.value.length <= maxLength) {
            setComment(event.target.value)
            setMessage(`(${event.target.value.length}/${maxLength})`)
            setError(false)
        } else {
            setMessage(`(${event.target.value.length - 1}/${maxLength}) max length ${maxLength} characters`)
            setError(true)
        }
    }

    const handleBlurComment = () => {
        setMessage(`(${comment.length}/${maxLength})`)
        setError(false)
    }

    const handleClickComment = async () => {
        if (comment.trim().length === 0) {
            setMessage(`(${comment.length}/${maxLength}) comment is required`)
            setError(true)
            return
        }

        setSaving(true)
        const url = URL_UPDATE_ANSWER_COMMENT_INSTRUCTOR
        const payload = {
            courseId: courseId,
            answerId: answerId,
            comment: comment
        }
        const response = await apiPrivate.put(url, payload)

        if (response.status === 201) {
            setIsCommentInstructor(true)
            onCommentSuccess()
        } else {
            alert('fail')
        }
        setSaving(false)
    }

    return (
        <Box>
        {
            isCommentInstructor ?
            <Grid container bgcolor='#f0f0f0' p={2} mt={2}>
                <Grid item xs={12}>
                    <Typography variant='caption' color='#777777'>
                        Comment instructor:
                    </Typography>
                </Grid>
                <Grid item xs={12}>
                    {comment}
                </Grid>
            </Grid>
            :
            <Grid container spacing={1}>
                <Grid item xs={12}>
                    <TextField
                        id='comment'
                        label='Comment instructor'
                        size='small'
                        type='text'
                        required
                        fullWidth
                        multiline
                        disabled={saving}
                        value={comment}
                        onChange={handleChangeComment}
                        helperText={message}
                        error={error}
                        onBlur={handleBlurComment}
                    />
                </Grid>
                <Grid item xs={12} container direction='row' justifyContent='center'>
                    <LoadingButton variant='contained' loading={saving} onClick={handleClickComment}>
                        Comment
                    </LoadingButton>
                </Grid>
            </Grid>   
        }
        </Box>
        
    )
}

export default InstructorCommentAnswer