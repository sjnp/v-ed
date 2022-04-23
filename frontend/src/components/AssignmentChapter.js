import React from 'react'

// Material UI component
import Paper from '@mui/material/Paper'
import Typography from '@mui/material/Typography'

const AssignmentChapter = ({ chapterNo, onClick }) => {

    return (
        <Paper sx={{ padding: 2, width: '80%', margin: 2, cursor: 'pointer' }} onClick={onClick}>
            <Typography variant='h6'>
                Assignment chapter {chapterNo}
            </Typography>
        </Paper>
    )
}

export default AssignmentChapter