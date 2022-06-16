import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import LoadingCircle from '../components/LoadingCircle'
import PostCard from '../components/PostCard'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Fab from '@mui/material/Fab'
import Breadcrumbs from '@mui/material/Breadcrumbs'

// Material UI icon
import AddIcon from '@mui/icons-material/Add'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_ALL_POSTS_BY_COURSE } from '../utils/url'

const StudentPost = () => {

    const { courseId } = useParams()
    const axiosPrivate = useAxiosPrivate()
    const navigate = useNavigate()

    const [ posts, setPosts ] = useState([])
    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {
        const url = URL_GET_ALL_POSTS_BY_COURSE.replace('{courseId}', courseId)
        const response = await apiPrivate.get(axiosPrivate, url)

        if (response.status === 200) {
            setPosts(response.data)
        } else {
            alert('fail')
        }
        setLoading(false)
    }, [])

    const handleClickCreatePost = () => {
        navigate(`/student/course/${courseId}/post/create`)
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
                        <Grid item xs={9}>
                            <Breadcrumbs>
                                <Typography color='black'>Post</Typography>
                            </Breadcrumbs>
                        </Grid>
                        <Grid item xs={2}>
                            <Fab size='small' color='primary' onClick={handleClickCreatePost} sx={{ position: 'fixed' }}>
                                <AddIcon titleAccess='Create post' />
                            </Fab>
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

export default StudentPost