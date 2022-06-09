import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import LoadingCircle from '../components/LoadingCircle'
import QuestionTopic from '../components/QuestionTopic'
import QuestionWriteComment from '../components/QuestionWriteComment'
import QuestionComment from '../components/QuestionComment'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Toolbar from '@mui/material/Toolbar'
import Breadcrumbs from '@mui/material/Breadcrumbs'
import Link from '@mui/material/Link'

// Material UI icon
import NavigateNextIcon from '@mui/icons-material/NavigateNext'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_POST } from '../utils/url'

const StudentBoard = () => {

    const { courseId, postId } = useParams()
    const navigate = useNavigate()


    // const axiosPrivate = useAxiosPrivate()

    // const [ questionBoard, setQuestionBoard ] = useState(null)

    // const [ loading, setLoading ] = useState(true)

    // useEffect(async () => {

    //     const url = URL_GET_POST
    //       .replace('{courseId}', courseId)
    //       .replace('{postId}', questionBoardId)
    //     const response = await apiPrivate.get(axiosPrivate, url)

    //     if (response.status === 200) {
    //         setQuestionBoard(response.data)
    //     } else {
    //         alert('fail')
    //     }
    //     setLoading(false)

    // }, [])

    // const handleCreateCommentSuccess = (data) => {
    //     const newComment = questionBoard.comments
    //     newComment.push(data)
    //     setQuestionBoard({
    //         ...questionBoard, 
    //         comments: newComment
    //     })
    // }

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
                                <Typography color='text.primary'>Board</Typography>
                            </Breadcrumbs>
                        </Grid>
                    </Grid>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={10} pt={3}>
                            {/* <QuestionCreate /> */}
                        </Grid>
                        <Grid item xs={1}></Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>

        // <Container>
        //     <AppBarSearchHeader />
        //     <br />
        //     <Grid container>
        //         <Grid item xs={3} md={3}>
        //             <StudentMenu active='question board' />
        //         </Grid>
        //         {/* <Grid item xs={9}>
        //             <Grid container>
        //                 <Grid item xs={1}></Grid>
        //                 <Grid item xs={11}>
        //                     <Typography variant='h6'>
        //                         Question board
        //                     </Typography>
        //                 </Grid>
        //             </Grid>
        //             <Grid container sx={{ pt: 3 }}>
        //                 <Grid item xs={1}></Grid>
        //                 <Grid item xs={9}>
        //                     {
        //                         questionBoard ? <QuestionTopic data={questionBoard} /> : null
        //                     }
        //                     {
        //                         questionBoard?.comments.map((comment, index) => (
        //                             <QuestionComment key={index} data={comment} />
        //                         ))
        //                     }
        //                     <Toolbar />
        //                     <LoadingCircle loading={loading} layoutLeft={60} />
        //                 </Grid>
        //                 <Grid item xs={1}>
        //                     <QuestionWriteComment onCreateCommentSuccess={handleCreateCommentSuccess} />
        //                 </Grid>
        //             </Grid>
        //         </Grid> */}
        //     </Grid>
        // </Container>
    )
}

export default StudentBoard