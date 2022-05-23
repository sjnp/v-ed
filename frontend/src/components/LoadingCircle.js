import React from 'react'

// Material UI component
import CircularProgress from '@mui/material/CircularProgress'
import Grid from '@mui/material/Grid'

const LoadingCircle = ({ loading, centerY }) => {

    const size = 30

    const paddingTop = centerY ? `${window.screen.availHeight / 4}px` : null

    return (
        <Grid container direction='column' alignItems='center' justifyContent='center' pt={paddingTop}>
            { loading && <CircularProgress size={size} /> }
        </Grid>
    )
}

export default LoadingCircle