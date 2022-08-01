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

// utils
import color from '../utils/color'

const PostTopic = ({ data }) => {

    const username = useSelector((state) => state.auth.value.username)

    const { id, topic, detail, datetime, profilePictureUrl, firstname, lastname } = data
 
    return (
        <Paper sx={{ borderLeft: 3, borderColor: color.getColorCommentType('OWNER'), mb: 5 }}>
            <Grid container p={2}>
                <Grid item xs={12}>
                    <Typography variant="h6">{topic}</Typography>
                </Grid>
                <Grid item xs={12} mt={2} mb={2}>
                    {detail}
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
                <Grid item xs={10} pl={1}>
                    <Grid item xs={12} mt={1}>
                        <Typography variant='subtitle1'> {firstname} {lastname}</Typography>
                    </Grid>
                    <Grid item xs={12}>
                        <Typography variant='caption' color="text.secondary">
                            {moment(datetime).format("DD/MM/YYYY | kk:mm:ss")}
                        </Typography>
                    </Grid>
                </Grid>
                <Grid item xs={1} mt={3} textAlign='right'>
                    <Report type='post' contentId={id} />
                </Grid>
            </Grid>
        </Paper>
    )
}

export default PostTopic