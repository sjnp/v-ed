import React from 'react'

// component
import QuestionTopic from './QuestionTopic';
import QuestionComment from './QuestionComment';

// Material UI
import Box from '@mui/material/Box'

const QuestionBoard = () => {

    return (
        <Box>
            <QuestionTopic />
            <QuestionComment />
        </Box>
    )
}

export default QuestionBoard