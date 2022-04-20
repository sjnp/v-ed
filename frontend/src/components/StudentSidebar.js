import React, { useState } from 'react'

// Material UI
import Paper from '@mui/material/Paper'
import List from '@mui/material/List'
import ListItemButton from '@mui/material/ListItemButton'
import ListItemIcon from '@mui/material/ListItemIcon'
import ListItemText from '@mui/material/ListItemText'

// Icon
import SchoolIcon from '@mui/icons-material/School';
import NotesIcon from '@mui/icons-material/Notes'
import AssignmentIcon from '@mui/icons-material/Assignment';
import QuestionAnswerIcon from '@mui/icons-material/QuestionAnswer';
import StarsIcon from '@mui/icons-material/Stars';
import PersonPinIcon from '@mui/icons-material/PersonPin';

const StudentSidebar = ({ onClickSidebar }) => {

    const [ selectSidebar, setSelectSidebar ] = useState('Content')
    
    const handleClickSidebar = (name) => {
        setSelectSidebar(name)
        onClickSidebar(name)
    }

    const listItem = [
        {
            text: 'Content',
            icon: <SchoolIcon />
        },
        {
            text: 'Assignment',
            icon: <AssignmentIcon />
        },
        {
            text: 'Question board',
            icon: <QuestionAnswerIcon />
        },
        {
            text: 'Review',
            icon: <StarsIcon />
        },
        {
            text: 'Instructor',
            icon: <PersonPinIcon />
        },
        {
            text: 'About course',
            icon: <NotesIcon />
        }
    ]

    return (
        <Paper elevation={4} sx={{ width: 240, flexShrink: 0, borderRadius: 1, position: 'fixed' }}>
            <List>
            {
                listItem.map((item, index) => (
                    <ListItemButton
                        key={index}
                        selected={selectSidebar === item.text}
                        onClick={() => handleClickSidebar(item.text)}
                    >
                        <ListItemIcon>{item.icon}</ListItemIcon>
                        <ListItemText primary={item.text} />
                    </ListItemButton>
                ))
            }
            </List>
        </Paper>
    )
}

export default StudentSidebar