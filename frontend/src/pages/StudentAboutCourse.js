import React from 'react'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'

const StudentAboutCourse = () => {



    return (
        <Container>
            <AppBarSearchHeader />
            <br/>
            <Grid container>
                <Grid item xs={3}>
                    <StudentMenu active='about course' /> 
                </Grid>
                <Grid item xs={9}>
                    About course
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentAboutCourse