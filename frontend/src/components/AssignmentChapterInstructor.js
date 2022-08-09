import React from 'react'
import { useParams, useNavigate } from 'react-router-dom'

// Material UI component
import Paper from '@mui/material/Paper'
import Typography from '@mui/material/Typography'
import Grid from '@mui/material/Grid'

const AssignmentChapterInstructor = ({ noIndex, detail }) => {

    const { courseId, chapterIndex } = useParams()
    const navigate = useNavigate()

    const no = noIndex + 1

    const handleClickAssignmentChapter = () => {
        navigate(`/instructor/course/${courseId}/assignment/chapter/${chapterIndex}/answer/${noIndex}`)
    }

    return (
        <Paper sx={{ p: 2, width: '80%', m: 2, cursor: 'pointer' }} onClick={handleClickAssignmentChapter}>
            <Grid container>
                <Grid item xs={10} pl={1}>
                    <Typography variant='subtitle1' fontWeight='bold'>{no}. {detail}</Typography>
                </Grid>
                <Grid item xs={1} color='gray' textAlign='right'>
                    {/* TODO */}
                </Grid>
                <Grid item xs={1} color='gray' pl={1}>
                    {/* TODO */}
                </Grid>
            </Grid>
        </Paper>
    )
}

export default AssignmentChapterInstructor