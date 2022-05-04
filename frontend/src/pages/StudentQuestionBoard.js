import React from 'react'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'

const StudentQuestionBoard = () => {


    return (
        <Container>
            <AppBarSearchHeader />
            <br/>
            <Grid container>
                <Grid item xs={3}>
                    <StudentMenu active='question board' /> 
                </Grid>
                <Grid item xs={9}>
                    Question board
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentQuestionBoard