import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import LoadingCircle from '../components/LoadingCircle'
import ContentPaper from '../components/ContentPaper'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_ABOUT_COURSE } from "../utils/url"

const StudentAboutCourse = () => {

    const { courseId } = useParams()

    const axiosPrivate = useAxiosPrivate()

    const [ overview, setOverview ] = useState('')
    const [ requirement, setRequirement ] = useState('')

    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {

        const response = await apiPrivate.get(axiosPrivate, URL_GET_ABOUT_COURSE.replace('{courseId}', courseId))

        if (response.status === 200) {
            setOverview(response.data.overview)
            setRequirement(response.data.requirement)
        } else {
            alert('GET about course fail')
        }
        setLoading(false)

    }, [])

    return (
        <Container>
            <AppBarSearchHeader />
            <br/>
            <Grid container>
                <Grid item xs={3}>
                    <StudentMenu active='about course' /> 
                </Grid>
                <Grid item xs={9}>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={11}>
                            <Typography variant='h6'>About course</Typography>
                        </Grid>
                    </Grid>
                    <Grid container>
                        <Grid item xs={2}></Grid>
                        <Grid item xs={8} sx={{ pt: 4 }}>
                            { loading ? null : <ContentPaper label='Overview' content={overview} /> }
                            { loading ? null : <ContentPaper label='Requirement' content={requirement} /> }    
                            <LoadingCircle loading={loading} layoutLeft={60} />
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentAboutCourse