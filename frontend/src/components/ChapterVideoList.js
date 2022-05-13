import React from 'react'
import { useNavigate } from 'react-router-dom'
import { useParams } from 'react-router-dom'

// Material UI component
import List from '@mui/material/List'
import ListItemButton from '@mui/material/ListItemButton'
import ListItemIcon from '@mui/material/ListItemIcon'
import ListItemText from '@mui/material/ListItemText'
import Divider from '@mui/material/Divider'

//  Material UI icon
import OndemandVideoIcon from '@mui/icons-material/OndemandVideo'

const ChapterVideoList = ({ videos }) => {

    const { courseId } = useParams()

    const navigate = useNavigate()

    const handleClickVideoList = (courseId, video) => {

        const [ , , , chapter, sectionAndFileType ] = video.videoUri.split('_')
        const [ section, ] = sectionAndFileType.split('.')

        const chapterNo = chapter.replace('c', '')
        const sectionNo = section.replace('s', '')

        navigate(`/student/course/${courseId}/video/c/${chapterNo}/s/${sectionNo}`)
    } 
    
    return (
        <List>
            <Divider />
            {
                videos.map((video, index) => (
                    <div key={index}>
                        <ListItemButton onClick={() => handleClickVideoList(courseId, video)}>
                            <ListItemIcon>
                                <OndemandVideoIcon />
                            </ListItemIcon>
                            <ListItemText primary={`Chapter ${index + 1} : ${video.name}`} />
                        </ListItemButton>
                        <Divider />
                    </div>
                ))
            }
        </List>
    )
}

export default ChapterVideoList