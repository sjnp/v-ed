import React from 'react'

// component
import QuestionTopic from './QuestionTopic'
import QuestionComment from './QuestionComment'
import QuestionWriteComment from './QuestionWriteComment'

// Material UI
import Box from '@mui/material/Box'
import Toolbar from '@mui/material/Toolbar'

const QuestionBoard = () => {
    
    

    return (
        <Box>
            <QuestionTopic />
            <QuestionComment />
            <Toolbar sx={{ m: 1 }} />
            <QuestionWriteComment />
        </Box>
    )
}

export default QuestionBoard