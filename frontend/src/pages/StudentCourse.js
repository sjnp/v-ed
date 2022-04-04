import React from 'react'

// component
import AppBarHeader from '../components/AppBarHeader'
import StudentSidebar from '../components/StudentSidebar'
import SectionCourse from '../components/SectionCourse'

// Material UI
import Container from '@mui/material/Container'
import Typography from '@mui/material/Typography'
import Grid from '@mui/material/Grid'

const StudentCourse = () => {

    return (
        <Container maxWidth="lg" sx={{ background: '#f5f5f5' }}>
            <AppBarHeader />
            <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
                Student Course
            </Typography>
            <Grid container>
                <Grid item xs={3}>
                    <StudentSidebar />
                </Grid>
                <Grid item xs={9}>
                    <SectionCourse />
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentCourse