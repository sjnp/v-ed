import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import StudentMenu from '../components/StudentMenu'
import LoadingContentPaper from '../components/LoadingContentPaper'
import ContentPaper from '../components/ContentPaper'
import stringToColor from '../components/stringToColor'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Breadcrumbs from '@mui/material/Breadcrumbs'
import Avatar from '@mui/material/Avatar'
import Skeleton from '@mui/material/Skeleton'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_ABOUT_COURSE } from "../utils/url"

const StudentAboutCourse = () => {

    const { courseId } = useParams()
    const axiosPrivate = useAxiosPrivate()

    const [ profilePictureUrl, setProfilePictureUrl ] = useState(null)
    const [ instructorFirstName, setInstructorFirstName ] = useState(null)
    const [ instructorLastName, setInstructorLastName ] = useState(null)
    const [ biography, setBiography ] = useState(null)
    const [ occupation, setOccupation ] = useState(null)
    const [ overview, setOverview ] = useState(null)
    const [ requirement, setRequirement ] = useState(null)

    const NAElement = <Typography variant='caption' color='gray'>N/A</Typography>
    const imageSize = 120

    useEffect(async () => {
        const url = URL_GET_ABOUT_COURSE.replace('{courseId}', courseId)
        const response = await apiPrivate.get(axiosPrivate, url)

        if (response.status === 200) {
            setProfilePictureUrl(response.data.profilePictureUrl || '')
            setInstructorFirstName(response.data.instructorFirstName)
            setInstructorLastName(response.data.instructorLastName)
            setBiography(response.data.biography || NAElement)
            setOccupation(response.data.occupation || NAElement)
            setOverview(response.data.overview)
            setRequirement(response.data.requirement)
        } else {
            alert('Fail')
        }
    }, [])

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <StudentMenu active='about course' />
                </Grid>
                <Grid item xs={9}>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={11}>
                            <Breadcrumbs>
                                <Typography color='black'>About course</Typography>
                            </Breadcrumbs>
                        </Grid>
                    </Grid>
                    <Grid container pt={3}>
                        <Grid item xs={2}></Grid>
                        <Grid item xs={8}>
                        {
                            profilePictureUrl === null ?
                            <Skeleton variant='circular' width={imageSize} height={imageSize} sx={{ m: 'auto' }} />
                            :
                            <Avatar
                                alt={instructorFirstName}
                                src={profilePictureUrl || "/static/images/avatar/1.jpg"} 
                                sx={{ 
                                    bgcolor: stringToColor(instructorFirstName), 
                                    width: imageSize, 
                                    height: imageSize,
                                    margin: 'auto',
                                    marginBottom: 3
                                }}
                            />
                        }
                        {
                            instructorFirstName === null && instructorLastName === null ?
                            <LoadingContentPaper />
                            :
                            <ContentPaper label='Instructor name' content={`${instructorFirstName} ${instructorLastName}`} />
                        }
                        {
                            biography === null ?
                            <LoadingContentPaper />
                            :
                            <ContentPaper label='Biography' content={biography} />
                        }
                        {
                            occupation === null ?
                            <LoadingContentPaper />
                            :
                            <ContentPaper label='Occupation' content={occupation} />
                        }
                        {
                            overview === null ?
                            <LoadingContentPaper />
                            :
                            <ContentPaper label='Overview' content={overview} />
                        }
                        {
                            requirement === null ?
                            <LoadingContentPaper />
                            :
                            <ContentPaper label='Requirement' content={requirement} />
                        }
                        </Grid>
                        <Grid item xs={2}></Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default StudentAboutCourse