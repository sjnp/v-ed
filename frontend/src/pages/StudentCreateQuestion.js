import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { useNavigate } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import QuestionCreate from '../components/QuestionCreate'

import CourseContent from '../components/CourseContent';

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Fab from '@mui/material/Fab'

// Material UI icon
import AddIcon from '@mui/icons-material/Add'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_COURSE_BY_ID } from "../utils/url"

const StudentCreateQuestion = () => {



    return (
        <Container>
            <AppBarSearchHeader />
            <br/>
            <Grid container>
                <Grid item xs={3} md={3}>
                    <StudentMenu active='question board' /> 
                </Grid>
                <Grid item xs={9}>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={11}>
                            <Typography variant='h6'>
                                Create question board
                            </Typography>
                        </Grid>
                    </Grid>
                    <Grid container sx={{ pt: 3 }}>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={9}>
                            <QuestionCreate />
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentCreateQuestion