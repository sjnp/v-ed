import React from 'react'
import { useParams, useNavigate } from 'react-router-dom'

// Material UI component
import Paper from '@mui/material/Paper'
import Typography from '@mui/material/Typography'
import Grid from '@mui/material/Grid'
import Badge from '@mui/material/Badge'

// Material UI icon
import AssignmentIcon from '@mui/icons-material/Assignment'

const AssignmentCourseInstructor = ({ chapterIndex, chapterNo, isNoti, countNoti }) => {

    const { courseId } = useParams()
    const navigate = useNavigate()

    const handleClickAssignmentChapter = () => {
        navigate(`/instructor/course/${courseId}/assignment/chapter/${chapterIndex}`)
    }

    return (
        <Paper sx={{ p: 2, width: '80%', m: 2, cursor: 'pointer' }} onClick={handleClickAssignmentChapter}>
            <Grid container>
                <Grid item xs={11} pl={1}>
                    <Typography variant='subtitle1'>Assignment chapter {chapterNo}</Typography>
                </Grid>
                <Grid item xs={1} color='gray' pl={1}>
                    <Badge badgeContent={countNoti} color='error'>
                        <AssignmentIcon />
                    </Badge>
                </Grid>
            </Grid>
        </Paper>
    )
}

export default AssignmentCourseInstructor