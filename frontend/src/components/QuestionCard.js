import React from 'react'

// Material UI component
import Grid from '@mui/material/Grid'
import Paper from '@mui/material/Paper'
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'

// Material UI icon
import CommentIcon from '@mui/icons-material/Comment'

const QuestionCard = ({ topic, datetime, commentCount, onClickQuestionCard }) => {
    
    return (
        <Paper sx={{ m: 2, p: 2, width: '70%', cursor: 'pointer' }} onClick={onClickQuestionCard}>
            <Grid container>
                <Grid item xs={10}>
                    <Box sx={{ fontWeight: 'bold' }}>
                        {topic}
                    </Box>
                </Grid>
                <Grid item xs={1} textAlign='end' sx={{ color: 'gray' }}>
                    <CommentIcon />
                </Grid>
                <Grid item xs={1} sx={{ pl: 1, fontSize: 15, color: 'gray' }}>
                    {commentCount}
                </Grid>
                <Grid item>
                    <Typography variant="caption" color="text.secondary">
                        {datetime}
                    </Typography>
                </Grid>
            </Grid>
        </Paper>
    )
}

export default QuestionCard