import React from 'react'
import { saveAs } from 'file-saver'

// Material UI component
import List from '@mui/material/List'
import ListItem from '@mui/material/ListItem'
import ListItemButton from '@mui/material/ListItemButton'
import ListItemIcon from '@mui/material/ListItemIcon'
import ListItemText from '@mui/material/ListItemText'
import Divider from '@mui/material/Divider'
import Typography from '@mui/material/Typography'

// icon
import DownloadIcon from '@mui/icons-material/Download'

const MaterialFileDownload = () => {

    const files = [
        {
            url: 'https://objectstorage.ap-singapore-1.oraclecloud.com/p/p5JCMP6Mqmwu5yTUybO4LLG9cPB5R8LcB9pexMLlLEtQJXfcgF4KiA6VYNQwcCem/n/axjskktj5xlm/b/bucket-20220411-1925/o/course_pic_17.jpeg',
            name: 'example1.jpeg'
        },
        {
            url: 'https://objectstorage.ap-singapore-1.oraclecloud.com/p/4zSsIDgy7IiaNcc-3CaqU5wJ6df9MPjOeDgpoxkh9rtwjUppe5qu4vXEU4VhRlZ3/n/axjskktj5xlm/b/bucket-20220411-1925/o/dummy.pdf',
            name: 'example2.pdf'
        },
        {
            url: 'https://objectstorage.ap-singapore-1.oraclecloud.com/p/9vby0hrcKpe_p7-GM9TxWzEvhD2I-qvR_WOsGIE0fv3mtv_n6zjeazHVIJ-wNfRM/n/axjskktj5xlm/b/bucket-20220411-1925/o/hello.py',
            name: 'example3.py'
        },
    ]

    return (
        <List>
            <Typography variant='subtitle1' sx={{ fontWeight: 'bold', mb: 1, mt: 1, textAlign: 'center' }}>
                Material Files
            </Typography>
            <Divider />
            {
                files.map((file, index) => (
                    <ListItem divider key={index}>
                        <ListItemText primary={file.name} />
                        <ListItemIcon>
                            <ListItemButton onClick={() => saveAs(file.url, file.name)}>
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