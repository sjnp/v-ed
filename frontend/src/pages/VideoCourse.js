import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { useSelector } from 'react-redux'
import ReactPlayer from 'react-player'

// component
import AppBarSearchHeader from "../components/AppBarSearchHeader";
import MaterialFileDownload from '../components/MaterialFileDownload'
import Grid from '@mui/material/Grid';

// Material UI component
import Container from '@mui/material/Container'
import IconButton from '@mui/material/IconButton'

// Material UI icon
import ArrowBackIcon from '@mui/icons-material/ArrowBack'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// url
import { URL_COURSE_VIDEO } from '../utils/url'

const VideoCourse = () => {

    const { id } = useParams()

    const navigate = useNavigate()

    const videoUri = useSelector(state => state.videoCourse.value.videoUri)

    const axiosPrivate = useAxiosPrivate()

    const [ videoExampleURI, setVideoExampleURI ] = useState('')

    useEffect(async () => {
        const response = await axiosPrivate.get(URL_COURSE_VIDEO + videoUri)
            .then(res => res)
            .catch(err => err.response)

        if (response.status === 200) {
            setVideoExampleURI(response.data)
        }
    }, [])

    return (
        <Container maxWidth="lg">
            <AppBarSearchHeader />
            <Grid container>
                <Grid item xs={12} sx={{ mt: 4, mb: 1 }}>
                    <IconButton onClick={() => navigate(`/student/course/${id}`)} title='Back to course page'>
                        <ArrowBackIcon  />
                    </IconButton>
                </Grid>
                <Grid item xs={8}>
                    <ReactPlayer url={videoExampleURI} controls={true} style={{ margin: 'auto' }} />
                </Grid>
                <Grid item xs={4}>
                    <MaterialFileDownload />
                </Grid>
            </Grid>
        </Container>
    )
}

export default VideoCourse