import React from 'react'

// Material UI component
import CircularProgress from '@mui/material/CircularProgress'
import Grid from '@mui/material/Grid'

const LoadingCircle = ({ loading, centerY }) => {

    const size = 30
    
    const height = centerY ? '50vh' : ''

    return (

        <Grid container direction="column" alignItems="center" justifyContent="center" height={height}>
            { loading && <CircularProgress size={size} /> }
        </Grid>
    )
}

export default LoadingCircle