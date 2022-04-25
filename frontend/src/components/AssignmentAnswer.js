import React from 'react'

// Material UI component
import Box from '@mui/material/Box'
import Paper from '@mui/material/Paper'
import Typography from '@mui/material/Typography'
import Divider from '@mui/material/Divider'
import IconButton from '@mui/material/IconButton'
import PhotoCamera from '@mui/icons-material/PhotoCamera';
import styled from '@mui/material/styles/styled';

// Material UI icon
import UploadFileIcon from '@mui/icons-material/UploadFile';

const AssignmentAnswer = ({ no, question, commentInstructor }) => {

    const Input = styled('input')({
        display: 'none'
    })

    return (
        <Paper sx={{ padding: 2, width: '80%', margin: 2 }}>
            <Typography variant='h6'>
                {no}. {question}
            </Typography>
            <Divider sx={{ marginTop: 2, marginBottom: 2 }} />
            <Box sx={{ display: 'inline' }}>
                <label htmlFor="icon-button-file">
                    <Input accept="image/*" id="icon-button-file" type="file" />
                    <IconButton aria-label="upload picture" component="span">
                        <UploadFileIcon />
                    </IconButton>
                </label>
                <Typography variant='caption'>
                    Upload answer file here.
                </Typography>
            </Box>
            <Box sx={{ display: commentInstructor ? 'block' : 'none' }}>
                <Box sx={{ background: '#f5f5f5', borderRadius: 1, padding: 2, marginTop: 1 }}>
                    {commentInstructor}
                </Box>
            </Box>
        </Paper>
    )
}

export default AssignmentAnswer