import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import LoadingCircle from '../components/LoadingCircle'
import PostTopic from '../components/PostTopic'
import PostWriteComment from '../components/PostWriteComment'
import PostComment from '../components/PostComment'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Breadcrumbs from '@mui/material/Breadcrumbs'
import Link from '@mui/material/Link'
import Box from '@mui/material/Box'

// Material UI icon
import NavigateNextIcon from '@mui/icons-material/NavigateNext'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'
import useReasonReport from '../hooks/useReasonReport'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_POST } from '../utils/url'

const StudentPostComment = () => {

    const { courseId, postId } = useParams()
    const axiosPrivate = useAxiosPrivate()
    const navigate = useNavigate()
    const createReasonReportRedux = useReasonReport()

    const [ posts, setPosts ] = useState(null)
    const [ topicName, setTopicName ] = useState('')
    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {
        const url = URL_GET_POST
            .replace('{courseId}', courseId)
            .replace('{postId}', postId)
        const response = await apiPrivate.get(axiosPrivate, url)

        if (response.status === 200) {
            setPosts(response.data)
            setTopicName(response.data.topic)
        } else {
            alert('fail')
        }
        setLoading(false)
        createReasonReportRedux()
    }, [])

    const handleCreateCommentSuucess = (newComments) => {
        setPosts({
            ...posts,
            comments: newComments
        })
    }

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <StudentMenu active='post' />
                </Grid>
                <Grid item xs={9}>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={11}>
                            <Breadcrumbs separator={<NavigateNextIcon fontSize="small" />} >
                                <Link
                                    underline='hover' 
                                    color='default' 
                                    sx={{ cursor: 'pointer' }} 
                                    onClick={() => navigate(`/student/course/${courseId}/post`)}
                                >
                                    Post
                                </Link>
                                <Box title={topicName}>
                                    <Typography noWrap width='300px' color='text.primary'>{topicName}</Typography>
                                </Box>
                            </Breadcrumbs>
                        </Grid>
                    </Grid>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={10} pt={3}>
                        {
                            posts ? <PostTopic data={posts} /> : null
                        }
                        {
                            posts?.comments?.map((comment, index) => (
                                <PostComment key={index} data={comment} />
                            ))
                        }
                        {
                            loading ? <LoadingCircle loading={loading} centerY={true} /> : null
                        }
                        </Grid>
                        <Grid item xs={1}>
                            <PostWriteComment
                                onCreateCommentSuccess={handleCreateCommentSuucess}
                                commentBy='student'    
                            />
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentPostComment