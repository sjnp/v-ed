import React, { useState } from 'react'
import { useParams } from 'react-router-dom'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// api
import apiPrivate from '../api/apiPrivate'

// Material UI component
import Grid from '@mui/material/Grid'
import Fab from '@mui/material/Fab'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import TextField from '@mui/material/TextField'
import Dialog from '@mui/material/Dialog'
import DialogActions from '@mui/material/DialogActions'
import DialogContent from '@mui/material/DialogContent'
import DialogTitle from '@mui/material/DialogTitle'
import Typography from '@mui/material/Typography'
import IconButton from '@mui/material/IconButton'
import LoadingButton from '@mui/lab/LoadingButton'

// Material UI icon
import CreateIcon from '@mui/icons-material/Create'
import CloseIcon from '@mui/icons-material/Close';

// url
import { URL_CREATE_COMMENT } from '../utils/url'

// utils
import scrollMove from '../utils/scrollMove'

const QuestionWriteComment = ({ onCreateCommentSuccess }) => {

    const { courseId, postId } = useParams()
    const axiosPrivate = useAxiosPrivate()

    const maxLength = 10
    const [ comment, setComment ] = useState('')
    const [ message, setMessage ] = useState(`(0/${maxLength})`)
    const [ error, setError ] = useState(false)

    const [ openCommentForm, setOpenCommentForm ] = useState(false)
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

    const handleCreateComment = async () => {
        if (comment.length === 0) {
            setMessage(`(${comment.length}/${maxLength}) is required`)
            setError(true)
            return
        }
        setSaving(true)
        
        const url = URL_CREATE_COMMENT.replace('{courseId}', courseId)
        const payload = {
            postId: postId,
            comment: comment
        }
        const response = await apiPrivate.post(axiosPrivate, url, payload)

        if (response.status === 201) {
            onCreateCommentSuccess(response.data)
            scrollMove.topToBottom()
            setOpenCommentForm(false)
            setComment('')
            setMessage(`(0/${maxLength})`)
            setError(false)
        } else {
            alert('Fail')
        }
        setSaving(false)
    }

    return (
        <Box>
            <Grid container>
                <Grid item xs={11}></Grid>
                <Grid item xs={1}>
                    <Fab color='primary' size='small' sx={{ position: 'fixed', bottom: 20 }} onClick={() => setOpenCommentForm(true)}>
                        <CreateIcon />
                    </Fab>
                </Grid>
            </Grid>
            <Dialog open={openCommentForm}>
                <DialogTitle>
                    Write comment
                    <Typography variant='caption' sx={{ ml: 1, color: error ? 'red' : 'gray' }}>
                        {message}
                    </Typography>
                    <IconButton sx={{ position: 'absolute', top: 8, right: 8 }} onClick={() => setOpenCommentForm(false)}>
                        <CloseIcon />
                    </IconButton>
                </DialogTitle>
                <DialogContent sx={{ width: 600 }}>
                    <TextField 
                        autoFocus
                        id="comment"
                        label="Comment"
                        type="text"
                        margin="normal"
                        required
                        fullWidth
                        multiline
                        value={comment}
                        onChange={handleChangeComment}
                        error={error}
                        onBlur={handleBlurComment}
                    />
                </DialogContent>
                <DialogActions sx={{ pr: 3, mt: -1, mb: 1 }}>
                    <Button variant='outlined' onClick={() => setOpenCommentForm(false)}>
                        Cancel
                    </Button>
                    <LoadingButton variant='contained' onClick={handleCreateComment} loading={saving}>
                        Comment
                    </LoadingButton>
                </DialogActions>
            </Dialog>
        </Box>
    )
}

export default QuestionWriteComment