import React from "react"
import { useNavigate } from "react-router-dom"

// component
import CourseCard from "./CourseCard"

// Materail UI
import Box from '@mui/material/Box'
import Grid from '@mui/material/Grid'
import Card from '@mui/material/Card'
import Button from '@mui/material/Button'
import Typography from '@mui/material/Typography'
import {useDispatch} from "react-redux";

import {reset} from '../features/searchSlice';

const CaroueselCourse = ({ data, label }) => {

    const navigate = useNavigate()
    const dispatch = useDispatch()

    const handleClickReadMore = () => {
        if (label === 'My Course') {
            navigate('/student/course')
        } else {
            dispatch(reset());
            navigate(`/search?category=${label}`)
        }
    }

    const pathTo = label === 'My Course' ? '/student/course/{courseId}/content' : '/overview/course/{courseId}'

    return (
        <Card sx={{ backgroundColor: '#f5f5f5', mt: 2, p: 1 }}>
            <Grid container spacing={2} sx={{ pl: 5, pr: 5, justifyContent: 'center' }}>
                <Grid item xs={12}>
                    <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                        <Typography variant="h6" sx={{ pt: 1 }}>{label}</Typography>
                        <Button varient="text" sx={{ mt: 1 }} onClick={handleClickReadMore}>
                            <Typography variant="caption">Read more</Typography>
                        </Button>
                    </Box>
                </Grid>
                {
                    data?.map((element, index) => (
                        <Grid item xs={3} sx={{ pb: 2 }} key={index} >
                            <CourseCard
                                image={element.pictureURL}
                                courseName={element.courseName}
                                instructorName={element.instructorName}
                                rating={element.rating}
                                reviewCount={element.reviewCount}
                                pathOnClick={pathTo?.replace('{courseId}', element.courseId)}
                                price={label === 'My Course' ? '' : element.price}
                            />
                        </Grid>
                    ))
                }
            </Grid>
        </Card>
    )
}

export default CaroueselCourse