import React, { useState } from 'react'

// component
import AssignmentChapter from './AssignmentChapter'
import AssignmentAnswer from './AssignmentAnswer'

// Material UI
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import IconButton from '@mui/material/IconButton'

// icon
import ArrowBackIcon from '@mui/icons-material/ArrowBack'

// Data hard code for test
const getAssignment = () => [
    {
        no: 1,
        name: 'Chapter name 1',
        questions: [
            {
                no: 1,
                question: 'question 1 ?',
                questionDetail: 'question detail 1',
                commentInstructor: ''
            },
        ]
    },
    {
        no: 2,
        name: 'Chapter name 2',
        questions: [
            {
                no: 1,
                question: 'question 1 ?',
                questionDetail: 'question detail 1',
                commentInstructor: ''
            },
            {
                no: 2,
                question: 'question 2 ?',
                questionDetail: 'question detail 2',
                commentInstructor: 'normal'
            },
        ]
    },
    {
        no: 3,
        name: 'Chapter name 3',
        questions: [
            {
                no: 1,
                question: 'question 1 ?',
                questionDetail: 'question detail 1',
                commentInstructor: ''
            },
            {
                no: 2,
                question: 'question 2 ?',
                questionDetail: 'question detail 2',
                commentInstructor: 'normal'
            },
            {
                no: 3,
                question: 'question 3 ?',
                questionDetail: 'question detail 3',
                commentInstructor: 'bad'
            },
        ]
    },
    {
        no: 4,
        name: 'Chapter name 4',
        questions: [
            {
                no: 1,
                question: 'question 1 ?',
                questionDetail: 'question detail 1',
                commentInstructor: ''
            },
            {
                no: 2,
                question: 'question 2 ?',
                questionDetail: 'question detail 2',
                commentInstructor: 'normal'
            },
            {
                no: 3,
                question: 'question 3 ?',
                questionDetail: 'question detail 3',
                commentInstructor: 'bad'
            },
            {
                no: 4,
                question: 'question 4 ?',
                questionDetail: 'question detail 4',
                commentInstructor: 'good'
            },
        ]
    },
    {
        no: 5,
        name: 'Chapter name 5',
        questions: [
            {
                no: 1,
                question: 'question 1 ?',
                questionDetail: 'question detail 1',
                commentInstructor: ''
            },
            {
                no: 2,
                question: 'question 2 ?',
                questionDetail: 'question detail 2',
                commentInstructor: 'normal'
            },
            {
                no: 3,
                question: 'question 3 ?',
                questionDetail: 'question detail 3',
                commentInstructor: 'bad'
            },
            {
                no: 4,
                question: 'question 4 ?',
                questionDetail: 'question detail 4',
                commentInstructor: 'good'
            },
            {
                no: 5,
                question: 'question 5 ?',
                questionDetail: 'question detail 5',
                commentInstructor: ''
            },
        ]
    }
]

const CourseAssignment = () => {

    const assignments = getAssignment() // this is api (future)

    const getAssignmentChapterElement = () => {
        return assignments.map((assignment, index) => (
            <AssignmentChapter
                key={index}
                chapterNo={assignment.no} 
                chapterName={assignment.name}
                onClick={() => handleClickChapterAssignment(assignment.questions)}
            />
        ))
    }

    const getAssignmentAnswerElement = (questions = []) => {
        return questions.map((question, index) => (
            <AssignmentAnswer
                key={index}
                no={question.no}
                question={question.question}
                questionDetail={question.questionDetail}
                commentInstructor={question.commentInstructor}
            />
        ))
    }

    const [ assignmentElement, setAssignmentElement ] = useState(getAssignmentChapterElement())

    const [ hideArrowBackIcon, setHideArrowBackIcon ] = useState(true)

    const handleClickChapterAssignment = (questions) => {
        const element = getAssignmentAnswerElement(questions)
        setAssignmentElement(element)
        setHideArrowBackIcon(false)
    }

    const handleClickArrowBack = () => {
        const element = getAssignmentChapterElement()
        setAssignmentElement(element)
        setHideArrowBackIcon(true)
    }

    return (
        <Box>
            <Typography variant='h6'>
                Assignment
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