import React, { useState } from 'react'
import { useParams } from 'react-router-dom'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// api
import apiPrivate from '../api/apiPrivate'

// component
import LoadingCircle from './LoadingCircle'

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

// Material UI icon
import CreateIcon from '@mui/icons-material/Create'

// url
import { URL_CREATE_COMMENT } from '../utils/url'

const QuestionWriteComment = ({ onCreateCommentSuccess }) => {

    const { courseId ,questionBoardId } = useParams()

    const axiosPrivate = useAxiosPrivate()
    
    const maxLength = 1000
    const [ comment, setComment ] = useState('')
    const [ message, setMessage ] = useState(`(${comment.length}/${maxLength})`)
    const [ error, setError ] = useState(false)

    const [ loading, setLoading ] = useState(false)
    const [ openDialog, setOpenDialog ] = useState(false);

    const handleChangeComment = (event) => {
        if (event.target.value.length <= maxLength) {
            setComment(event.target.value)
            setMessage(`(${event.target.value.length}/${maxLength})`)
            setError(false)
        } else {
            setMessage(`(${comment.length}/${maxLength}) limit ${maxLength} character.`)
            setError(true)
        }
    }

    const handleClickComment = async () => {

        if (comment.length === 0) {
            setMessage(`(${comment.length}/${maxLength}) is required.`)
            setError(true)
            return
        }

        setLoading(true)
        
        const payload = {
            questionId: questionBoardId,
            comment: comment
        }
        const response = await apiPrivate.post(axiosPrivate,
          URL_CREATE_COMMENT.replace('{courseId}', courseId),
          payload)

        if (response.status === 201) {
            setComment('')
            setMessage(`(${comment.length}/${maxLength})`)
            setError(false)
            handleCloseDialog()
            onCreateCommentSuccess(response.data)
        } else {
            alert('Error, please try again.')
        }

        setLoading(false)
    }

    const handleBlur = () => {
        setMessage(`(${comment.length}/${maxLength})`)
        setError(false)
    }

    const handleOpenDialog = () => {
        setOpenDialog(true)
    }

    const handleCloseDialog = (event, reason) => {

        if (loading && reason === 'backdropClick') return

        setOpenDialog(false)
        setMessage(`(${comment.length}/${maxLength})`)
        setError(false)
    }

    return (
        <Box>
            <Grid container>
                <Grid item xs={11}></Grid>
                <Grid item xs={1}>
                    <Fab color='primary' onClick={handleOpenDialog} size='small' sx={{ position: 'fixed', bottom: 20 }}>
                        <CreateIcon />
                    </Fab>
                </Grid>
            </Grid>
            <Dialog open={openDialog} onClose={handleCloseDialog}>
                <DialogTitle>
                    Write comment
                    <Typography variant='caption' sx={{ ml: 1, color: error ? 'red' : 'gray' }}>
                        {message}
                    </Typography>
                </DialogTitle>
                <DialogContent sx={{ width: 600 }}>
                    <TextField
                        autoFocus
                        id="comment"
                        label="Comment"
                        type="email"
                        margin="normal"
                        disabled={loading}
                        required
                        fullWidth
                        multiline
                        value={comment}
                        onChange={handleChangeComment}
                        error={error}
                        onBlur={handleBlur}
                    />
                </DialogContent>
                <DialogActions>
                    <Button
                        variant='outlined'
                        color="primary" 
                        disabled={loading}
                        sx={{ mr: 1, mb:1 }}
                        onClick={handleCloseDialog} 
                    >
                        CANCEL
                    </Button>
                    <Button 
                        variant='contained' 
                        color="primary"
                        disabled={loading}
                        sx={{ mr: 1, mb:1 }}
                        onClick={handleClickComment}                         
                    >
                        COMMENT
                    </Button>
                    <LoadingCircle loading={loading} layoutLeft={50} />
                </DialogActions>
            </Dialog>
        </Box>
    )
}

export default QuestionWriteComment