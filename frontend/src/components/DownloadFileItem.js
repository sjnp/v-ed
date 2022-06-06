import React, { useState } from 'react'
import { useParams } from 'react-router-dom'
import { saveAs } from 'file-saver'

// Material UI component
import ListItem from '@mui/material/ListItem'
import ListItemIcon from '@mui/material/ListItemIcon'
import ListItemText from '@mui/material/ListItemText'
import Typography from '@mui/material/Typography'
import LoadingButton from '@mui/lab/LoadingButton'

// Material UI icon
import DownloadIcon from '@mui/icons-material/Download'
import FileDownloadDoneIcon from '@mui/icons-material/FileDownloadDone'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_HANDOUT } from '../utils/url'

const DownloadFileItem = ({ fileName, handoutIndex }) => {

    const { courseId, chapterIndex, sectionIndex } = useParams()

    const axiosPrivate = useAxiosPrivate()

    const [ downloadIcon, setDownloadIcon ] = useState(<DownloadIcon titleAccess='Download now' />)
    const [ downloading, setDownloading ] = useState(false)

    // ex. course_handout_40_c0_s0_example.jpg -> example.jpg
    const name = fileName?.replace(`course_handout_${courseId}_c${chapterIndex}_s${sectionIndex}_`, '')

    const handleDownloadFile = async () => {
        setDownloading(true)

        const url = URL_GET_HANDOUT
            .replace('{courseId}', courseId)
            .replace('{chapterIndex}', chapterIndex)
            .replace('{sectionIndex}', sectionIndex)
            .replace('{handoutIndex}', handoutIndex)
        
        const response = await apiPrivate.get(axiosPrivate, url)
        if (response.status === 200) {
            saveAs(response.data, name)
            setDownloadIcon(<FileDownloadDoneIcon titleAccess='Downloaded' />)
            setDownloading(false)
        } else {
            setDownloading(false)
            alert('Download fail')
        }
    }
    
    return (
        <ListItem divider>
            <ListItemText primary={<Typography noWrap>{name}</Typography>} title={name} />
            <ListItemIcon>
                <LoadingButton loading={downloading} color='inherit' onClick={handleDownloadFile}>
                    {downloadIcon}
                </LoadingButton>
            </ListItemIcon>
        </ListItem>
    )
}

export default DownloadFileItem