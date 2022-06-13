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
    const axiosPrivate = useAxiosPrivate()

    const [ posts, setPosts ] = useState(null)
    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {
        const url = URL_GET_POST
            .replace('{courseId}', courseId)
            .replace('{postId}', postId)
        const response = await apiPrivate.get(axiosPrivate, url)

        if (response.status === 200) {
            setPosts(response.data)
        } else {
            alert('fail')
        }
        setLoading(false)
    }, [])

    const handleCreateCommentSuucess = (newComment) => {
        posts.comments.push(newComment)
        setPosts({ ...posts })
    }

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
                        {
                            posts ? <QuestionTopic data={posts} /> : null
                        }
                        {
                            posts?.comments?.map((comment, index) => (
                                <QuestionComment key={index} data={comment} />
                            ))
                        }
                        {
                            loading ? <LoadingCircle loading={loading} centerY={true} /> : null
                        }
                        </Grid>
                        <Grid item xs={1}>
                            <QuestionWriteComment onCreateCommentSuccess={handleCreateCommentSuucess}/>
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentBoard