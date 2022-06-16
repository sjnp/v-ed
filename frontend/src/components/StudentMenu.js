import React from 'react'
import { useParams, useNavigate } from 'react-router-dom'

// Material UI component
import Paper from '@mui/material/Paper'
import List from '@mui/material/List'
import ListItemButton from '@mui/material/ListItemButton'
import ListItemIcon from '@mui/material/ListItemIcon'
import ListItemText from '@mui/material/ListItemText'
import Box from '@mui/material/Box'

// Material UI icon
import SchoolIcon from '@mui/icons-material/School'
import NotesIcon from '@mui/icons-material/Notes'
import AssignmentIcon from '@mui/icons-material/Assignment'
import QuestionAnswerIcon from '@mui/icons-material/QuestionAnswer'
import StarsIcon from '@mui/icons-material/Stars'
import PersonPinIcon from '@mui/icons-material/PersonPin'

const StudentMenu = ({ active }) => {

    const { courseId } = useParams()

    const navigate = useNavigate()

    const items = [
        {
            text: 'Content',
            icon: <SchoolIcon />,
            path: `/student/course/${courseId}/content`
        },
        {
            text: 'Assignment',
            icon: <AssignmentIcon />,
            path: `/student/course/${courseId}/assignment`
        },
        {
            text: 'Post',
            icon: <QuestionAnswerIcon />,
            path: `/student/course/${courseId}/post`
        },
        {
            text: 'Review',
            icon: <StarsIcon />,
            path: `/student/course/${courseId}/review`
        },
        {
            text: 'Instructor',
            icon: <PersonPinIcon />,
            path: `/student/course/${courseId}/instructor`
        },
        {
            text: 'About course',
            icon: <NotesIcon />,
            path: `/student/course/${courseId}/about-course`
        }
    ]

    return (
        <Box display="flex" justifyContent="center">
            <Paper elevation={4} sx={{ width: 240, flexShrink: 0, borderRadius: 1, position: 'fixed' }}>
                <List>
                {
                    items.map((item, index) => (
                        <ListItemButton
                            key={index}
                            selected={active === item.text.toLowerCase()}
                            onClick={() => navigate(item.path)}
                        >
                            <ListItemIcon>{item.icon}</ListItemIcon>
                            <ListItemText primary={item.text} />
                        </ListItemButton>
                    ))
                }
                </List>
            </Paper>
        </Box>
    )
}

export default StudentMenu