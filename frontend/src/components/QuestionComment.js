import React from 'react'
import moment from 'moment'

// component
import Report from './Report'

// Material UI component
import Grid from '@mui/material/Grid'
import Paper from '@mui/material/Paper'
import Divider from '@mui/material/Divider'
import Avatar from '@mui/material/Avatar'
import Typography from '@mui/material/Typography'
import Chip from '@mui/material/Chip';

// utils
import color from '../utils/color'

const QuestionComment = ({ data }) => {

    let { comment, commentDateTime, commentState, firstname, lastname } = data

    return (
        <Paper sx={{ mt: 2, borderLeft: 3, borderColor: color.getColorCommentType(commentState) }}>
            <Grid container sx={{ padding: 2 }}>
                <Grid item xs={12} sx={{ mb: 2 }}>
                    {comment}
                </Grid>
                <Grid item xs={12}>
                    <Divider />
                </Grid>
                <Grid item xs={1} sx={{ justifyContent: "center", display: "flex", mt: 2 }}>
                    <Avatar src="/static/images/avatar/1.jpg" />
                </Grid>
                <Grid item xs={10}>
                    <Grid item xs={12} sx={{ mt: 1 }}>
                        <Typography variant='subtitle1'>
                            {firstname} {lastname} 
                            <Chip label={commentState} sx={{ ml: 1, pt: 0.4 }} size="small" variant="outlined" />
                        </Typography>
                    </Grid>
                    <Grid item xs={12}>
                        <Typography variant='caption' color="text.secondary">
                            {moment(commentDateTime).format("DD/MM/YYYY | kk:mm:ss")}
                        </Typography>
                    </Grid>
                </Grid>
                <Grid item xs={1} sx={{ textAlign: 'right', mt: 3 }}>
                    <Report type={'comment'} />
                </Grid>
            </Grid>
        </Paper>
    )
}

export default QuestionComment