import React, { useState } from 'react'

// component
import QuestionCreate from './QuestionCreate';

// Material UI
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import Fab from '@mui/material/Fab';
import Grid from '@mui/material/Grid';
import IconButton from '@mui/material/IconButton'

// icon
import AddIcon from '@mui/icons-material/Add';
import ArrowBackIcon from '@mui/icons-material/ArrowBack'


const CourseQuestion = () => {

    const [ hideAddButton, setHideAddButton ] = useState(false)
    const [ hideArrowBackIcon, setHideArrowBackIcon ] = useState(true)



    return (
        <Box>
            <Grid container>
                <Grid item xs={2}>
                    <Typography variant='h6'>
                        Question board
                    </Typography>
                </Grid>
                <Grid item xs={8}></Grid>
                <Grid item xs={2}>
                    <Box hidden={hideAddButton}>
                        <Fab size="small" color="primary" onClick={() => alert('Create question board')}>
                            <AddIcon />
                        </Fab>
                    </Box>
                </Grid>
            </Grid>
            <Box hidden={hideArrowBackIcon}>
                <IconButton onClick={() => alert('Back question board')}>
                    <ArrowBackIcon />
                </IconButton>
            </Box>
            <Box>
                <QuestionCreate />
            </Box>
        </Box>
    )
}

export default CourseQuestion