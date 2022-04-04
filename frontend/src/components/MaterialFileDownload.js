import React, { useState } from 'react'

// Material UI
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Divider from '@mui/material/Divider';

// icon
import DownloadIcon from '@mui/icons-material/Download';

const MaterialFileDownload = () => {

    const files = [
        {
            name: 'first.js'
        },
        {
            name: 'second.js'
        },
        {
            name: 'third.js'
        },
    ]

    return (
        <List>
            <div style={{ textAlign: 'center', margin: 10, fontWeight: 'bold' }}>
                Material File
            </div>
            <Divider />
            {
                files.map((file, index) => (
                    <ListItem divider key={index}>
                        <ListItemText primary={file.name} />
                        <ListItemIcon>
                            <ListItemButton onClick={() => alert('Download ' + file.name)}>
                                <DownloadIcon />
                            </ListItemButton>
                        </ListItemIcon>
                    </ListItem>
                ))
            }
        </List>
    )
}

export default MaterialFileDownload