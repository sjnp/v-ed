import React, { useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// Material UI component
import Box from '@mui/material/Box'
import Paper from '@mui/material/Paper'
import TextField from '@mui/material/TextField'
import LoadingButton from '@mui/lab/LoadingButton'

// url
import { URL_CREATE_POST } from '../utils/url'

const QuestionCreate = () => {

    const { courseId } = useParams()
    const axiosPrivate = useAxiosPrivate()
    const navigate = useNavigate()

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
        let invalid = false

        if (topic.length === 0) {
            setMessageTopic(`(${topic.length}/${maxLengthTopic}) is required`)
            setErrorTopic(true)
            invalid = true
        }

        if (detail.length === 0) {
            setMessageDetail(`(${detail.length}/${maxLengthDetail}) is required`)
            setErrorDetail(true)
            invalid = true
        }

        if (invalid) return

        setLoading(true)
        const payLoad = {
            courseId: courseId,
            topic: topic,
            detail: detail
        }
        const response = await apiPrivate.post(axiosPrivate, URL_CREATE_POST, payLoad)
        setLoading(false)

        if (response.status === 201) {
            navigate(`/student/course/${courseId}/post/${response.data.postId}`)
        } else {
            alert('Error, please try again')
        }
    }

    return (
        <Paper sx={{ p: 3 }}>
            <TextField
                id="topic"
                label="Topic"
                variant="outlined"
                margin="normal"
                size='small'
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
                size='small'
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
                <LoadingButton variant='contained' loading={loading} onClick={handleClickCreate} sx={{ width: '20%' }}>
                    Create
                </LoadingButton>
            </Box>
        </Paper>
    )
}

export default QuestionCreate