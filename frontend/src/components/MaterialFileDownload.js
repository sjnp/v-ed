import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'

// component
import DownloadFileItem from './DownloadFileItem'

// Material UI component
import List from '@mui/material/List'
import Divider from '@mui/material/Divider'
import Typography from '@mui/material/Typography'
import Box from '@mui/material/Box'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate';

// url
import { URL_GET_COURSE } from '../utils/url'

const MaterialFileDownload = () => {

    const { courseId, chapterIndex, sectionIndex } = useParams()

    const axiosPrivate = useAxiosPrivate()

    const [ handouts, setHandouts ] = useState([])

    useEffect(async () => {
        const url = URL_GET_COURSE.replace('{courseId}', courseId)
        const response = await apiPrivate.get(axiosPrivate, url)

        if (response.status === 200) {
            const result = response.data.content[chapterIndex].sections[sectionIndex].handouts
            setHandouts(result)
        }
    }, [])

    return (
        <Box>
            <Typography variant='subtitle1' fontWeight='bold' mb={1} mt={1} textAlign='center'>
                Material Files
            </Typography>
            <List sx={{ overflow: 'auto', maxHeight: 300 }}>
            <Divider />
            {
                handouts?.map((handout, index) => (
                    <DownloadFileItem key={index} fileName={handout.handoutUri} handoutIndex={index} />
                ))
            }
            </List>
        </Box>
    )
}

export default MaterialFileDownload