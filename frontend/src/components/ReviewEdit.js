import React, { useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'

// Material UI component
import Rating from '@mui/material/Rating'
import Typography from '@mui/material/Typography'
import Paper from '@mui/material/Paper'
import Grid from '@mui/material/Grid'
import TextField from '@mui/material/TextField'
import LoadingButton from '@mui/lab/LoadingButton';

// Material UI icon
import StarIcon from '@mui/icons-material/Star'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_EDIT_REVIEW } from '../utils/url'

const ReviewEdit = ({ dataRating, dataReview }) => {

    const { courseId, reviewId } = useParams()
    const navigate = useNavigate()
    const axiosPrivate = useAxiosPrivate()

    const [ rating, setRating ] = useState(dataRating)
    const [ messageRating, setMessageRating ] = useState(dataRating)
    const [ errorRating, setErrorRating ] = useState(false)

    const maxLength = 1000
    const [ review, setReview ] = useState(dataReview)
    const [ messageReview, setMessageReview ] = useState(`(${review.length}/${maxLength})`)
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

    const handleClickReview = async () => {
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
        const url = URL_EDIT_REVIEW
            .replace('{courseId}', courseId)
            .replace('{reviewId}', reviewId)
        const payload = {
            courseId: courseId,
            reviewId: reviewId,
            rating: rating,
            review: review
        }
        const response = await apiPrivate.put(axiosPrivate, url, payload)

        if (response.status === 204) {
            navigate(`/student/course/${courseId}/review`)
        } else {
            alert(response.message)
        }
        setSaving(false)
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

export default ReviewEdit