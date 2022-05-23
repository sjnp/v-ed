import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { useDispatch } from 'react-redux'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import AssignmentChapter from '../components/AssignmentChapter'
import LoadingCircle from '../components/LoadingCircle'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// feature slice
import { setAssignment } from '../features/assignmentSlice'

// url
import { URL_GET_COURSE_BY_ID } from '../utils/url'

const StudentAssignment = () => {

    // const { courseId } = useParams()

    // const axiosPrivate = useAxiosPrivate()

    // const dispatch = useDispatch()
    // const navigate = useNavigate()

    // const [ assignments, setAssignments ] = useState([])

    // const [ loading, setLoading ] = useState(true)

    // useEffect(async () => {
    //     const url = URL_GET_COURSE_BY_ID + courseId
    //     const response = await apiPrivate.get(axiosPrivate, url)

    //     if (response.status === 200) {
    //         const assignmentList = response.data.content.map(item => item.assignments)
    //         setAssignments(assignmentList)
    //     } else {
    //         alert('Fail')
    //     }
    //     setLoading(false)
    // }, [])

    // const handleClickAssignmentChapter = (assignment, index) => {
    //     const chapterNo = index + 1

    //     dispatch( setAssignment({
    //         chapterNo: chapterNo,
    //         assignments: assignment
    //     }))

    //     navigate(`/student/course/${courseId}/assignment/chapter/${chapterNo}`)
    // }

    return (
        <Container>
            <AppBarSearchHeader />
            <br/>
            <Grid container>
                <Grid item xs={3} md={3}>
                    <StudentMenu active='assignment' /> 
                </Grid>
                {/* <Grid item xs={9}>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={11}>
                            <Typography variant='h6'>
                                Assignment
                            </Typography>
                        </Grid>
                    </Grid>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={10} sx={{ pt: 1 }}>
                        {
                            assignments?.map((assignment, index) => (
                                <AssignmentChapter
                                    key={index}
                                    chapterNo={index + 1}
                                    onClick={() => handleClickAssignmentChapter(assignment, index)}
                                />
                            ))
                        }
                        <LoadingCircle loading={loading} layoutLeft={60} />
                        </Grid>
                    </Grid>
                </Grid> */}
            </Grid>
        </Container>
    )
}

export default StudentAssignment