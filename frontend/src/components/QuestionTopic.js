import React, { useState } from 'react'

// component
import Report from './Report'

// Material UI
import Grid from '@mui/material/Grid'
import Paper from '@mui/material/Paper'
import Box from '@mui/material/Box'
import Divider from '@mui/material/Divider'
import Avatar from '@mui/material/Avatar'
import IconButton from '@mui/material/IconButton'
import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'


// icon
import MoreVertIcon from '@mui/icons-material/MoreVert';
import { Typography } from '@mui/material'
import FlagIcon from '@mui/icons-material/Flag';

const QuestionTopic = () => {

    const topic = "Topic Name"
    const detail = "detail topic"
    const firstname = "pradinan"
    const lastname = "benjanavee"
    const datetime = "7/4/64 01:10:46"

    const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

    return (
        <Paper>
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
                
                <Grid item xs={10}>

                    <Grid item xs={12} sx={{ mt: 1 }}>
                        <Typography variant='subtitle1'>
                            {firstname} {lastname}
                        </Typography>
                    </Grid>
                
                    <Grid item xs={12}>
                        <Typography variant='caption' color="text.secondary">
                            {datetime}
                        </Typography>
                    </Grid>
                
                </Grid>

                <Grid item xs={1} sx={{ textAlign: 'right', mt: 3 }}>
                    <Report />
                </Grid>

            </Grid>
        </Paper>
    )
}

export default QuestionTopic