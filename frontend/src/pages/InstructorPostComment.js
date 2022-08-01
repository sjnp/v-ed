import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import InstructorMenu from '../components/InstructorMenu'
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
import useReasonReport from '../hooks/useReasonReport'

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'

// url
import { URL_GET_POSTS_ID } from '../utils/url'

const InstructorPostComment = () => {

    const { courseId, postId } = useParams()
    const navigate = useNavigate()
    const apiPrivate = useApiPrivate()
    const createReasonReportRedux = useReasonReport()

    const [ posts, setPosts ] = useState(null)
    const [ topicName, setTopicName ] = useState('')
    const [ loading, setLoading ] = useState(true)
    
    useEffect(async () => {
        const url = URL_GET_POSTS_ID
            .replace('{courseId}', courseId)
            .replace('{postId}', postId)
        const response = await apiPrivate.get(url)

        if (response.status === 200) {
            setPosts(response.data)
            setTopicName(response.data.topic)
        } else {
            alert('fail')
        }
        setLoading(false)
        createReasonReportRedux()
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
                    <InstructorMenu active='post' />
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
                                    onClick={() => navigate(`/instructor/course/${courseId}/post`)}
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
                                commentBy='instructor'
                            />
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default InstructorPostComment