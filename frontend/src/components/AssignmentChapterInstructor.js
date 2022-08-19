import React from 'react'
import { useParams, useNavigate } from 'react-router-dom'

// Material UI component
import Paper from '@mui/material/Paper'
import Typography from '@mui/material/Typography'
import Grid from '@mui/material/Grid'
import Badge from '@mui/material/Badge'

// Material UI icon
import AssignmentIcon from '@mui/icons-material/Assignment'

const AssignmentChapterInstructor = ({ noIndex, detail, countNoti }) => {

    const { courseId, chapterIndex } = useParams()
    const navigate = useNavigate()

    const no = noIndex + 1

    const handleClickAssignmentChapter = () => {
        navigate(`/instructor/course/${courseId}/assignment/chapter/${chapterIndex}/answer/${noIndex}`)
    }

    return (
        <Paper sx={{ p: 2, width: '80%', m: 2, cursor: 'pointer' }} onClick={handleClickAssignmentChapter}>
            <Grid container>
                <Grid item xs={11} pl={1}>
                    <Typography variant='subtitle1' fontWeight='bold'>{no}. {detail}</Typography>
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

export default AssignmentChapterInstructor