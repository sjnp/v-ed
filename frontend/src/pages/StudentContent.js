import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import BetaCourseContent from '../components/BetaCourseContent';

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_COURSE_BY_ID } from "../utils/url"

const StudentContent = () => {

    const { courseId } = useParams()

    const axiosPrivate = useAxiosPrivate()

    const [ content, setContent ] = useState([])

    useEffect(async () => {

        const url = URL_GET_COURSE_BY_ID + courseId
        const response = await apiPrivate.get(axiosPrivate, url)

        if (response.status === 200) {
            setContent(response.data.content)
        } else {
            alert('Fail')
        }

    }, [])

    return (
        <Container>
            <AppBarSearchHeader />
            <br/>
            <Grid container>
                <Grid item xs={3} md={3}>
                    <StudentMenu active='content' /> 
                </Grid>
                <Grid item xs={9}>

                    <Grid item xs={12}>
                        <Typography variant='h6'>
                            Course content
                        </Typography>
                    </Grid>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={10}>
                            <br/>
                            <BetaCourseContent chapters={content} />
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentContent