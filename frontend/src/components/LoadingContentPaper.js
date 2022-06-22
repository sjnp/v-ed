import React from 'react'

// Material UI component
import Grid from '@mui/material/Grid'
import Paper from '@mui/material/Paper'
import Skeleton from '@mui/material/Skeleton'

const LoadingContentPaper = () => {

    return (
        <Grid container mb={5}>
            <Grid item xs={12} mb={1} pl={1}>
                <Skeleton variant='text' width={100} />
            </Grid>
            <Grid item xs={12}>
                <Paper sx={{ p: 3 }}>
                    <Skeleton variant='text' width='70%' />
                    <Skeleton variant='text' width='50%' />
                </Paper>
            </Grid>
        </Grid>
    )
}

export default LoadingContentPaper