import React, { useState } from 'react'

// Material UI component
import Rating from '@mui/material/Rating'
import Typography from '@mui/material/Typography'
import Paper from '@mui/material/Paper'
import Grid from '@mui/material/Grid'
import TextField from '@mui/material/TextField'
import LoadingButton from '@mui/lab/LoadingButton';

// Material UI icon
import StarIcon from '@mui/icons-material/Star'

const ReviewCreate = () => {

    const [ rating, setRating ] = useState(null)
    const [ messageRating, setMessageRating ] = useState(null)
    const [ errorRating, setErrorRating ] = useState(false)

    const maxLength = 10
    const [ review, setReview ] = useState('')
    const [ messageReview, setMessageReview ] = useState(`(0/${maxLength})`)
    const [ errorReview, setErrorReview ] = useState(false)

    const [ saving, setSaving ] = useState(false)

    const handleChangeRating = (event, newRating) => {
        setRating(newRating)
    }

    const handleChangeActiveRating = (event, newHover) => {
        const newMessage = newHover > 0 ? newHover : rating
        setMessageRating(newMessage)
        setErrorRating(false)
    }

    const handleChangeReview = (event) => {
        if (event.target.value.length <= maxLength) {
            setReview(event.target.value)
            setMessageReview(`(${event.target.value.length}/${maxLength})`)
            setErrorReview(false)
        } else {
            setMessageReview(`(${review.length}/${maxLength}) limit ${maxLength} character`)
            setErrorReview(true)
        }
    }

    const handleBlurReview = () => {
        setMessageReview(`(${review.length}/${maxLength})`)
        setErrorReview(false)
    }

    const handleClickReview = () => {
        let invalid = false

        if (rating === null || rating < 0) {
            setErrorRating(true)
            setMessageRating('Rating is required')
            invalid = true
        }

        if (review.length === 0) {
            setMessageReview(`(${review.length}/${maxLength}) is required`)
            setErrorReview(true)
            invalid = true
        }

        if (invalid) return

        setSaving(true)
        const payload = {
            
        }

    }

    return (
        <Paper sx={{ mt: 3, p: 3 }}>
            <Grid container>
                <Rating 
                    value={rating} 
                    onChange={handleChangeRating}
                    onChangeActive={handleChangeActiveRating}
                    emptyIcon={<StarIcon fontSize="inherit" />}
                />
                {
                    errorRating ?
                    <Typography variant='caption' color='red' pl={1} pt={0.5}>{messageRating}</Typography>
                    :
                    <Typography pl={1}>{messageRating}</Typography>
                }
            </Grid>
            <Grid container>
                <TextField
                    label='Review'
                    size='small'
                    margin='normal'
                    required
                    fullWidth
                    value={review}
                    onChange={handleChangeReview}
                    helperText={messageReview}
                    error={errorReview}
                    onBlur={handleBlurReview}
                />
            </Grid>
            <Grid container direction='column' alignItems='center' justify='center'>
                <LoadingButton variant='contained' loading={saving} onClick={handleClickReview} sx={{ width: '20%' }}>
                    Review
                </LoadingButton>
            </Grid>
        </Paper>
    )
}

export default ReviewCreate