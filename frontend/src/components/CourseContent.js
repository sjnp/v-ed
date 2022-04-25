import React from 'react'
import { useSelector } from 'react-redux'

// Material UI component
import Accordion from '@mui/material/Accordion'
import AccordionSummary from '@mui/material/AccordionSummary'
import AccordionDetails from '@mui/material/AccordionDetails'
import Typography from '@mui/material/Typography'
import Box from '@mui/material/Box'

// Material UI icon
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'

// component
import ChapterVideoList from './ChapterVideoList';

const CourseContent = () => {

    const content = useSelector(state => state.studentCourse.value.content)

    const chapters = content
    
    return (
        <Box>
            <Typography variant='h6' sx={{ mb: 2 }}>
                Content
            </Typography>
            <Box>
            {
                chapters?.map((chapter, index) => (
                    <Accordion key={index}>
                        <AccordionSummary id="panel1a-header" expandIcon={<ExpandMoreIcon />}>
                            <Typography>{`Chapter ${index + 1}: ${chapter.name}`}</Typography>
                        </AccordionSummary>
                        <AccordionDetails>
                            <ChapterVideoList videos={chapter.sections} />
                        </AccordionDetails>
                    </Accordion>
                ))
            }
            </Box>
        </Box>
    )
}

export default CourseContent