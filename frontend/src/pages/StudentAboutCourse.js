import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import LoadingContentPaper from '../components/LoadingContentPaper'
import ContentPaper from '../components/ContentPaper'

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
import { URL_GET_ABOUT_COURSE } from "../utils/url"

const StudentAboutCourse = () => {

    const { courseId } = useParams()
    const axiosPrivate = useAxiosPrivate()

    const [ overview, setOverview ] = useState(null)
    const [ requirement, setRequirement ] = useState(null)

    useEffect(async () => {
        const url = URL_GET_ABOUT_COURSE.replace('{courseId}', courseId)
        const response = await apiPrivate.get(axiosPrivate, url)

        if (response.status === 200) {
            setOverview(response.data.overview)
            setRequirement(response.data.requirement)
        } else {
            alert('Fail')
        }
    }, [])

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <StudentMenu active='about course' />
                </Grid>
                <Grid item xs={9}>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={11}>
                            <Breadcrumbs>
                                <Typography color='black'>About course</Typography>
                            </Breadcrumbs>
                        </Grid>
                    </Grid>
                    <Grid container pt={3}>
                        <Grid item xs={2}></Grid>
                        <Grid item xs={8}>
                        {
                            overview === null ?
                            <LoadingContentPaper />
                            :
                            <ContentPaper label='Overview' content={overview} />
                        }
                        {
                            requirement === null ?
                            <LoadingContentPaper />
                            :
                            <ContentPaper label='Requirement' content={requirement} />
                        }
                        </Grid>
                        <Grid item xs={2}></Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentAboutCourse