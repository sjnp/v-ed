import React from "react"

// component
import CourseCard from "./CourseCard"

// Materail UI
import Box from '@mui/material/Box'
import Card from '@mui/material/Card'
import Button from '@mui/material/Button'
import Typography from '@mui/material/Typography'

const CaroueselCourse = ({ data, labelCorousel, pathTo }) => {

    const handleClickReadMore = () => alert(`Read more ${labelCorousel}`)

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
                data?.map((element, index) => (
                    <CourseCard 
                        key={index}
                        image={`https://picsum.photos/200/300?random=${index}`} // hard code, Fix after.
                        courseName={element.courseName}
                        instructorName={element.instructorName}
                        rating={element.rating}
                        reviewTotal={element.reviewTotal}
                        pathOnClick={pathTo}
                    />
                ))
            }
            </Box>
        </Card>
    )
}

export default CaroueselCourse