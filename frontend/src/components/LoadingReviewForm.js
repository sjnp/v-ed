import React from 'react'

// Material UI component
import Paper from '@mui/material/Paper'
import Grid from '@mui/material/Grid'
import Skeleton from '@mui/material/Skeleton'

const LoadingReviewForm = () => {

    return (
        <Paper sx={{ mt: 3, p: 3 }}>
            <Grid container>
            {
                Array(5).fill().map((element, index) => (
                    <Skeleton key={index} variant="circular" width={20} height={20} sx={{ m: 0.5 }} />
                ))
            }
            </Grid>
            <Grid container pt={2}>
                <Skeleton variant='rectangular'  width='100%' height={35} />
            </Grid>
            <Grid container pt={2}>
                <Grid item xs={2} pl={2}>
                    <Skeleton variant='rectangular' width={40} height={15} />
                </Grid>
                <Grid item xs={8} pt={2}>
                    <Skeleton variant='rectangular' width={140} height={35} sx={{ m: 'auto' }} />
                </Grid>
                <Grid item xs={2}></Grid>
            </Grid>
        </Paper>
    )
}

export default LoadingReviewForm