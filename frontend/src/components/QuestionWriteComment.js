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
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog'
import DialogActions from '@mui/material/DialogActions'
import DialogContent from '@mui/material/DialogContent'
import DialogTitle from '@mui/material/DialogTitle'
import Typography from '@mui/material/Typography'

// Material UI icon
import CreateIcon from '@mui/icons-material/Create'

const QuestionWriteComment = () => {

    const axiosPrivate = useAxiosPrivate()

    const questionId = useSelector(state => state.questionBoard.value.questionId)

    const maxLength = 1000

    const [ comment, setComment ] = useState('')

    const [ message, setMessage ] = useState(`(${comment.length}/${maxLength})`)

    const [ error, setError ] = useState(false)

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
        
        const payload = {
            questionId: questionId,
            comment: comment

        }
        const response = await apiPrivate.post(axiosPrivate, '/api/comment/create', payload)

        if (response.status === 201) {
            setComment('')
            setMessage(`(${comment.length}/${maxLength})`)
            setError(false)
            handleCloseDialog()
        } else {
            alert('Error, please try again.')
        }
    }

    const handleBlur = () => {
        setMessage(`(${comment.length}/${maxLength})`)
        setError(false)
    }

    const [ openDialog, setOpenDialog ] = useState(false);

    const handleOpenDialog = () => {
        setOpenDialog(true)
    }

    const handleCloseDialog = () => {
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
                    <Button variant='outlined' onClick={handleCloseDialog} color="primary">
                        Cancel
                    </Button>
                    <Button variant='contained' onClick={handleClickComment} color="primary">
                        Comment
                    </Button>
                </DialogActions>
            </Dialog>
        </Box>
    )
}

export default QuestionWriteComment