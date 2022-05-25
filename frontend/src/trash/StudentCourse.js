import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom';
import { useDispatch } from 'react-redux';

// component
import AppBarSearchHeader from "../components/AppBarSearchHeader";
import StudentSidebar from '../components/StudentSidebar'
// import CourseContent from '../components/CourseContent'
// import CourseAbout from '../components/CourseAbout'
// import CourseAssignment from '../components/CourseAssignment'
// import CourseQuestion from '../components/CourseQuestion'
// import CourseReview from '../components/CourseReview'
// import CourseInstructorDetail from '../components/CourseInstructorDetail'

// Material UI component
import Container from '@mui/material/Container'
import Typography from '@mui/material/Typography'
import Grid from '@mui/material/Grid'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// url
import { URL_GET_COURSE_BY_ID } from "../utils/url";

// fueature slice
import { setCourse } from '../features/studentCourseSlice';
import { setAssignment } from '../features/studentAssignmentSlice';

const StudentCourse = () => {

    const { courseId } = useParams()

    const axiosPrivate = useAxiosPrivate()

    const dispatch = useDispatch()

    useEffect(async () => {
        const response = await axiosPrivate.get(URL_GET_COURSE_BY_ID + courseId)
            .then(res => res)
            .catch(err => err.response)
        if (response.status === 200) {
            dispatch( setCourse({ 
                courseId: response.data.courseId, 
                content: response.data.content
            }))

            dispatch( setAssignment({
                assignment: response.data.content.map(element => element.assignments)
            }))
        }
        
    }, [])

    // const [ mainElement, setMainElement ] = useState(<CourseContent />)
    const [ mainElement, setMainElement ] = useState(null)

    const handleChangeMain = (name) => {
        const element = getMainElement(name)
        setMainElement(element)
    } 

    const getMainElement = (name) => {
        switch (name) {
            // case 'Content': return <CourseContent />
            // case 'Assignment': return <CourseAssignment />
            // case 'Question board': return <CourseQuestion />
            // case 'Review': return <CourseReview />
            // case 'Instructor': return <CourseInstructorDetail />
            // case 'About course': return <CourseAbout />
            // default: return <h1>ERROR!!!</h1>
        }
    }

    return (
        <Container maxWidth="lg" sx={{ background: '#f5f5f5' }}>
            <AppBarSearchHeader />
            <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
                Student Course
            </Typography>
            <Grid container>
                <Grid item xs={3}>
                    <StudentSidebar onClickSidebar={handleChangeMain} />
                </Grid>
                <Grid item xs={9}>
                    {mainElement}
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentCourse