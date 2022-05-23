import React from 'react'

// component
import CourseCardWide from './CourseCardWide'

// Materail UI component
import Grid from '@mui/material/Grid'

const MyCourseCardList = ({ data }) => {

    const side = 3
    const size = 6

    const getCourseCardElement = (data) => {
        return (
            <Grid item xs={size} key={++key}>
                <CourseCardWide
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
    for (let i = 0; i < data?.length; ++i) {
        elements.push(<Grid item xs={side} key={++key}></Grid>)
        elements.push(getCourseCardElement(data[i]))
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

export default MyCourseCardList