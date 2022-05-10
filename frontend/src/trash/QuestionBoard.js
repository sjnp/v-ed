import React, { useState } from 'react'
import { useSelector } from 'react-redux'

// component
import QuestionTopic from '../components/QuestionTopic'
import QuestionComment from '../components/QuestionComment'
import QuestionWriteComment from '../components/QuestionWriteComment'

// Material UI
import Box from '@mui/material/Box'
import Toolbar from '@mui/material/Toolbar'

const QuestionBoard = () => {
    
    const initComments = useSelector(state => state.comment.value.comments)

    const [ comments, setComments ] = useState(initComments)

    const handleCreateCommentSuccess = (data) => {
        setComments([ ...comments, data ])
    }

    return (
        <Box>
            <QuestionTopic />
            {
                comments?.map((comment, index) => <QuestionComment key={index} data={comment} />)
            }
            <Toolbar sx={{ m: 1 }} />
            <QuestionWriteComment onCreateCommentSuccess={handleCreateCommentSuccess} />
        </Box>
    )
}

export default QuestionBoard