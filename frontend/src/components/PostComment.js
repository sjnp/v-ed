import React from 'react'
import { useSelector} from "react-redux"
import moment from 'moment'

// component
import Report from './Report'
import stringToColor from './stringToColor'

// Material UI component
import Grid from '@mui/material/Grid'
import Paper from '@mui/material/Paper'
import Divider from '@mui/material/Divider'
import Avatar from '@mui/material/Avatar'
import Typography from '@mui/material/Typography'
import Chip from '@mui/material/Chip'

// utils
import color from '../utils/color'

const PostComment = ({ data }) => {

    const username = useSelector((state) => state.auth.value.username)

    let { id, comment, commentDateTime, commentState, profilePictureUrl, firstname, lastname } = data

    return (
        <Paper sx={{ mt: 3, borderLeft: 3, borderColor: color.getColorCommentType(commentState) }}>
            <Grid container p={2}>
                <Grid item xs={12} mb={2}>
                    {comment}
                </Grid>
                <Grid item xs={12}>
                    <Divider />
                </Grid>
                <Grid item xs={1} display='flex' justifyContent='center' mt={2}>
                    <Avatar
                        alt={username} 
                        src={profilePictureUrl || "/static/images/avatar/2.jpg"}
                        sx={{bgcolor: stringToColor(username)}}
                    />
                </Grid>
                <Grid item xs={10}>
                    <Grid item xs={12} mt={1}>
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
                <Grid item xs={1} mt={3} textAlign='right'>
                    <Report type='comment' contentId={id} />
                </Grid>
            </Grid>
        </Paper>
    )
}

export default PostComment