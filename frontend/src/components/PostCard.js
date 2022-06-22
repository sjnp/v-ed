import React from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import moment from 'moment'

// Material UI component
import Grid from '@mui/material/Grid'
import Paper from '@mui/material/Paper'
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'

// Material UI icon
import CommentIcon from '@mui/icons-material/Comment'

const PostCard = ({ postId, topic, datetime, commentCount }) => {

    const { courseId } = useParams()
    const navigate = useNavigate()

    const handleClickPost = () => {
        navigate(`/student/course/${courseId}/post/${postId}`)
    }
    
    return (
        <Paper sx={{ m: 2, p: 2, width: '70%', cursor: 'pointer' }} onClick={handleClickPost}>
            <Grid container>
                <Grid item xs={10}>
                    <Typography noWrap fontWeight='bold' title={topic}>{topic}</Typography>
                </Grid>
                <Grid item xs={2} textAlign='end' sx={{ color: 'gray' }}>
                    <Box display='flex' justifyContent='flex-end'>
                        <CommentIcon />
                        <Typography variant='body2' component='span' paddingLeft={1}>{commentCount}</Typography>
                    </Box>
                </Grid>
                <Grid item>
                    <Typography variant="caption" color="text.secondary">
                        {moment(datetime).format("DD/MM/YYYY | kk:mm:ss")}
                    </Typography>
                </Grid>
            </Grid>
        </Paper>
    )
}

export default PostCard