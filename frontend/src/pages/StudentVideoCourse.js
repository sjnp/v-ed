import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import ReactPlayer from 'react-player'

// component
import AppBarSearchHeader from "../components/AppBarSearchHeader"
import MaterialFileDownload from '../components/MaterialFileDownload'
import LoadingCircle from '../components/LoadingCircle'

// Material UI component
import Container from '@mui/material/Container'
import IconButton from '@mui/material/IconButton'
import Grid from '@mui/material/Grid';

// Material UI icon
import ArrowBackIcon from '@mui/icons-material/ArrowBack'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate';

// url
import { URL_GET_VIDEO } from '../utils/url'

const VideoCourse = () => {

    const { courseId, chapterIndex, sectionIndex } = useParams()

    const navigate = useNavigate()

    const axiosPrivate = useAxiosPrivate()

    const [ videoUrl, setVideoUrl ] = useState('')

    useEffect(async () => {
        const url = URL_GET_VIDEO
            .replace('{courseId}', courseId)
            .replace('{chapterIndex}', chapterIndex)
            .replace('{sectionIndex}', sectionIndex)

        const response = await apiPrivate.get(axiosPrivate, url)

        if (response.status === 200) {
            console.log('video page', response.data)
            // setVideoUrl(response.data)
        }
    }, [])

    // const [ videoUrl, setVideoUrl ] = useState('')
    // const [ materials, setMaterials ] = useState([])

    // const [ videoLoading, setVideoLoading ] = useState(true)

    // useEffect(async () => {

    //     const url = URL_GET_VIDEO
    //         .replace('{courseId}', courseId)
    //         .replace('{chapterIndex}', chapterIndex)
    //         .replace('{sectionIndex}', sectionIndex)
            
    //     const response = await apiPrivate.get(axiosPrivate, url)
        
    //     if (response.status === 200) {
    //         setVideoUrl(response.data)
    //     } else {
    //         alert('Error api video')
    //     }
    //     setVideoLoading(false)

    // }, [])

    const handleClickArrowBack = () => {
        navigate(`/student/course/${courseId}/content`)
    }

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container>
                <Grid item xs={12} mt={4} mb={1} >
                    <IconButton onClick={handleClickArrowBack} title='Back to course page'>
                        <ArrowBackIcon  />
                    </IconButton>
                </Grid>
                <Grid item xs={8}>
                    <ReactPlayer url={videoUrl} controls={true} style={{ margin: 'auto' }} />
                    {/* <LoadingCircle loading={videoLoading} layoutLeft={35} /> */}
                </Grid>
                <Grid item xs={4}>
                    <MaterialFileDownload />
                </Grid>
            </Grid>
        </Container>
    )
}

export default VideoCourse