import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import CourseContent from '../components/CourseContent';
import LoadingCircle from '../components/LoadingCircle'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Breadcrumbs from '@mui/material/Breadcrumbs'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import {URL_GET_COURSE} from "../utils/url"

const StudentContent = () => {

    const { courseId } = useParams()

    const axiosPrivate = useAxiosPrivate()

    const [ content, setContent ] = useState([])
    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {
        const url = URL_GET_COURSE.replace('{courseId}', courseId)
        const response = await apiPrivate.get(axiosPrivate, url)

        if (response.status === 200) {
            setContent(response.data.content)
        } else {
            alert('Fail')
        }
        setLoading(false)
    }, [])

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3} md={3}>
                    <StudentMenu active='content' /> 
                </Grid>
                <Grid item xs={9}>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={11}>
                            <Breadcrumbs>
                                <Typography color='black'>Course content</Typography>
                            </Breadcrumbs>
                        </Grid>
                    </Grid>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={10} pt={4}>
                            <CourseContent chapters={content} />
                            <LoadingCircle loading={loading} centerY={true} />
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentContent