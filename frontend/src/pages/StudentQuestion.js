import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import moment from 'moment'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import LoadingCircle from '../components/LoadingCircle'
import QuestionCard from '../components/QuestionCard'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Fab from '@mui/material/Fab'

// Material UI icon
import AddIcon from '@mui/icons-material/Add'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_ALL_POSTS_BY_COURSE } from '../utils/url'

const StudentQuestion = () => {

    const { courseId } = useParams()
    
    const navigate = useNavigate()

    const axiosPrivate = useAxiosPrivate()

    const [ question, setQuestion ] = useState([])

    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {

        const response = await apiPrivate.get(axiosPrivate, URL_GET_ALL_POSTS_BY_COURSE.replace('{courseId}', courseId))

        if (response.status === 200) {
            setQuestion(response.data)
        } else {
            alert('Qeustion fail')
        }
        setLoading(false)

    }, [])

    const handleNavigateCreateQuestion = () => {
        navigate(`/student/course/${courseId}/question-board/create`)
    }

    const handleNavigateQuestionCard = (questionBoardId) => {
        navigate(`/student/course/${courseId}/question-board/${questionBoardId}`)
    }

    return (
        <Container>
            <AppBarSearchHeader />
            <br/>
            <Grid container>
                <Grid item xs={3} md={3}>
                    <StudentMenu active='question board' /> 
                </Grid>
                <Grid item xs={9}>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={9}>
                            <Typography variant='h6'>
                                Question board
                            </Typography>
                        </Grid>
                        <Grid item xs={2}>
                            <Fab
                                size="small" 
                                color="primary"
                                title='Create question'
                                onClick={handleNavigateCreateQuestion}
                                sx={{ position: 'fixed' }}
                            >
                                <AddIcon />
                            </Fab>
                        </Grid>
                    </Grid>
                    <Grid container sx={{ mt: 2 }}>
                        <Grid item xs={2}></Grid>
                        <Grid item xs={10}>
                            {
                                question?.map((item, index) => (
                                    <QuestionCard 
                                        key={index}
                                        topic={item.topic}
                                        datetime={moment(item.createDateTime).format("DD/MM/YYYY | kk:mm:ss")}
                                        commentCount={item.comments.length}
                                        onClickQuestionCard={() => handleNavigateQuestionCard(item.id)}
                                    />
                                ))
                            }
                            <LoadingCircle loading={loading} layoutLeft={60} />
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentQuestion