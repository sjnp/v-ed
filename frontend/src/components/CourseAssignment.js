import React, { useState } from 'react'
import { useSelector } from 'react-redux'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// component
import AssignmentChapter from './AssignmentChapter'
import AssignmentAnswer from './AssignmentAnswer'

// Material UI
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import IconButton from '@mui/material/IconButton'

// icon
import ArrowBackIcon from '@mui/icons-material/ArrowBack'

const CourseAssignment = () => {

    const assignments = useSelector(state => state.studentAssignment.value.assignment)

    const [ suffixAssignment, setSuffixAssignment ] = useState('')

    const getAssignmentChapterElement = () => {
        return assignments.map((assignment, index) => (
            <AssignmentChapter
                key={index}
                chapterNo={index + 1}
                onClick={() => handleClickChapterAssignment(assignment, index + 1)}
            />
        ))
    }

    const getAssignmentAnswerElement = (questions = [], chapterNo) => {
        return questions.map((question, index) => (
            <AssignmentAnswer
                key={index}
                no={index + 1}
                chapterNo={chapterNo}
                question={question.detail}
            />
        ))
    }

    const [ assignmentElement, setAssignmentElement ] = useState(getAssignmentChapterElement())

    const [ hideArrowBackIcon, setHideArrowBackIcon ] = useState(true)

    const handleClickChapterAssignment = (questions, chapterNo) => {
        const element = getAssignmentAnswerElement(questions, chapterNo)
        setAssignmentElement(element)
        setHideArrowBackIcon(false)
        setSuffixAssignment(` chapter ${chapterNo}`)
    }

    const handleClickArrowBack = () => {
        const element = getAssignmentChapterElement()
        setAssignmentElement(element)
        setHideArrowBackIcon(true)
        setSuffixAssignment('')
    }

    return (
        <Box>
            <Typography variant='h6'>
                Assignment {suffixAssignment}
            </Typography>
            <Box hidden={hideArrowBackIcon}>
                <IconButton onClick={handleClickArrowBack}>
                    <ArrowBackIcon />
                </IconButton>
            </Box>
            <Box>
                {assignmentElement}
            </Box>
        </Box>
    )
}

export default CourseAssignment