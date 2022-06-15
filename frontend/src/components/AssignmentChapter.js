import React from 'react'
import { useParams, useNavigate } from 'react-router-dom'

// Material UI component
import Paper from '@mui/material/Paper'
import Typography from '@mui/material/Typography'
import Grid from '@mui/material/Grid'

// Material UI icon
import AssignmentIcon from '@mui/icons-material/Assignment'

const AssignmentChapter = ({ chapterIndex, assignmentCount }) => {

    const { courseId } = useParams()

    const navigate = useNavigate()

    const chapterNo = Number(chapterIndex) + 1

    const handleClickAssignmentChapter = () => {
        navigate(`/student/course/${courseId}/assignment/chapter/${chapterIndex}`)
    }

    return (
        <Paper sx={{ p: 2, width: '80%', m: 2, cursor: 'pointer' }} onClick={handleClickAssignmentChapter}>
            <Grid container>
                <Grid item xs={10} pl={1}>
                    <Typography variant='subtitle1'>Assignment chapter {chapterNo}</Typography>
                </Grid>
                <Grid item xs={1} color='gray' textAlign='right'>
                    <AssignmentIcon />
                </Grid>
                <Grid item xs={1} color='gray' pl={1}>
                    <Typography>{assignmentCount}</Typography>
                </Grid>
            </Grid>
        </Paper>
    )
}

export default AssignmentChapter