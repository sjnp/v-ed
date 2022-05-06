import React from 'react'
import { useParams  } from 'react-router-dom'
import { useSelector } from 'react-redux'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import Toolbar from '@mui/material/Toolbar'

const StudentAssignmentAnswer = () => {

    const { chapterNo } = useParams()

    const assignments = useSelector(state => state.assignmentStudent.value.assignments)

    return (
        <Container>
            <AppBarSearchHeader />
            <br/>
            <Grid container>
                <Grid item xs={3} md={3}>
                    <StudentMenu active='assignment' /> 
                </Grid>
                <Grid item xs={9}>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={11}>
                            <Typography variant='h6'>
                                Assignment chapter {chapterNo}
                            </Typography>
                        </Grid>
                    </Grid>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={10} sx={{ pt: 1 }}>
                        {
                            assignments?.map((assignment, index) => (
                                <Box key={index}>
                                    <Toolbar />
                                    {assignment.detail}
                                    <hr/>
                                </Box>
                            ))
                        }
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentAssignmentAnswer