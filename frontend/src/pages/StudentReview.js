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

// Material UI icon
import AddIcon from '@mui/icons-material/Add'
import EditIcon from '@mui/icons-material/Edit'
import StarIcon from '@mui/icons-material/Star'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_ALL_REVIEWS_BY_COURSE } from '../utils/url'

const StudentReview = () => {

    // const { courseId } = useParams()

    // const navigate = useNavigate()

    // const axiosPrivate = useAxiosPrivate()

    // const [ reviews, setReviews ] = useState([])
    // const [ isReview, setIsReview ] = useState(false)
    // const [ myReviewId, setMyReviewId ] = useState(null)
    // const [ rating, setRating ] = useState(null)
    // const [ totalReview, setTotalReview ] = useState('')

    // const [ loading, setLoading ] = useState(true)

    // useEffect(async () => {

    //     const response = await apiPrivate.get(axiosPrivate, URL_GET_ALL_REVIEWS_BY_COURSE.replace('{courseId}', courseId))

    //     if (response.status === 200) {
    //         setReviews(response.data.reviews)
    //         setIsReview(response.data.isReview)
    //         setMyReviewId(response.data.myReviewId)
    //         setRating(response.data.star)
    //         setTotalReview(`(${response.data.totalReviewUser})`)
    //     } else {
    //         alert('fail')
    //     }
    //     setLoading(false)

    // }, [])

    // const handleClickCreateReview = () => {
    //     navigate(`/student/course/${courseId}/review/create`)
    // }

    // const handleClickEditReview = () => {
    //     navigate(`/student/course/${courseId}/review/${myReviewId}`)
    // }

    return (
        <Container>
            <AppBarSearchHeader />
            <br/>
            <Grid container>
                <Grid item xs={3} md={3}>
                    <StudentMenu active='review' /> 
                </Grid>
                {/* <Grid item xs={9} marginBottom={5}>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={3}>
                            <Typography variant='h6'>
                                Review
                            </Typography>
                        </Grid>
                        <Grid item xs={6}>
                        {
                            loading === false ?
                            <Box sx={{ display: 'flex', alignItems: 'center' }}>
                                <Rating
                                    size='large' 
                                    value={rating} 
                                    precision={0.1} 
                                    readOnly 
                                    emptyIcon={<StarIcon fontSize="inherit" />}
                                />
                                <Typography component='span' sx={{ pl: 1}}>{rating}</Typography>
                                <Typography component='span' sx={{ pl: 1}}>{totalReview}</Typography>
                            </Box>
                            :
                            null
                        }
                        </Grid>
                        <Grid item xs={2}>
                        {
                            loading === true ?
                                null
                                :
                                    isReview ?
                                    <Fab size='small' color='primary' onClick={handleClickEditReview}>
                                        <EditIcon />
                                    </Fab>
                                    :
                                    <Fab size='small' color='primary' onClick={handleClickCreateReview}>
                                        <AddIcon />
                                    </Fab>
                        }    
                        </Grid>
                    </Grid>
                    <Grid container sx={{ mt: 2 }}>
                        <Grid item xs={2}></Grid>
                        <Grid item xs={8} sx={{ pt: 3 }}>
                        {
                            reviews?.map((review, index) => {

                                if (review.visible === true) {
                                    return (
                                        <ReviewCard
                                            key={index}
                                            rating={review.rating}
                                            comment={review.comment}
                                            firstname={review.firstname}
                                            lastname={review.lastname} 
                                            datetime={review.reviewDateTime}
                                        />
                                    )
                                }
                            })
                        }    
                            <LoadingCircle loading={loading} layoutLeft={60} />
                        </Grid>
                    </Grid>
                </Grid> */}
            </Grid>
        </Container>
    )
}

export default StudentReview