import React from 'react'
import { useNavigate } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'

// Material UI component
import List from '@mui/material/List'
import ListItemButton from '@mui/material/ListItemButton'
import ListItemIcon from '@mui/material/ListItemIcon'
import ListItemText from '@mui/material/ListItemText'
import Divider from '@mui/material/Divider'

// icon
import OndemandVideoIcon from '@mui/icons-material/OndemandVideo'

// feature slice
import { setVideo } from '../features/videoCourseSlice'

const ChapterVideoList = ({ videos }) => {

    const navigate = useNavigate()

    const dispatch = useDispatch()

    const courseId = useSelector(state => state.studentCourse.value.courseId)

    const handleClickVideoList = (courseId, videoUri) => {
        dispatch( setVideo({ 
            courseId: courseId, 
            videoUri: videoUri 
        }))
        navigate(`/student/course/video/${courseId}`)
    } 
    
    return (
        <List>
            <Divider />
            {
                videos.map((video, index) => (
                    <div key={index}>
                        <ListItemButton onClick={() => handleClickVideoList(courseId, video.videoUri)}>
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