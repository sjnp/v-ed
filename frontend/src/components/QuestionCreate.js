import React, { useState } from 'react'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// api
import apiPrivate from '../api/apiPrivate';

// Material UI component
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import CircularProgress from '@mui/material/CircularProgress';

// url
import { URL_QUESTION_BOARD_CREATE } from '../utils/url';

const QuestionCreate = ({ onCreateSuccess }) => {

    const axiosPrivate = useAxiosPrivate()

    const maxLengthTopic = 200
    const maxLengthDetail = 1000

    const [ topic, setTopic ] = useState('')
    const [ detail, setDetail ] = useState('')

    const [ messageTopic, setMessageTopic ] = useState(`(0/${maxLengthTopic})`)
    const [ messageDetail, setMessageDetail ] = useState(`(0/${maxLengthDetail})`)

    const [ errorTopic, setErrorTopic ] = useState(false)
    const [ errorDetail, setErrorDetail ] = useState(false)

    const [ loading, setLoading ] = useState(false)

    const handleChangeTopic = (event) => {
        if (event.target.value.length <= maxLengthTopic) {
            setTopic(event.target.value)
            setMessageTopic(`(${event.target.value.length}/${maxLengthTopic})`)
            setErrorTopic(false)
        } else {
            setMessageTopic(`(${topic.length}/${maxLengthTopic}) limit ${maxLengthTopic} character`)
            setErrorTopic(true)
        }
    }

    const handleChangeDetail = (event) => {
        if (event.target.value.length <= maxLengthDetail) {
            setDetail(event.target.value)
            setMessageDetail(`(${event.target.value.length}/${maxLengthDetail})`)
            setErrorDetail(false)
        } else {
            setMessageDetail(`(${detail.length}/${maxLengthDetail}) limit ${maxLengthDetail} character`)
            setErrorDetail(true)
        }
    }

    const handleBlurTopic = () => {
        setMessageTopic(`(${topic.length}/${maxLengthTopic})`)
        setErrorTopic(false)
    }

    const handleBlurDetail = () => {
        setMessageDetail(`(${detail.length}/${maxLengthDetail})`)
        setErrorDetail(false)
    }

    const handleClickCreate = async () => {
        
        let isRequired = false

        if (topic.length === 0) {
            setMessageTopic(`(${topic.length}/${maxLengthTopic}) is required`)
            setErrorTopic(true)
            isRequired = true
        }

        if (detail.length === 0) {
            setMessageDetail(`(${detail.length}/${maxLengthDetail}) is required`)
            setErrorDetail(true)
            isRequired = true
        }

        if (isRequired) return

        setLoading(true)

        const payLoad = {
            topic: topic,
            detail: detail
        }
        const response = await apiPrivate.post(axiosPrivate, URL_QUESTION_BOARD_CREATE, payLoad)

        if (response.status === 201) {
            onCreateSuccess(response.data)
        } else {
            alert('Error, please try again')
        }
        setLoading(false)
    }

    return (
        <Paper sx={{ p: 3 }}>
            <TextField
                id="topic"
                label="Topic"
                variant="outlined"
                margin="normal"
                required 
                fullWidth
                multiline
                value={topic}
                onChange={handleChangeTopic}
                helperText={messageTopic}
                error={errorTopic}
                onBlur={handleBlurTopic}
            />
            <TextField
                id="detail"
                label="Detail"
                variant="outlined"
                margin="normal"
                required 
                fullWidth
                multiline
                value={detail}
                onChange={handleChangeDetail}
                helperText={messageDetail}
                error={errorDetail}
                onBlur={handleBlurDetail}
            />
            <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', mt: 2 }}>
                <Button variant='contained' disabled={loading} sx={{ width: '20%' }} onClick={handleClickCreate}>
                    Create
                </Button>
            </Box>
            {
                loading && 
                <CircularProgress
                    size={24}
                    sx={{
                        color: 'green', 
                        position: 'absolute', 
                        top: '50%', 
                        left: '50%', 
                        mt: '-12px', 
                        ml: '-12px'
                    }}
                />
            }
        </Paper>
    )
}

export default QuestionCreate