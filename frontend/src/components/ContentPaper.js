import React from 'react'

// Material UI component
import Grid from '@mui/material/Grid' 
import Typography from '@mui/material/Typography'
import Paper from '@mui/material/Paper'

const ContentPaper = ({ label, content }) => {

    return (
        <Grid container mb={5}>
            <Grid item xs={12} mb={1} pl={1}>
                <Typography variant='body1' fontWeight='bold' >{label}</Typography>
            </Grid>
            <Grid item xs={12}>
                <Paper sx={{ p: 3 }}>
                    {content}
                </Paper>
            </Grid>
        </Grid>
    )
}

export default ContentPaper