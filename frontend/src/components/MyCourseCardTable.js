import React from 'react'

// component
import CourseCard from './CourseCard'

// Materail UI component
import Grid from '@mui/material/Grid'

const MyCourseCardTable = ({ data }) => {

    const side = 1.5
    const size = 3

    const getCourseCardElement = (data) => {
        return (
            <Grid item xs={size} key={++key}>
                <CourseCard
                    key={key}
                    image={data.pictureURL}
                    courseName={data.courseName}
                    instructorName={data.instructorName}
                    rating={data.rating}
                    reviewCount={data.reviewCount}
                    pathOnClick={'/student/course/' + data.courseId + '/content'}
                />
            </Grid>
        )
    }

    let key = 0
    let elements = []
    for (let i = 0; i < data?.length; i += 3) {

        elements.push(<Grid item xs={side} key={++key}></Grid>)
        
        for (let n = i; n < i + 3; ++n) {
        
            if ( n < data.length) {
                elements.push(getCourseCardElement(data[n]))   
            } else {
                elements.push(<Grid item xs={size} key={++key}></Grid>)
            }

        }
        
        elements.push(<Grid item xs={side} key={++key}></Grid>)
    }

    return (
        <Grid container spacing={2}>
        {
            elements?.map(e => e)
        }
        </Grid>
    )
}

export default MyCourseCardTable