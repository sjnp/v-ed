import React, { useState } from 'react'

// component
import AssignmentSection from './AssignmentSection'
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
        name: 'Section name 1',
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
        name: 'Section name 2',
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
        name: 'Section name 3',
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
        name: 'Section name 4',
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
        name: 'Section name 5',
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

    const getAssignmentSectionElement = () => {
        return assignments.map((assignment, index) => (
            <AssignmentSection
                key={index}
                sectionNo={assignment.no} 
                sectionName={assignment.name}
                onClick={() => handleClickSectionAssignment(assignment.questions)}
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

    const [ assignmentElement, setAssignmentElement ] = useState(getAssignmentSectionElement())

    const [ hideArrowBackIcon, setHideArrowBackIcon ] = useState(true)

    const handleClickSectionAssignment = (questions) => {
        const element = getAssignmentAnswerElement(questions)
        setAssignmentElement(element)
        setHideArrowBackIcon(false)
    }

    const handleClickArrowBack = () => {
        const element = getAssignmentSectionElement()
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