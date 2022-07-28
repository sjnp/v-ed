import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import InstructorMenu from '../components/InstructorMenu'
import LoadingCircle from '../components/LoadingCircle'
import ReviewCard from '../components/ReviewCard'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Breadcrumbs from '@mui/material/Breadcrumbs'
import Rating from '@mui/material/Rating'
import Box from '@mui/material/Box'
import Skeleton from '@mui/material/Skeleton'

// Material UI icon
import StarIcon from '@mui/icons-material/Star'

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'
import useReasonReport from '../hooks/useReasonReport'

// url
import { URL_GET_REVIEWS_COURSE } from '../utils/url'

const InstructorReview = () => {

    const apiPrivate = useApiPrivate()
    const { courseId } = useParams()
    const createReasonReportRedux = useReasonReport()

    const [ stars, setStars ] = useState(null)
    const [ totalUserReview, setTotalUserReview ] = useState(null)
    const [ reviews, setReviews ] = useState([])
    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {
        const url = URL_GET_REVIEWS_COURSE.replace('{courseId}', courseId)
        const response = await apiPrivate.get(url)

        if (response.status === 200) {
            setStars(response.data.summary.star)
            setTotalUserReview(response.data.summary.totalUser)
            setReviews(response.data.reviews)
        } else {
            alert('Error: ', response.message)
        }
        setLoading(false)
        createReasonReportRedux()
    }, [])
    
    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <InstructorMenu active='review' />
                </Grid>
                <Grid item xs={9}>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={3}>
                            <Breadcrumbs>
                                <Typography color='black'>Reviews</Typography>
                            </Breadcrumbs>
                        </Grid>
                        <Grid item xs={4} display='flex' flexDirection='row' alignItems='center' justifyContent='center'>
                        {
                            loading ?
                            <Box display='flex' flexDirection='row' alignItems='center' justifyContent='center'>
                            {
                                Array(5).fill().map((element, index) => (
                                    <Skeleton key={index} variant="circular" width={20} height={20} sx={{ m: 0.5 }} />
                                ))
                            }
                            </Box>
                            :
                            <Box display='flex' flexDirection='row' alignItems='center' justifyContent='center'>
                                <Rating value={stars} precision={0.1} readOnly emptyIcon={<StarIcon fontSize="inherit" />} />
                                <Box display='flex' flexDirection='row' alignItems='center' justifyContent='center'>
                                    <Typography variant='subtitle1' pl={1} pt={0.1}>{stars}</Typography>
                                    <Typography variant='subtitle1' pl={1} pt={0.1}>({totalUserReview})</Typography>
                                </Box>
                            </Box>
                        }
                        </Grid>
                        <Grid item xs={4}></Grid>
                    </Grid>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={10} pt={5}>
                        {
                            loading ?
                            <LoadingCircle loading={loading} centerY={true} />
                            :
                            reviews?.map((review, index) => (
                                <ReviewCard
                                    key={index}
                                    reviewId={review.id}
                                    rating={review.rating}
                                    comment={review.comment}
                                    firstname={review.firstname}
                                    lastname={review.lastname} 
                                    datetime={review.reviewDateTime}
                                />
                            ))
                        }
                        </Grid>
                        <Grid item xs={1}></Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default InstructorReview