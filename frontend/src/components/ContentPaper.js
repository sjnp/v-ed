import React from 'react'

import Typography from '@mui/material/Typography'
import Box from '@mui/material/Box'
import Paper from '@mui/material/Paper'

const ContentPaper = ({ label, content }) => {

    return (
        <Box sx={{ mb: 6 }}>
            <Typography variant='body1' fontWeight='bold' sx={{ pl: 1 }}>{label}</Typography>
            <Paper sx={{ mt: 1, p: 3 }}>
                {content}
            </Paper>
        </Box>
    )
}

export default ContentPaper