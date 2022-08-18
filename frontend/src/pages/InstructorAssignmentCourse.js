import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import InstructorMenu from '../components/InstructorMenu'
import AssignmentCourseInstructor from '../components/AssignmentCourseInstructor'
import LoadingCircle from '../components/LoadingCircle'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Breadcrumbs from '@mui/material/Breadcrumbs'

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'

// url
import { URL_GET_ASSIGNMENTS_COURSE_INSTRUCTOR } from '../utils/url'

const InstructorAssignmentCourse = () => {

    const { courseId } = useParams()
    const apiPrivate = useApiPrivate()

    const [ assignments, setAssignments ] = useState([])
    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {
        const url = URL_GET_ASSIGNMENTS_COURSE_INSTRUCTOR.replace('{courseId}', courseId)
        const response = await apiPrivate.get(url)
        if (response.status === 200) {
            setAssignments(response.data)
        } else {
            alert('Fail')
        }
        setLoading(false)
    }, [])
    
    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <InstructorMenu active='assignment' />
                </Grid>
                <Grid item xs={9}>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={11}>
                            <Breadcrumbs>
                                <Typography color='black'>Assignment</Typography>
                            </Breadcrumbs>
                        </Grid>
                    </Grid>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={10} pt={2}>
                            {
                                assignments.map((assignment, index) => (
                                    <AssignmentCourseInstructor
                                        key={index}
                                        chapterIndex={assignment.chapterIndex}
                                        chapterNo={assignment.chapterNo}
                                        countNoti={assignment.countNoti}
                                    />
                                ))
                            }
                            <LoadingCircle loading={loading} centerY={true} />
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default InstructorAssignmentCourse