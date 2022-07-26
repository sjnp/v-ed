import React from 'react'
import { useNavigate } from 'react-router-dom'

// Material UI component
import Paper from '@mui/material/Paper'
import List from '@mui/material/List'
import ListItemButton from '@mui/material/ListItemButton'
import ListItemIcon from '@mui/material/ListItemIcon'
import ListItemText from '@mui/material/ListItemText'
import Box from '@mui/material/Box'

// Material UI icon
import PersonIcon from '@mui/icons-material/Person'
import SchoolIcon from '@mui/icons-material/School'

const AccountManageMenu = ({ active }) => {

    const navigate = useNavigate()

    if (!active) {
        console.error('AccountManageMenu required active props')
    }

    const items = [
        {
            text: 'Profile',
            icon: <PersonIcon />,
            path: `/account-manage/profile`
        },
        {
            text: 'Instructor',
            icon: <SchoolIcon />,
            path: `/account-manage/instructor`
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

export default AccountManageMenu