import React from 'react'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import WriteReview from '../components/WriteReview'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'

const StudentCreateReview = () => {

    return (
        <Container>
            <AppBarSearchHeader />
            <br/>
            <Grid container>
                <Grid item xs={3} md={3}>
                    <StudentMenu active='review' /> 
                </Grid>
                <Grid item xs={9}>
                    <Grid container>
                        <Grid item xs={2}></Grid>
                        <Grid item xs={10}>
                            <Typography variant='h6'>Create review</Typography>
                        </Grid>
                    </Grid>
                    <Grid container sx={{ mt: 2 }}>
                        <Grid item xs={2}></Grid>
                        <Grid item xs={8} sx={{ pt: 3 }}>
                            <WriteReview type='create' />
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentCreateReview