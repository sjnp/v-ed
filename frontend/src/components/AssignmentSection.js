import React from 'react'

// Material UI
import Box from '@mui/material/Box'
import Paper from '@mui/material/Paper'
import Typography from '@mui/material/Typography'

const AssingmentSection = ({ sectionNo, sectionName, onClick }) => {

    // const handleClick = () => alert(`Section ${sectionNo}: ${sectionName}`)

    return (
        <Paper sx={{ padding: 2, width: '80%', margin: 2, cursor: 'pointer' }} onClick={onClick}>
            <Typography variant='h6'>
                Assignment section {sectionNo}: {sectionName}
            </Typography>
        </Paper>
    )
}

export default AssingmentSection