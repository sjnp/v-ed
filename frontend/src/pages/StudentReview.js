import React from 'react'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'

const StudentReview = () => {



    return (
        <Container>
            <AppBarSearchHeader />
            <br/>
            <Grid container>
                <Grid item xs={3}>
                    <StudentMenu active='review' /> 
                </Grid>
                <Grid item xs={9}>
                    Review
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentReview