import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import AssignmentChapter from '../components/AssignmentChapter'
import LoadingCircle from '../components/LoadingCircle'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Breadcrumbs from '@mui/material/Breadcrumbs'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_COURSE } from '../utils/url'

const StudentAssignment = () => {

    const { courseId } = useParams()

    const axiosPrivate = useAxiosPrivate()

    const [ assignments, setAssignments ] = useState([])
    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {
        const url = URL_GET_COURSE.replace('{courseId}', courseId)
        const response = await apiPrivate.get(axiosPrivate, url)

        if (response.status === 200) {
            const result = response.data.content.map((element => element.assignments))
            setAssignments(result)
            
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
                            <Breadcrumbs>
                                <Typography>Assignment</Typography>
                            </Breadcrumbs>
                        </Grid>
                    </Grid>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={10} pt={2}>
                            {
                                assignments?.map((assignment, index) => (
                                    <AssignmentChapter 
                                        key={index} 
                                        chapterIndex={index} 
                                        assignmentCount={assignment.length}
                                    />
                                ))
                            }    
                            <LoadingCircle loading={loading} centerY={true} />
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>


        // <Container>
        //     <AppBarSearchHeader />
        //     <br/>
        //     <Grid container>
        //         <Grid item xs={3} md={3}>
        //             <StudentMenu active='assignment' /> 
        //         </Grid>
        //         <Grid item xs={9}>
        //             <Grid container>
        //                 <Grid item xs={1}></Grid>
        //                 <Grid item xs={11}>
        //                     <Typography variant='h6'>
        //                         Assignment
        //                     </Typography>
        //                 </Grid>
        //             </Grid>
        //             <Grid container>
        //                 <Grid item xs={1}></Grid>
        //                 <Grid item xs={10} sx={{ pt: 1 }}>
        //                 {
        //                     assignments?.map((assignment, index) => (
        //                         <AssignmentChapter
        //                             key={index}
        //                             chapterNo={index + 1}
        //                             onClick={() => handleClickAssignmentChapter(assignment, index)}
        //                         />
        //                     ))
        //                 }
        //                 <LoadingCircle loading={loading} layoutLeft={60} />
        //                 </Grid>
        //             </Grid>
        //         </Grid>
        //     </Grid>
        // </Container>
    )
}

export default StudentAssignment