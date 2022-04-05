import React, { useState } from 'react'

// Material UI
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

const QuestionCreate = () => {

    // const getCapitalize = (str) => {
    //     return str.charAt(0).toUpperCase() + str.slice(1)
    // }

    // const getMaxLengthInput = (label, questionName, name) => {
    //     return `${label} (${questionName.length}/${maxLength[name]})`
    // }

    // const [ question, setQuestion ] = useState({
    //     topic: '',
    //     detail: ''
    // })

    // const [ label, setLabel ] = useState({
    //     topic: 'Topic',
    //     detail: 'Detail'
    // })

    // const [ maxLength, setMaxLength ] = useState({
    //     topic: 200,
    //     detail: 1000
    // })

    // const handleChange = (event) => {
    //     const { name, value } = event.target
    //     setQuestion({
    //         ...question,
    //         [name]: value
    //     })

    //     const labelName = getCapitalize(name)
    //     setLabel({
    //         ...label,
    //         [name]: getMaxLengthInput(labelName, value, name)
    //     })
    // }

    // const handleFocus = (event) => {
    //     const { name } = event.target
    //     const labelName = getCapitalize(name) // text -> Text   
    //     setLabel({
    //         ...label,
    //         [name]: getMaxLengthInput(labelName, question[name], name)
    //     })
    // }

    // const handleBlur = (event) => {
    //     const { name } = event.target
    //     let labelName = getCapitalize(name) // text -> Text
    //     if (question[name].length > 0) {
    //         labelName = getMaxLengthInput(labelName, question[name], name)
    //     }

    //     setLabel({
    //         ...label,
    //         [name]: labelName
    //     })
    // }

    

    const [ question, setQuestion ] = useState({
        topic: '',
        detail: ''
    })

    const handleChangeQuestion = (event) => {
        const { id, value } = event.target
        setQuestion({
            ...question,
            [id]: value
        })

        console.log(`${id} => ${value}`)
    }

    const createQuestionBoard = () => {
        alert(`topic => ${question.topic}\ndetail => ${question.detail}`)
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
                onChange={handleChangeQuestion}
                // onFocus={handleFocus}
                // onBlur={handleBlur}
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
                onChange={handleChangeQuestion}
                // onFocus={handleFocus}
                // onBlur={handleBlur}
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