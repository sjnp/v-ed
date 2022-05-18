import React from 'react'

// Materail UI component
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'

const OverviewTabOverview = ({ data }) => {
    return (
        <Box minHeight={500} paddingTop={5} paddingLeft={20} paddingRight={20}>
            <Typography variant='subtitle1'>{data}</Typography>
        </Box>
    )
}

export default OverviewTabOverview