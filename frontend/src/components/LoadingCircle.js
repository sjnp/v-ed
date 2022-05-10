import React from 'react'

// Material UI component
import CircularProgress from '@mui/material/CircularProgress';

const LoadingCircle = ({ loading, layoutLeft }) => {

    const style = {
        color: 'primary',
        position: 'absolute',
        top: '50%',
        left: `${layoutLeft}%` || '50%',
        mt: '-12px',
        ml: '-12px'
    }

    const size = 30

    return (
        <div>
            { loading && <CircularProgress size={size} sx={style} /> }
        </div>
    )
}

export default LoadingCircle