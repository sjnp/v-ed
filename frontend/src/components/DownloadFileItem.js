import React, { useState } from 'react'
import { saveAs } from 'file-saver'

// Material UI component
import ListItem from '@mui/material/ListItem'
import ListItemButton from '@mui/material/ListItemButton'
import ListItemIcon from '@mui/material/ListItemIcon'
import ListItemText from '@mui/material/ListItemText'

// Material UI icon
import DownloadIcon from '@mui/icons-material/Download'
import FileDownloadDoneIcon from '@mui/icons-material/FileDownloadDone';

const DownloadFileItem = ({ url, name }) => {

    const [ iconDownload, setIconDownload ] = useState(<DownloadIcon />)

    const [ isDownload, setIsDownload ] = useState(false)

    const handleDownload = async () => {
        // todo : call api get preauthenticate
        await saveAs(url, name)
        setIconDownload(<FileDownloadDoneIcon />)
        setIsDownload(true)
    }

    return (
        <ListItem divider>
            <ListItemText primary={name} />
            <ListItemIcon>
                <ListItemButton disabled={isDownload} onClick={handleDownload}>
                    {iconDownload}
                </ListItemButton>
            </ListItemIcon>
        </ListItem>
    )
}

export default DownloadFileItem