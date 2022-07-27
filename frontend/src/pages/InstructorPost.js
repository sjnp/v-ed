import React from 'react'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import InstructorMenu from '../components/InstructorMenu'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Breadcrumbs from '@mui/material/Breadcrumbs'

const InstructorPost = () => {

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <InstructorMenu active='post' />
                </Grid>
                <Grid item xs={9}>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={11}>
                            <Breadcrumbs>
                                <Typography color='black'>Post</Typography>
                            </Breadcrumbs>
                        </Grid>
                    </Grid>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={10}>
                        {/* TODO */}
                        </Grid>
                        <Grid item xs={1}></Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default InstructorPost