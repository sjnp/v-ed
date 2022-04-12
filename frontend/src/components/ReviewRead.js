import React from 'react'

// component
import Report from './Report'

// Material UI
import Paper from '@mui/material/Paper'
import Grid from '@mui/material/Grid'
import Rating from '@mui/material/Rating'
import Avatar from '@mui/material/Avatar'
import Divider from '@mui/material/Divider'

// icon
import StarIcon from '@mui/icons-material/Star'
import { Typography } from '@mui/material'

const ReviewRead = ({ rating, comment, firstname, lastname, datetime }) => {

    return (
        <Paper elevation={3} sx={{ width: 500, m: 'auto', p: 2, mb: 3 }}>
            <Grid container>

                <Grid item xs={4}>
                    <Rating
                        value={rating} 
                        size="large" 
                        readOnly 
                        precision={0.1} 
                        emptyIcon={<StarIcon fontSize="inherit" />}
                        sx={{ m: 'auto' }}
                    />
                </Grid>
                    
                <Grid item xs={1} sx={{ p: 1 }}>
                    {rating}
                </Grid>
                
                <Grid item xs={7} sx={{ textAlign: 'right' }}>
                    <Report />
                </Grid>
                
                <Grid item xs={12} sx={{ mb: 2, mt: 2, pl: 2 }}>
                    <Typography variant='subtitle1'>
                        {comment}
                    </Typography>
                </Grid>

                <Grid item xs={12}>
                    <Divider />
                </Grid>

                <Grid item xs={2} sx={{ justifyContent: "center", display: "flex", mt: 2 }}>
                    <Avatar src="/static/images/avatar/1.jpg" />
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

            </Grid>
        </Paper>
    )
}

export default ReviewRead