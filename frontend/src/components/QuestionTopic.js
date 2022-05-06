import React from 'react'
import { useSelector } from 'react-redux'
import moment from 'moment'

// component
import Report from './Report'

// Material UI component
import Grid from '@mui/material/Grid'
import Paper from '@mui/material/Paper'
import Divider from '@mui/material/Divider'
import Avatar from '@mui/material/Avatar'

// Material UI icon
import { Typography } from '@mui/material'

// utils
import color from '../utils/color'

const QuestionTopic = ({ data }) => {

    const { id, topic, detail, datetime, firstname, lastname } = data

    // const topic = useSelector(state => state.questionBoard.value.topic)
    // const detail = useSelector(state => state.questionBoard.value.detail)
    // const datetime = useSelector(state => state.questionBoard.value.datetime)

    // const firstname = "pradinan"
    // const lastname = "benjanavee"
 
    return (
        <Paper sx={{ borderLeft: 3, borderColor: color.getColorCommentType('OWNER') }}>
            <Grid container sx={{ padding: 2 }}>
                <Grid item xs={12}>
                    <Typography variant="h6">
                        {topic} 
                    </Typography>
                </Grid>
                <Grid item xs={12} sx={{ mt: 2, mb:2 }}>
                    {detail}
                </Grid>
                <Grid item xs={12}>
                    <Divider />
                </Grid>
                <Grid item xs={1} sx={{ justifyContent: "center", display: "flex", mt: 2 }}>
                    <Avatar src="/static/images/avatar/1.jpg" sx={{   }} />
                </Grid>
                <Grid item xs={10} sx={{ pl: 1 }}>
                    <Grid item xs={12} sx={{ mt: 1 }}>
                        <Typography variant='subtitle1'>
                            {firstname} {lastname}
                        </Typography>
                    </Grid>
                    <Grid item xs={12}>
                        <Typography variant='caption' color="text.secondary">
                            {moment(datetime).format("DD/MM/YYYY | kk:mm:ss")}
                        </Typography>
                    </Grid>
                </Grid>
                <Grid item xs={1} sx={{ textAlign: 'right', mt: 3 }}>
                    <Report type={'question'} />
                </Grid>
            </Grid>
        </Paper>
    )
}

export default QuestionTopic