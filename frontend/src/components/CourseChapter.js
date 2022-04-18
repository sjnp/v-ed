import React from 'react'

// Material UI
import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import Typography from '@mui/material/Typography';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';

// icon
import OndemandVideoIcon from '@mui/icons-material/OndemandVideo';
import { Divider } from '@mui/material';

// component
import ChapterVideoList from './ChapterVideoList';

const CourseChapter = () => {

    const chapters = [
        {
            name: 'chapter 1',
            videos: [
                {
                    name: 'video 1',
                    link: 'student/course/video/1'
                },
                {
                    name: 'video 2',
                    link: 'student/course/video/2'
                },
            ]
        },
        {
            name: 'chapter 2',
            videos: [
                {
                    name: 'video 1',
                    link: 'student/course/video/1'
                },
                {
                    name: 'video 2',
                    link: 'student/course/video/2'
                },
                {
                    name: 'video 3',
                    link: 'student/course/video/3'
                },
            ]
        },
        {
            name: 'chapter 3',
            videos: [
                {
                    name: 'video 1',
                    link: 'student/course/video/1'
                },
                {
                    name: 'video 2',
                    link: 'student/course/video/2'
                },
                {
                    name: 'video 3',
                    link: 'student/course/video/3'
                },
                {
                    name: 'video 4',
                    link: 'student/course/video/4'
                },
            ]
        },
    ]
    
    return (
        <div>
        {
            chapters.map((chapter, index) => (
                <Accordion key={index}>
                    <AccordionSummary id="panel1a-header" expandIcon={<ExpandMoreIcon />}>
                        <Typography>{chapter.name}</Typography>
                    </AccordionSummary>
                    <AccordionDetails>
                        <ChapterVideoList videos={chapter.videos} />
                    </AccordionDetails>
                </Accordion>
            ))
        }
        </div>
    )
}

export default CourseChapter