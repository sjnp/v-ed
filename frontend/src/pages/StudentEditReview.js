import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import ReviewEdit from '../components/ReviewEdit'
import LoadingReviewForm from '../components/LoadingReviewForm'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Link from '@mui/material/Link'
import Breadcrumbs from '@mui/material/Breadcrumbs'

// Material UI icon
import NavigateNextIcon from '@mui/icons-material/NavigateNext'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_REVIEW } from '../utils/url'

const StudentEditReview = () => {

    const { courseId, reviewId } = useParams()
    const navigate = useNavigate()
    const axiosPrivate = useAxiosPrivate()

    const [ rating, setRating ] = useState(null)
    const [ review, setReview ] = useState('')
    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {
        const url = URL_GET_REVIEW
            .replace('{courseId}', courseId)
            .replace('{reviewId}', reviewId)
        const response = await apiPrivate.get(axiosPrivate, url)

        if (response.status === 200) {
            setRating(response.data.rating)
            setReview(response.data.comment)
        } else {
            alert(response.message)
        }
        setLoading(false)
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
                        <Grid item xs={11}>
                            <Breadcrumbs separator={<NavigateNextIcon fontSize="small" />}>
                                <Link
                                    underline='hover' 
                                    color='default' 
                                    sx={{ cursor: 'pointer' }} 
                                    onClick={() => navigate(`/student/course/${courseId}/review`)}
                                >
                                    Reviews
                                </Link>
                                <Typography color='black'>Edit</Typography>
                            </Breadcrumbs>
                        </Grid>
                    </Grid>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={10}>
                        {
                            loading ?
                            <LoadingReviewForm />
                            :
                            <ReviewEdit dataRating={rating} dataReview={review} />
                        }
                        </Grid>
                        <Grid item xs={1}></Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentEditReview