import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { useNavigate } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import BetaCourseContent from '../components/BetaCourseContent';
import LoadingCircle from '../components/LoadingCircle'


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
import { URL_GET_COURSE_BY_ID } from "../utils/url"
import { URL_GET_QUESTION_BOARD_BY_ID } from '../utils/url'
import QuestionTopic from '../components/QuestionTopic'

const StudentBoard = () => {

    const { questionBoardId } = useParams()

    const axiosPrivate = useAxiosPrivate()

    const [ loading, setLoading ] = useState(true)

    const [ questionBoard, setQuestionBoard ] = useState(null)


    useEffect(async () => {

        const url = URL_GET_QUESTION_BOARD_BY_ID + questionBoardId
        const response = await apiPrivate.get(axiosPrivate, url)

        if (response.status === 200) {
            setQuestionBoard(response.data)
        } else {
            alert('fail')
        }
        setLoading(false)

    }, [])

    return (
        <Container>
            <AppBarSearchHeader />
            <br />
            <Grid container>
                <Grid item xs={3} md={3}>
                    <StudentMenu active='question board' />
                </Grid>
                <Grid item xs={9}>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={11}>
                            <Typography variant='h6'>
                                Question board
                            </Typography>
                        </Grid>
                    </Grid>
                    <Grid container sx={{ pt: 3 }}>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={10}>
                        {
                            questionBoard ? <QuestionTopic data={questionBoard} /> : null
                        }
                            
                            <LoadingCircle loading={loading} layoutLeft={60} />
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentBoard