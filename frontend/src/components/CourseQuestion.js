import React, { useState } from 'react'

// component
import QuestionBoard from './QuestionBoard';
import QuestionCreate from './QuestionCreate';
import QuestionCard from './QuestionCard';

// Material UI
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import Fab from '@mui/material/Fab';
import Grid from '@mui/material/Grid';
import IconButton from '@mui/material/IconButton'

// icon
import AddIcon from '@mui/icons-material/Add';
import ArrowBackIcon from '@mui/icons-material/ArrowBack'


const CourseQuestion = () => {

    const handleClickQuestionCard = (item) => {
        setQuestionBoardElement(
            <QuestionBoard
                
            />
        )
        setHideAddButton(true)
        setHideArrowBackIcon(false)
    }

    const getListQuestionBoard = () => {
        let index = 0
        const data = [
            {
                topic: `Topic ${++index}`,
                datetime: new Date().toISOString(),
                comment: index
            },
            {
                topic: `Topic ${++index}`,
                datetime: new Date().toISOString(),
                comment: index
            },
            {
                topic: `Topic ${++index}`,
                datetime: new Date().toISOString(),
                comment: index
            },
            {
                topic: `Topic ${++index}`,
                datetime: new Date().toISOString(),
                comment: index
            },
        ]

        const result = data.map((item, index) => (
            <QuestionCard 
                key={index}
                topic={item.topic}
                datetime={item.datetime}
                comment={item.comment}
                onClickQuestionCard={() => handleClickQuestionCard(item)}
            />
        ))

        return result
    }

    const listQuestionBorad = getListQuestionBoard() // api 

    const [ quesetionBoardElement, setQuestionBoardElement ] = useState(listQuestionBorad)

    const [ hideAddButton, setHideAddButton ] = useState(false)
    const [ hideArrowBackIcon, setHideArrowBackIcon ] = useState(true)

    const handleClickCreate = () => {
        setQuestionBoardElement(<QuestionCreate onCreateSuccess={handleCreateSuccess} />)
        setHideAddButton(true)
        setHideArrowBackIcon(false)
    }

    const handleClickArrowBack = () => {
        setQuestionBoardElement(listQuestionBorad)
        setHideAddButton(false)
        setHideArrowBackIcon(true)
    }

    const handleCreateSuccess = () => {
        handleClickArrowBack()
    }

    return (
        <Box>
            <Grid container>
                <Grid item xs={2}>
                    <Typography variant='h6'>
                        Question board
                    </Typography>
                </Grid>
                <Grid item xs={8}></Grid>
                <Grid item xs={2}>
                    <Box hidden={hideAddButton}>
                        <Fab size="small" color="primary" onClick={handleClickCreate}>
                            <AddIcon />
                        </Fab>
                    </Box>
                </Grid>
            </Grid>
            <Box hidden={hideArrowBackIcon}>
                <IconButton onClick={handleClickArrowBack}>
                    <ArrowBackIcon />
                </IconButton>
            </Box>
            <Box>
                {quesetionBoardElement}
            </Box>
        </Box>
    )
}

export default CourseQuestion