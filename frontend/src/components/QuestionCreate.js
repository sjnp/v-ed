import React, { useState } from 'react'

// Material UI
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

const QuestionCreate = ({ onCreateSuccess }) => {

    const maxLength = {
        topic: 2,
        detail: 10
    }

    const [ question, setQuestion ] = useState({
        topic: '',
        detail: ''
    })
    
    const [ message, setMessage ] = useState({ // ex. (0/200)
        topic: `(${question.topic.length}/${maxLength.topic})`,
        detail: `(${question.detail.length}/${maxLength.detail})`
    })

    const [ error, setError ] = useState({
        topic: false,
        detail: false
    })

    const handleChange = (event) => {

        const { id, value } = event.target

        if (value.length <= maxLength[id]) {

            setQuestion({ ...question, [id]: value })
            const newMessage = `(${value.length}/${maxLength[id]})`
            setMessage({ ...message, [id]: newMessage })
            setError({ topic: false, detail: false })
        
        } else {
            setMessage({ ...message, [id]: `(${question[id].length}/${maxLength[id]}) over max length` })
            setError({ ...error, [id]: true })
        }   
    }

    const handleBlur = (event) => {
        const { id, value } = event.target
        const newMessage = `(${value.length}/${maxLength[id]})`
        setMessage({ ...message, [id]: newMessage })
        setError({ ...error, [id]: false })
    }

    const createQuestionBoard = () => {
        
        if (question.topic.length === 0) {
            setMessage({ ...message, topic: 'Topic is require' })
            setError({ ...error, topic: true })
            return
        }

        if (question.detail.length === 0) {
            setMessage({ ...message, detail: 'Detail is require' })
            setError({ ...error, detail: true })
            return
        }

        console.log(typeof onCreateSuccess)
        onCreateSuccess()
    }

    return (
        <Paper sx={{ padding: 3 }}>
            <TextField
                id="topic"
                label="Topic"
                variant="outlined"
                margin="normal"
                required 
                fullWidth
                value={question.topic}
                helperText={message.topic}
                error={error.topic}
                onChange={handleChange}
                onBlur={handleBlur}
            />
            <TextField
                id="detail"
                label="Detail"
                variant="outlined"
                margin="normal"
                required 
                fullWidth
                multiline
                rows={10}
                value={question.detail}
                helperText={message.detail}
                error={error.detail}
                onChange={handleChange}
                onBlur={handleBlur}
            />
            <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                <Button variant='contained' onClick={createQuestionBoard}>
                    Create
                </Button>
            </Box>
        </Paper>
    )
}

export default QuestionCreate