import React, { useState } from 'react'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// api
import apiPrivate from '../api/apiPrivate'


// Material UI
import Grid from '@mui/material/Grid'
import Paper from '@mui/material/Paper'
import Box from '@mui/material/Box'
// import Rating from '@material-ui/lab/Rating';
import Rating from "@mui/material/Rating"
import TextField from "@mui/material/TextField"
import Button from "@mui/material/Button"
import CircularProgress from '@mui/material/CircularProgress'

const ReviewWrite = ({ dataRating, dataComment }) => {

    const axiosPrivate = useAxiosPrivate()

    const [ rating, setRating ] = useState(0)
    const [ messageRating, setMessageRating ] = useState('')

    const maxLengthComment = 1000
    const [ comment, setComment ] = useState('')
    const [ messageComment, setMessageComment ] = useState(`(0/${maxLengthComment})`)
    const [ errorComment, setErrorComment ] = useState(false)

    const [ loading, setLoading ] = useState(false)

    const handleChangeRating = (event, newRating) => {
        if (newRating === null) return
        setRating(newRating)
    }
    
    const handleChangeActiveRating = (event, newRating) => {

        const value = newRating > 0 ? newRating : rating
        
        if (value > 0 && value <= 5) {
            setMessageRating(` ${value}`)
        } else {
            setMessageRating('')
        }
    }

    const handleChangeComment = (event) => {

        if (event.target.value.length <= maxLengthComment) {
            setComment(event.target.value)
            setMessageComment(`(${event.target.value.length}/${maxLengthComment})`)
            setErrorComment(false)
        } else {
            setMessageComment(`(${comment.length}/${maxLengthComment}) limit ${maxLengthComment} character.`)
            setErrorComment(true)
        }
    }

    const handleBlurComment = () => {
        setMessageComment(`(${comment.length}/${maxLengthComment})`)
        setErrorComment(false)
    }

    const postReview = async () => {

        let invalid = false

        if (rating === 0) {
            setMessageRating(<span style={{ fontSize: 13, color: 'red' }}> Rating is required</span>)
            invalid = true
        }

        if (comment.trim().length === 0) {
            setMessageComment('Comment is required')
            setErrorComment(true)
            invalid = true
        }

        if (invalid) return

        setLoading(true)

        const payload = {
            rating: rating,
            comment: comment
        }
        const response = await apiPrivate.post(axiosPrivate, '/api/review/create', payload)
        
        if (response.status === 201) {
            
        }
        setLoading(false)
    }

    return (
        <Paper sx={{ p: 2, width: 500 }}>
            <Grid container>
                <Grid item xs={12}>
                    <Rating 
                        name="rating-review"
                        value={rating}
                        precision={0.1}
                        onChange={handleChangeRating}
                        onChangeActive={handleChangeActiveRating}
                        onError={() => 'require'}
                        readOnly={loading}
                    />
                    {messageRating}
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        id="comment"
                        label="Comment"
                        variant="outlined"
                        margin="normal"
                        required 
                        fullWidth
                        multiline
                        rows={5}
                        value={comment}
                        helperText={messageComment}
                        error={errorComment}
                        onChange={handleChangeComment}
                        onBlur={handleBlurComment}
                        disabled={loading}
                    />
                </Grid>
                <Grid item xs={12}>
                    <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                        <Button variant='contained' onClick={postReview} disabled={loading}>
                            Review
                        </Button>
                    </Box>
                </Grid>
            </Grid>
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
        </Paper>
    )
}

export default ReviewWrite