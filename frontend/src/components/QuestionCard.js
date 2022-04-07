import React from 'react'

// Material UI
import Grid from '@mui/material/Grid'
import Paper from '@mui/material/Paper'
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import IconButton from '@mui/material/IconButton'

// icon
import CommentIcon from '@mui/icons-material/Comment';
import MessageIcon from '@mui/icons-material/Message';

const QuestionCard = ({ topic, datetime, comment, onClickQuestionCard }) => {
    
    return (
        <Paper sx={{ m: 2, p: 2, width: '70%', cursor: 'pointer' }} onClick={onClickQuestionCard}>
            <Grid container>
                <Grid item xs={11}>
                    <Box sx={{ fontWeight: 'bold' }}>
                        {topic}
                    </Box>
                </Grid>
                <Grid item xs={1}>
                    <CommentIcon />
                    {comment}
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