import React from 'react'

// Material UI component
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'

const TextNotAnyData = ({ text }) => {

    const minHeight = window.innerHeight / 8 + 'vh'

    return (
        <Box display='flex' justifyContent='center' alignItems='center' minHeight={minHeight}>
            <Typography variant='subtitle1' color='gray'>{text}</Typography>
        </Box>
    )
}

export default TextNotAnyData