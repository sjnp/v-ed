import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import ReactPlayer from 'react-player'
import { useSelector } from 'react-redux';

// component
import AppBarSearchHeader from "../components/AppBarSearchHeader";
import MaterialFileDownload from '../components/MaterialFileDownload'
import Grid from '@mui/material/Grid';

// Material UI component
import Container from '@mui/material/Container'
import Typography from '@mui/material/Typography'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate';

// url
import { URL_COURSE_VIDEO } from '../utils/url';

const VideoCourse = () => {

    const { id } = useParams()

    const videoUri = useSelector(state => state.videoCourse.value.videoUri)

    const axiosPrivate = useAxiosPrivate()

    const [videoExampleURI, setVideoExampleURI] = useState('')

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
            <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
                Student Video
            </Typography>
            <Grid container rowSpacing={1} sx={{ marginTop: 1 }}>
                <Grid item xs={8}>
                    <ReactPlayer
                        url={videoExampleURI}
                        controls={true}
                        style={{ margin: 'auto' }}
                    />
                </Grid>
                <Grid item xs={4}>
                    <MaterialFileDownload />
                </Grid>
            </Grid>
        </Container>
    )
}

export default VideoCourse