import React from 'react'

// Material UI component
import Box from '@mui/material/Box'
import Accordion from '@mui/material/Accordion'
import AccordionSummary from '@mui/material/AccordionSummary'
import AccordionDetails from '@mui/material/AccordionDetails'
import Typography from '@mui/material/Typography'

// Material UI icon
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'

// component
import ChapterVideoList from './ChapterVideoList';

const BetaCourseContent = ({ chapters }) => {

    return (
        <Box>
        {
            chapters?.map((chapter, index) => (
                <Accordion key={index}>
                    <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                        <Typography>{`Chapter ${index + 1}: ${chapter.name}`}</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <ChapterVideoList videos={chapter.sections} />
                    </AccordionDetails>
                </Accordion>
            ))
        }
        </Box>
    )
}

export default BetaCourseContent