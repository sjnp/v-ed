import React from 'react'
import { useParams } from 'react-router-dom'
import { useNavigate } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import QuestionCreate from '../components/QuestionCreate'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Breadcrumbs from '@mui/material/Breadcrumbs'
import Link from '@mui/material/Link'

// Material UI icon
import NavigateNextIcon from '@mui/icons-material/NavigateNext'

const StudentCreateQuestion = () => {

    const { courseId } = useParams()
    const navigate = useNavigate()

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <StudentMenu active='question board' />
                </Grid>
                <Grid item xs={9}>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={11}>
                            <Breadcrumbs separator={<NavigateNextIcon fontSize="small" />}>
                                <Link
                                    underline='hover' 
                                    color='default' 
                                    sx={{ cursor: 'pointer' }} 
                                    onClick={() => navigate(`/student/course/${courseId}/question-board`)}
                                >
                                    Question
                                </Link>
                                <Typography color='text.primary'>Create</Typography>
                            </Breadcrumbs>
                        </Grid>
                    </Grid>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={10} pt={3}>
                            <QuestionCreate />
                        </Grid>
                        <Grid item xs={1}></Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentCreateQuestion