import React from 'react'

// Material UI component
import Grid from '@mui/material/Grid'
import Skeleton from '@mui/material/Skeleton'

const LoadingBuyCourseOverview = () => {

    return (
        <Grid container p={3}>
        <Grid item xs={2}>
          <Skeleton variant="circular" width={40} height={40} />
        </Grid>
        <Grid item xs={10}>
            <Grid item xs={10}> <Skeleton variant="text" /> </Grid>
            <Grid item xs={8}> <Skeleton variant="text" /> </Grid>
        </Grid>
        <Grid item xs={12} pt={5}>
          <Grid container direction="row" alignItems="center" justifyContent="center">
          {
            Array(5).fill().map((element, index) => (
              <Skeleton key={index} variant="circular" width={30} height={30} sx={{ m: 0.5 }} />
            ))
          } 
          </Grid>
        </Grid>
        <Grid item xs={12} pt={5}>
          <Grid container direction="column" alignItems="center" justifyContent="center">
            <Skeleton variant='rectangular' width={70} height={30}  />
            <Skeleton variant='rectangular' width={100} height={30} sx={{ mt: 3, mb:2 }}/>
          </Grid>
        </Grid>
      </Grid>
    )
}

export default LoadingBuyCourseOverview