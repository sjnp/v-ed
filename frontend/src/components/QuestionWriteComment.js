import React, { useState } from 'react'
import { useSelector } from 'react-redux'

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

import CircularProgress from '@mui/material/CircularProgress'

// Material UI icon
import CreateIcon from '@mui/icons-material/Create'

// url
import { URL_CREATE_COMMENT } from '../utils/url'

const QuestionWriteComment = ({ onCreateCommentSuccess }) => {

    const axiosPrivate = useAxiosPrivate()

    const questionId = useSelector(state => state.questionBoard.value.questionId)
    
    const maxLength = 1000

    const [ comment, setComment ] = useState('')

    const [ message, setMessage ] = useState(`(${comment.length}/${maxLength})`)

    const [ error, setError ] = useState(false)

    const [ loading, setLoading ] = useState(false)

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
            questionId: questionId,
            comment: comment
        }
        const response = await apiPrivate.post(axiosPrivate, URL_CREATE_COMMENT, payload)

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

    const [ openDialog, setOpenDialog ] = useState(false);

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
                    {
                        loading && 
                        <CircularProgress
                            size={24}
                            sx={{
                                color: 'green', 
                                position: 'absolute', 
                                top: '50%', 
                                left: '50%', 
                                mt: '-12px', 
                                ml: '-12px'
                            }}
                        />
                    }
                </DialogActions>
            </Dialog>
        </Box>
    )
}

export default QuestionWriteComment