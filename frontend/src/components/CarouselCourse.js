import React from "react";
import { useNavigate } from "react-router-dom";

import CourseCard from "./CourseCard"

// Materail UI
import Box from '@mui/material/Box'
import Card from '@mui/material/Card'
import Button from '@mui/material/Button'
import Typography from '@mui/material/Typography'

const CaroueselCourse = (props) => {

    const navigate = useNavigate();

    const { labelCorousel, startIndex } = props

    const handleClickReadMore = () => alert(`Read more ${labelCorousel}`)

    let index = startIndex
    const data = [
        {
            image: `https://picsum.photos/200/300?random=${index++}`,
            courseName: `Java programming ${index}`,
            instructorName: `pradinan benjanavee ${index}`,
            rating: 4.7,
            reviewTotal: 125,
            pathOnClick: '/overview'
        },
        {
            image: `https://picsum.photos/200/300?random=${index++}`,
            courseName: `Java programming ${index}`,
            instructorName: `pradinan benjanavee ${index}`,
            rating: 4.7,
            reviewTotal: 125,
            pathOnClick: '/overview'
        },
        {
            image: `https://picsum.photos/200/300?random=${index++}`,
            courseName: `Java programming ${index}`,
            instructorName: `pradinan benjanavee ${index}`,
            rating: 4.7,
            reviewTotal: 125,
            pathOnClick: '/overview'
        },
        {
            image: `https://picsum.photos/200/300?random=${index++}`,
            courseName: `Java programming ${index}`,
            instructorName: `pradinan benjanavee ${index}`,
            rating: 4.7,
            reviewTotal: 125,
            pathOnClick: '/overview'
        },
        {
            image: `https://picsum.photos/200/300?random=${index++}`,
            courseName: `Java programming ${index}`,
            instructorName: `pradinan benjanavee ${index}`,
            rating: 4.7,
            reviewTotal: 125,
            pathOnClick: '/overview'
        },    
    ]

    return (
        <Card sx={{ backgroundColor: '#f5f5f5', marginBottom: 2 }}>
            <Box sx={{ display: 'flex', justifyContent: 'space-between'}}>
                <Typography variant="h6" sx={{ marginTop: 1, marginLeft: 3 }}>
                    {labelCorousel}
                </Typography>
                <Button varient="text" sx={{ marginRight: 2, marginTop: 1 }} onClick={handleClickReadMore}>
                    <Typography variant="caption">
                        Read more
                    </Typography>        
                </Button>
            </Box>
            <Box sx={{ display: 'flex', justifyContent: 'space-evenly', m: 1, p: 1, marginTop: 0 }}>
            { 
                data.map((element, index) => (
                    <CourseCard 
                        key={index}
                        image={element.image}
                        courseName={element.courseName}
                        instructorName={element.instructorName}
                        rating={element.rating}
                        reviewTotal={element.reviewTotal}
                        pathOnClick={element.pathOnClick}
                    />
                ))
            }
            </Box>
        </Card>
    )
}

export default CaroueselCourse