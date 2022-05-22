import React from 'react'

// Material UI component
import CircularProgress from '@mui/material/CircularProgress'
import Grid from '@mui/material/Grid'

const LoadingCircle = ({ loading, layoutLeft }) => {

    const size = 30

    return (

        <Grid container direction="column" alignItems="center" justifyContent="center">
            { loading && <CircularProgress size={size} /> }
        </Grid>
    )
}

export default LoadingCircle