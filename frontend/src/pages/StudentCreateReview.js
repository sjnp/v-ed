import React from 'react'
import { useParams, useNavigate } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import WriteReview from '../components/WriteReview'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Link from '@mui/material/Link'
import Breadcrumbs from '@mui/material/Breadcrumbs'

// Material UI icon
import NavigateNextIcon from '@mui/icons-material/NavigateNext'


const StudentCreateReview = () => {

    const { courseId } = useParams()
    const navigate = useNavigate()

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <StudentMenu active='review' />
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
                                    onClick={() => navigate(`/student/course/${courseId}/review`)}
                                >
                                    Reviews
                                </Link>
                                <Typography color='black'>Create</Typography>
                            </Breadcrumbs>
                        </Grid>
                    </Grid>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={10}>
                            <WriteReview type='create' />
                        </Grid>
                        <Grid item xs={1}></Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
        // <Container>
        //     <AppBarSearchHeader />
        //     <br/>
        //     <Grid container>
        //         <Grid item xs={3} md={3}>
        //             <StudentMenu active='review' /> 
        //         </Grid>
        //         {/* <Grid item xs={9}>
        //             <Grid container>
        //                 <Grid item xs={2}></Grid>
        //                 <Grid item xs={10}>
        //                     <Typography variant='h6'>Create review</Typography>
        //                 </Grid>
        //             </Grid>
        //             <Grid container sx={{ mt: 2 }}>
        //                 <Grid item xs={2}></Grid>
        //                 <Grid item xs={8} sx={{ pt: 3 }}>
        //                     <WriteReview type='create' />
        //                 </Grid>
        //             </Grid>
        //         </Grid> */}
        //     </Grid>
        // </Container>
    )
}

export default StudentCreateReview