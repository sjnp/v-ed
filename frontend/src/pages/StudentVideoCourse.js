import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import ReactPlayer from 'react-player'

// component
import AppBarSearchHeader from "../components/AppBarSearchHeader"
import MaterialFileDownload from '../components/MaterialFileDownload'

// Material UI component
import Container from '@mui/material/Container'
import IconButton from '@mui/material/IconButton'
import Grid from '@mui/material/Grid'
import Skeleton from '@mui/material/Skeleton'
import Chip from '@mui/material/Chip'

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
    const [ pictureUrl, setPictureUrl ] = useState('')
    const [ chapterName, setChapterName ] = useState('')
    const [ sectionName, setSectionName ] = useState('')
    
    const [ loadingVideo, setLoadingVideo ] = useState(true)

    useEffect(async () => {
        const url = URL_GET_VIDEO
            .replace('{courseId}', courseId)
            .replace('{chapterIndex}', chapterIndex)
            .replace('{sectionIndex}', sectionIndex)

        const response = await apiPrivate.get(axiosPrivate, url)
        
        if (response.status === 200) {
            setVideoUrl(response.data.videoUrl)
            setPictureUrl(response.data.pictureUrl)
            setChapterName(response.data.chapterName)
            setSectionName(response.data.sectionName)
        }
        setLoadingVideo(false)
    }, [])

    const handleClickArrowBack = () => {
        navigate(`/student/course/${courseId}/content`)
    }

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container>
                <Grid item xs={1} mt={4} mb={1}>
                    <IconButton onClick={handleClickArrowBack} title='Back to course page'>
                        <ArrowBackIcon  />
                    </IconButton>
                </Grid>
                <Grid item xs={11} mt={4} mb={1}>
                    <Chip
                        variant='outlined' 
                        label={`Chapter ${Number(chapterIndex) + 1}`} 
                        title={`Chapter ${Number(chapterIndex) + 1} : ${chapterName}`}
                    />
                    <Chip
                        variant='outlined' 
                        label={`Section ${Number(sectionIndex) + 1}`}
                        title={`Section ${Number(sectionIndex) + 1} : ${sectionName}`}
                        sx={{ ml: 1 }}
                    />
                </Grid>
                <Grid item xs={1}></Grid>
                <Grid item xs={7} height='70vh'>
                {
                    loadingVideo ?
                    <Skeleton variant='rectangular' width='95%' height='80%'  />
                    :
                    <ReactPlayer url={videoUrl} light={pictureUrl} playing controls={true} />
                }
                </Grid>
                <Grid item xs={4}>
                    <MaterialFileDownload />
                </Grid>
            </Grid>
        </Container>
    )
}

export default VideoCourse