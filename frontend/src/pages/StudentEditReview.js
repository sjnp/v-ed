import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import WriteReview from '../components/WriteReview'
import LoadingCircle from '../components/LoadingCircle'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_REVIEW } from '../utils/url'

const StudentEditReview = () => {

    const { courseId, reviewId } = useParams()

    const axiosPrivate = useAxiosPrivate()

    const [ rating, setRating ] = useState(null)
    const [ review, setReview ] = useState('')

    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {

        const response = await apiPrivate.get(axiosPrivate,
          URL_GET_REVIEW
            .replace('{courseId}', courseId)
            .replace('{reviewId}', reviewId))

        if (response.status === 200) {
            setRating(response.data.rating)
            setReview(response.data.comment)
        } else {
            alert('call api get review by id ERROR!!!')
        }
        setLoading(false)
    
    }, [])

    return (
        <Container>
            <AppBarSearchHeader />
            <br/>
            <Grid container>
                <Grid item xs={3} md={3}>
                    <StudentMenu active='review' /> 
                </Grid>
                <Grid item xs={9}>
                    <Grid container>
                        <Grid item xs={2}></Grid>
                        <Grid item xs={10}>
                            <Typography variant='h6'>Edit review</Typography>
                        </Grid>
                    </Grid>
                    <Grid container sx={{ mt: 2 }}>
                        <Grid item xs={2}></Grid>
                        <Grid item xs={8} sx={{ pt: 3 }}>
                        {
                            loading === false ?
                                <WriteReview initRating={rating} initReview={review} type='update' />
                                :
                                null
                        }    
                            <LoadingCircle loading={loading} layoutLeft={60} />
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentEditReview