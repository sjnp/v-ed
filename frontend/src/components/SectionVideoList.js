import React from 'react'
import { useNavigate } from 'react-router-dom'

// Material UI
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Divider from '@mui/material/Divider';

// icon
import OndemandVideoIcon from '@mui/icons-material/OndemandVideo';

const SectionVideoList = ({ videos }) => {

    const navigate = useNavigate()
    const handleClickVideoList = (link) => {
        const links = link.split('/')
        navigate(`/student/course/video/${links[links.length-1]}`)
    } 
    
    return (
        <List>
            <Divider />
            {
                videos.map((video, index) => (
                    <div key={index}>
                        <ListItemButton onClick={() => handleClickVideoList(video.link)}>
                            <ListItemIcon>
                                <OndemandVideoIcon />
                            </ListItemIcon>
                            <ListItemText primary={video.name} />
                        </ListItemButton>
                        <Divider />
                    </div>
                ))
            }
        </List>
    )
}

export default SectionVideoList