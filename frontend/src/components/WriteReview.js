import React, { useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'

// Material UI component
import Rating from '@mui/material/Rating'
import Typography from '@mui/material/Typography'
import Box from '@mui/material/Box'
import Paper from '@mui/material/Paper'
import TextField from '@mui/material/TextField'
import LoadingButton from '@mui/lab/LoadingButton';

// Material UI icon
import StarIcon from '@mui/icons-material/Star'
import AddIcon from '@mui/icons-material/Add'
import EditIcon from '@mui/icons-material/Edit'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_CREATE_REVIEW } from '../utils/url'
import { URL_EDIT_REVIEW } from '../utils/url'

const WriteReview = ({ initRating, initReview, type }) => {

    const { courseId, reviewId } = useParams()

    const navigate = useNavigate()

    const axiosPrivate = useAxiosPrivate()

    const [ rating, setRating ] = useState(initRating || null)
    const [ messageRating, setMessageRating ] = useState('')
    const [ verifyRating, setVerifyRating ] = useState(initRating ? true : false)

    const handleChangeRating = (event, newRating) => {
        if (newRating !== null) {
            setRating(newRating)
            setMessageRating(newRating)
            setVerifyRating(true)
        } else {
            setRating(null)
            setMessageRating('')
            setVerifyRating(false)
        }
    }

    const handleChangeActiveRating = (event, newHover) => {
        if (newHover > 0) {
            setMessageRating(newHover)
        } else {
            setMessageRating(rating)
        }
    }

    const maxLengthReview = 1000
    const [ review, setReview ] = useState(initReview || '')
    const [ messageReview, setMessageReview ] = useState(`(${review.length}/${maxLengthReview})`)
    const [ verifyReview, setVerifyReview ] = useState(initReview ? true : false)

    const handleChangeReview = (event) => {
        if (event.target.value.length <= maxLengthReview) {
            setReview(event.target.value)
            setMessageReview(`(${event.target.value.length}/${maxLengthReview})`)
        }

        const verify = event.target.value.trim().length > 0
        setVerifyReview(verify)
    }

    const [ loading, setLoading ] = useState(false)

    const callApiCreateReview = async (payload) => {
        console.log('select create review')
        return await apiPrivate.post(axiosPrivate, URL_CREATE_REVIEW, payload)
    }

    const callApiEditReview = async (payload) => {
        console.log('select edit review')
        const url = URL_EDIT_REVIEW.replace('{reviewId}', reviewId)
        return await apiPrivate.put(axiosPrivate, url, payload)
    }

    const handleClickReview = async () => {
        setLoading(true)

        const payload = {
            courseId: courseId,
            rating: rating,
            review: review
        }
        console.log('before select method api')
        const callApi = type === 'create' ? callApiCreateReview : callApiEditReview
        const response = await callApi(payload)

        if (response.status === 201) {
            navigate(`/student/course/${courseId}/review`)
        } else {
            alert(`${type} review fail`)
        }
        setLoading(false)
    }

    return (
        <Paper sx={{ p: 3 }}>
            <Box sx={{ display: 'flex', alignItems: 'center' }}>
                <Rating
                    size='large' 
                    value={rating}
                    onChange={handleChangeRating} 
                    onChangeActive={handleChangeActiveRating}
                    emptyIcon={<StarIcon fontSize="inherit" />}
                />
                <Typography component='span' sx={{ pl: 1 }}>{messageRating}</Typography>
            </Box>
            <Box sx={{ mt: 3 }}>
                <TextField
                    id='review'
                    variant='outlined'
                    label='Review'
                    required
                    multiline
                    fullWidth
                    value={review}
                    helperText={messageReview}
                    onChange={handleChangeReview}
                />
            </Box>
            <Box sx={{ mt: 3, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                <LoadingButton 
                    variant='contained'
                    loading={loading}
                    loadingPosition='start'
                    startIcon={type === 'create' ? <AddIcon /> : <EditIcon />}
                    sx={{ width: '35%' }}
                    color='primary'
                    onClick={handleClickReview} 
                    disabled={!verifyRating || !verifyReview}
                >
                    Review
                </LoadingButton>
            </Box>
        </Paper>
    )
}

export default WriteReview