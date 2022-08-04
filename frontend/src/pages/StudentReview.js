import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import LoadingCircle from '../components/LoadingCircle'
import ReviewCard from '../components/ReviewCard'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Fab from '@mui/material/Fab'
import Rating from '@mui/material/Rating'
import Box from '@mui/material/Box'
import Breadcrumbs from '@mui/material/Breadcrumbs'
import Skeleton from '@mui/material/Skeleton'

// Material UI icon
import AddIcon from '@mui/icons-material/Add'
import EditIcon from '@mui/icons-material/Edit'
import StarIcon from '@mui/icons-material/Star'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'
import useReasonReport from '../hooks/useReasonReport'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_REVIEWS_BY_COURSE } from '../utils/url'

const StudentReview = () => {

    const { courseId } = useParams()
    const axiosPrivate = useAxiosPrivate()
    const navigate = useNavigate()
    const createReasonReportRedux = useReasonReport()

    const [ stars, setStars ] = useState(null)
    const [ totalUserReview, setTotalUserReview ] = useState(null)
    const [ reviews, setReviews ] = useState([])
    const [ myReviewId, setMyReviewId ] = useState(null)
    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {
        const url = URL_GET_REVIEWS_BY_COURSE.replace('{courseId}', courseId)
        const response = await apiPrivate.get(axiosPrivate, url)

        if (response.status === 200) {
            setStars(response.data.summary.star)
            setTotalUserReview(response.data.summary.totalUser)
            setReviews(response.data.reviews)
            setMyReviewId(response.data.myReviewId)
        } else {
            alert(response.message)
        }
        setLoading(false)
        createReasonReportRedux()
    }, [])

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <StudentMenu active='review' />
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
                        <Grid item xs={2}></Grid>
                        <Grid item xs={2}>
                        {
                            loading === true ?
                            null
                            :
                            myReviewId === null ?
                                <Fab 
                                    size='small' 
                                    color='primary' 
                                    onClick={() => navigate(`/student/course/${courseId}/review/create`)} 
                                    sx={{ position: 'fixed' }}
                                >
                                    <AddIcon titleAccess='Create review' />
                                </Fab>
                                :
                                myReviewId === 0 ?
                                    null
                                    :
                                    <Fab
                                        size='small' 
                                        color='primary' 
                                        onClick={() => navigate(`/student/course/${courseId}/review/${myReviewId}`)} 
                                        sx={{ position: 'fixed' }}
                                    >
                                        <EditIcon titleAccess='Edit review' />
                                    </Fab>
                        }
                        </Grid>
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
                                    displayUrl={review.displayUrl}
                                    reviewUsername={review.reviewUsername}
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

export default StudentReview