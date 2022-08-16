import React, { useState } from 'react'
import { useParams } from 'react-router-dom'
import { saveAs } from 'file-saver'

// Material UI component
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Chip from '@mui/material/Chip'
import CircularProgress from '@mui/material/CircularProgress'

// Material UI icon
import DownloadForOfflineIcon from '@mui/icons-material/DownloadForOffline'

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'

// url
import { URL_GET_DOWNLOAD_ANSWER } from '../utils/url'

const InstructorDownloadAnswer = ({ answerId, fileName }) => {

    const { courseId } = useParams()
    const apiPrivate = useApiPrivate()

    const [ downloading, setDownloading ] = useState(false)

    const handleClickDownload = async () => {
        setDownloading(true)
        const url = URL_GET_DOWNLOAD_ANSWER
            .replace('{courseId}', courseId)
            .replace('{answerId}', answerId)
        const response = await apiPrivate.get(url)
        if (response.status === 200) {
            saveAs(response.data, fileName)
        } else {
            alert('fail')
        }
        setDownloading(false)
    }

    return (
        <Grid container>
        {
            downloading ?
            <Grid item container direction='row' alignItems='center' spacing={1}>
                <Grid item>
                    <CircularProgress size={25} />
                </Grid>
                <Grid item>
                    <Typography>Loading...</Typography>
                </Grid>
            </Grid>
            :
            <Grid item xs={12}>
                <Chip
                    variant='outlined'
                    title='Download this answer'
                    label={fileName}
                    icon={<DownloadForOfflineIcon />}
                    onClick={handleClickDownload}
                />
            </Grid>
        }
        </Grid>
    )
}

export default InstructorDownloadAnswer