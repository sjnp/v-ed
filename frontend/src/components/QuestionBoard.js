import React from 'react'

// component
import QuestionTopic from './QuestionTopic';
import QuestionComment from './QuestionComment';

// Material UI
import Box from '@mui/material/Box'

const QuestionBoard = () => {

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

    return (
        <Box>
            <QuestionTopic />
            <QuestionComment />
        </Box>
    )
}

export default QuestionBoard