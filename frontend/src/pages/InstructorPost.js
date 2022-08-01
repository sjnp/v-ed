import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import InstructorMenu from '../components/InstructorMenu'
import LoadingCircle from '../components/LoadingCircle'
import PostCard from '../components/PostCard'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Breadcrumbs from '@mui/material/Breadcrumbs'

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'
import useReasonReport from '../hooks/useReasonReport'

// url
import { URL_GET_POSTS_COURSE } from '../utils/url'

const InstructorPost = () => {

    const apiPrivate = useApiPrivate()
    const { courseId } = useParams()

    const [ posts, setPosts ] = useState([])
    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {
        const url = URL_GET_POSTS_COURSE.replace('{courseId}', courseId)
        const response = await apiPrivate.get(url)

        if (response.status === 200) {
            setPosts(response.data)
        } else {
            alert('fail')
        }
        setLoading(false)
    }, [])

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
                            <Breadcrumbs>
                                <Typography color='black'>Post</Typography>
                            </Breadcrumbs>
                        </Grid>
                    </Grid>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={10}>
                        {
                            loading ?
                            <LoadingCircle loading={loading} centerY={true} />
                            :
                            null
                        }
                        {
                            posts?.map((post, index) => (
                                post.visible ?
                                <PostCard 
                                    key={index}
                                    postId={post.id}
                                    topic={post.topic}
                                    datetime={post.createDateTime} 
                                    commentCount={post.commentCount}
                                    type='instructor'
                                />
                                :
                                null
                            ))
                        }
                        </Grid>
                        <Grid item xs={1}></Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default InstructorPost