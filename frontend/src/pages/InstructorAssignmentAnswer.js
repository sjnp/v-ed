import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import InstructorMenu from '../components/InstructorMenu'
import LoadingCircle from '../components/LoadingCircle'
import TextNotAnyData from '../components/TextNotAnyData'
import AnswerInstructor from '../components/AnswerInstructor'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Breadcrumbs from '@mui/material/Breadcrumbs'
import Link from '@mui/material/Link'

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'

// url
import { URL_GET_ASSIGNMENTS_ANSWER_INSTRUCTOR } from '../utils/url'

const InstructorAssignmentAnswer = () => {

    const { courseId, chapterIndex, noIndex } = useParams()
    const navigate = useNavigate()
    const apiPrivate = useApiPrivate()

    const chapterNo = Number(chapterIndex) + 1

    const [ answers, setAnswers ] = useState([])
    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {
        const url = URL_GET_ASSIGNMENTS_ANSWER_INSTRUCTOR
            .replace('{courseId}', courseId)
            .replace('{chapterIndex}', chapterIndex)
            .replace('{noIndex}', noIndex)
        const response = await apiPrivate.get(url)
        if (response.status === 200) {
            setAnswers(response.data)
        } else {
            alert('Fail')
        }
        setLoading(false)
    }, [])

    const handleClickLinkBreadcrumbsAssignment = () => {
        navigate(`/instructor/course/${courseId}/assignment`)
    }

    const handleClickLinkBreadcrumbsChapter = () => {
        navigate(`/instructor/course/${courseId}/assignment/chapter/${chapterIndex}`)
    }

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <InstructorMenu active='assignment' />
                </Grid>
                <Grid item xs={9}>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={11}>
                            <Breadcrumbs>
                                <Link
                                    underline='hover'
                                    color='default'
                                    sx={{ cursor: 'pointer' }}
                                    onClick={handleClickLinkBreadcrumbsAssignment}
                                >
                                    Assignment
                                </Link>
                                <Link
                                    underline='hover'
                                    color='default'
                                    sx={{ cursor: 'pointer' }}
                                    onClick={handleClickLinkBreadcrumbsChapter}
                                >
                                    Chapter {chapterNo}
                                </Link>
                                <Typography color='black'>Answer</Typography>
                            </Breadcrumbs>
                        </Grid>
                    </Grid>
                    <Grid container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={11} pt={2}>
                        {
                            loading ?
                                <LoadingCircle loading={loading} centerY={true} />    
                                :
                                answers.length > 0 ?
                                    answers.map((answer, index) => (
                                        <AnswerInstructor key={index} answer={answer} />
                                    ))
                                    :
                                    <TextNotAnyData text='Not any answer.' />
                        }
                        </Grid>
                        <Grid item xs={1}></Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default InstructorAssignmentAnswer