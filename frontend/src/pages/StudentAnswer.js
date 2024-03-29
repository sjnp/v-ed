import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import LoadingCircle from '../components/LoadingCircle'
import Answer from '../components/Answer'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Breadcrumbs from '@mui/material/Breadcrumbs'
import Link from '@mui/material/Link'

// Material UI icon
import NavigateNextIcon from '@mui/icons-material/NavigateNext'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_ASSIGNMENT_ANSWER } from '../utils/url'

const StudentAnswer = () => {

    const { courseId, chapterIndex } = useParams()
    const navigate = useNavigate()

    const axiosPrivate = useAxiosPrivate()

    const [ assignmentAnswers, setAssignmentAnswers ] = useState([])
    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {
        const url = URL_GET_ASSIGNMENT_ANSWER
            .replace('{courseId}', courseId)
            .replace('{chapterIndex}', chapterIndex)
        const response = await apiPrivate.get(axiosPrivate, url)

        if (response.status === 200) {
            setAssignmentAnswers(response.data)
        }
        setLoading(false)
    }, [])

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <StudentMenu active='assignment' /> 
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
                                    onClick={() => navigate(`/student/course/${courseId}/assignment`)}
                                >
                                    Assignment
                                </Link>
                                <Typography color='text.primary'>Chapter {Number(chapterIndex) + 1}</Typography>
                            </Breadcrumbs>
                        </Grid>
                    </Grid>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={10} pt={4}>
                            {
                                assignmentAnswers?.map((assignmentAnswer, index) => {
                                    return (
                                        <Answer
                                            key={index}
                                            assignment={assignmentAnswer.assignment}
                                            answer={assignmentAnswer.answer}
                                            noIndex={index}
                                            commentInstructor={assignmentAnswer.answer?.commentInstructor || null}
                                        />
                                    )
                                })
                            }
                            <LoadingCircle loading={loading} centerY={true} />
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentAnswer