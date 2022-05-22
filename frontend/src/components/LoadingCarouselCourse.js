import React from 'react'

// Material UI component
import Paper from "@mui/material/Paper"
import Grid from "@mui/material/Grid"
import Skeleton from "@mui/material/Skeleton"

const LoadingCarouselCourse = () => {

    const widthLabel = 170
    const heightLabel = 30

    const widthCard = 250
    const heightCard = 260

    return (
        <Paper sx={{ mt: 5 }}>
            <Grid container padding={3} rowSpacing={2} columnSpacing={4}>
                <Grid item xs={2}>
                    <Skeleton variant='rectangular' width={widthLabel} height={heightLabel} />
                </Grid>
                <Grid item xs={9}></Grid>
                <Grid item xs={1}>
                    <Skeleton variant='text' />
                </Grid>
                <Grid item xs={3}>
                    <Skeleton variant='rectangular' width={widthCard} height={heightCard} />
                </Grid>
                <Grid item xs={3}>
                    <Skeleton variant='rectangular' width={widthCard} height={heightCard} />
                </Grid>
                <Grid item xs={3}>
                    <Skeleton variant='rectangular' width={widthCard} height={heightCard} />
                </Grid>
                <Grid item xs={3}>
                    <Skeleton variant='rectangular' width={widthCard} height={heightCard} />
                </Grid>
            </Grid>
        </Paper>
    )
}

export default LoadingCarouselCourse