import React from 'react'
import moment from 'moment'

// component
import Report from './Report'

// Material UI component
import Paper from '@mui/material/Paper'
import Grid from '@mui/material/Grid'
import Rating from '@mui/material/Rating'
import Avatar from '@mui/material/Avatar'
import Divider from '@mui/material/Divider'
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'

// Material UI icon 
import StarIcon from '@mui/icons-material/Star'

const ReviewCard = ({ reviewId, rating, comment, firstname, lastname, datetime }) => {

    return (
        <Paper elevation={3} sx={{ width: 500, m: 'auto', p: 2, mb: 3 }}>
            <Grid container>
                <Grid item xs={12}>
                    <Box display="flex" alignItems='center'>
                        <Box flexGrow={1}>
                            <Rating value={rating} readOnly emptyIcon={<StarIcon fontSize="inherit" />} />
                        </Box>
                        <Box >
                            <Report type={'review'} />
                        </Box>
                    </Box>
                </Grid>
                <Grid item xs={12} sx={{ mb: 2, pl: 1 }}>
                    <Typography variant='subtitle1'>{comment}</Typography>
                </Grid>
                <Grid item xs={12}>
                    <Divider />
                </Grid>
                <Grid item xs={2} sx={{ justifyContent: "center", display: "flex", mt: 2 }}>
                    <Avatar src="/static/images/avatar/1.jpg" />
                </Grid>
                <Grid item xs={10}>
                    <Grid item xs={12} sx={{ mt: 1 }}>
                        <Typography variant='subtitle1'>{firstname} {lastname}</Typography>
                    </Grid>
                    <Grid item xs={12}>
                        <Typography variant='caption' color="text.secondary">
                            {moment(datetime).format('MM/DD/YYYY | kk:mm:ss')}
                        </Typography>
                    </Grid>
                </Grid>
            </Grid>
        </Paper>
    )
}

export default ReviewCard