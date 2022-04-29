import React, { useState, useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'

import moment from 'moment'

// feature slice
import { setQuestionBoard } from '../features/questionBoardSlice'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// api
import apiPrivate from '../api/apiPrivate'

// component
import QuestionBoard from './QuestionBoard'
import QuestionCreate from './QuestionCreate'
import QuestionCard from './QuestionCard'

// Material UI component
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import Fab from '@mui/material/Fab'
import Grid from '@mui/material/Grid'
import IconButton from '@mui/material/IconButton'

// Material UI icon
import AddIcon from '@mui/icons-material/Add'
import ArrowBackIcon from '@mui/icons-material/ArrowBack'

// url
import { URL_GET_QUESTION_TOPIC_ALL } from '../utils/url'

const CourseQuestion = () => {

    const axiosPrivate = useAxiosPrivate()

    const dispatch = useDispatch()

    const [ question, setQuestion ] = useState([])

    const [ quesetionBoardElement, setQuestionBoardElement ] = useState([])

    const [ questionLabel, setQuestionLabel ] = useState('All question board')
    const [ hideAddButton, setHideAddButton ] = useState(false)
    const [ hideArrowBackIcon, setHideArrowBackIcon ] = useState(true)

    const handleClickQuestionCard = (item) => {
        dispatch( setQuestionBoard({
            questionId: item.id,
            topic: item.topic,
            detail: item.detail,
            datetime: item.createdDateTime
        }))
        setQuestionBoardElement(<QuestionBoard />)
        setHideAddButton(true)
        setHideArrowBackIcon(false)
        setQuestionLabel('Question board')
    }

    const getListQuestionBoard = (data) => {

        return data?.map((item, index) => (
            <QuestionCard 
                key={index}
                topic={item.topic}
                datetime={moment(item.createdDateTime).format("DD/MM/YYYY | kk:mm:ss")}
                comment={0}
                onClickQuestionCard={() => handleClickQuestionCard(item)}
            />
        ))
    }

    useEffect(async () => {
        const response = await apiPrivate.get(axiosPrivate, URL_GET_QUESTION_TOPIC_ALL)
        if (response.status === 200) {
            setQuestion(response.data)
            setQuestionBoardElement(getListQuestionBoard(response.data))
        }
    }, [])

    const handleClickCreate = () => {
        setQuestionBoardElement(<QuestionCreate onCreateSuccess={handleCreateSuccess} />)
        setHideAddButton(true)
        setHideArrowBackIcon(false)
        setQuestionLabel('Create Question')
    }

    const handleClickArrowBack = () => {
        setQuestionBoardElement(getListQuestionBoard(question))
        setHideAddButton(false)
        setHideArrowBackIcon(true)
        setQuestionLabel('All question board')
    }

    const handleCreateSuccess = (item) => {
        handleClickArrowBack([ item ])
        handleClickQuestionCard(item)
    }
    
    return (
        <Box>
            <Grid container>
                <Grid item xs={10}>
                    <Typography variant='h6'>
                        {questionLabel}
                    </Typography>
                </Grid>
                <Grid item xs={2}>
                    <Box hidden={hideAddButton}>
                        <Fab size="small" color="primary" onClick={handleClickCreate}>
                            <AddIcon />
                        </Fab>
                    </Box>
                </Grid>
                <Grid item xs={12} hidden={hideArrowBackIcon} sx={{ mb: 1 }} >
                    <IconButton onClick={handleClickArrowBack}>
                        <ArrowBackIcon />
                    </IconButton>
                </Grid>
                <Grid item xs={hideArrowBackIcon ? 1 : 0}></Grid>
                <Grid item xs={11}>
                    {quesetionBoardElement}
                </Grid>
            </Grid>

        </Box>
    )
}

export default CourseQuestion