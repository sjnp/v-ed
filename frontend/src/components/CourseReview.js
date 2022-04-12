import React from 'react'

// component
import ReviewRead from './ReviewRead'

// Material UI
import Typography from '@mui/material/Typography'
import Box from '@mui/material/Box'

const CourseReview = () => {

    let index = 0

    const reviews = [
        {
            rating: 2.6,
            comment: `this test commment from data index ${++index}`,
            firstname: `firstname ${index}`,
            lastname: `lastname ${index}`,
            datetime: new Date().toISOString()
        },
        {
            rating: 2.6,
            comment: `this test commment from data index ${++index}`,
            firstname: `firstname ${index}`,
            lastname: `lastname ${index}`,
            datetime: new Date().toISOString()
        },
        {
            rating: 2.6,
            comment: `this test commment from data index ${++index}`,
            firstname: `firstname ${index}`,
            lastname: `lastname ${index}`,
            datetime: new Date().toISOString()
        },
        {
            rating: 2.6,
            comment: `this test commment from data index ${++index}`,
            firstname: `firstname ${index}`,
            lastname: `lastname ${index}`,
            datetime: new Date().toISOString()
        },
        {
            rating: 2.6,
            comment: `this test commment from data index ${++index}`,
            firstname: `firstname ${index}`,
            lastname: `lastname ${index}`,
            datetime: new Date().toISOString()
        },
        {
            rating: 2.6,
            comment: `this test commment from data index ${++index}`,
            firstname: `firstname ${index}`,
            lastname: `lastname ${index}`,
            datetime: new Date().toISOString()
        },
        {
            rating: 2.6,
            comment: `this test commment from data index ${++index}`,
            firstname: `firstname ${index}`,
            lastname: `lastname ${index}`,
            datetime: new Date().toISOString()
        },
        {
            rating: 2.6,
            comment: `this test commment from data index ${++index}`,
            firstname: `firstname ${index}`,
            lastname: `lastname ${index}`,
            datetime: new Date().toISOString()
        },
        {
            rating: 2.6,
            comment: `this test commment from data index ${++index}`,
            firstname: `firstname ${index}`,
            lastname: `lastname ${index}`,
            datetime: new Date().toISOString()
        },
        {
            rating: 2.6,
            comment: `this test commment from data index ${++index}`,
            firstname: `firstname ${index}`,
            lastname: `lastname ${index}`,
            datetime: new Date().toISOString()
        },
        {
            rating: 2.6,
            comment: `this test commment from data index ${++index}`,
            firstname: `firstname ${index}`,
            lastname: `lastname ${index}`,
            datetime: new Date().toISOString()
        },
        {
            rating: 2.6,
            comment: `this test commment from data index ${++index}`,
            firstname: `firstname ${index}`,
            lastname: `lastname ${index}`,
            datetime: new Date().toISOString()
        },
        {
            rating: 2.6,
            comment: `this test commment from data index ${++index}`,
            firstname: `firstname ${index}`,
            lastname: `lastname ${index}`,
            datetime: new Date().toISOString()
        },
    ]

    return (
        <Box>
            <Typography variant='h6'>
                Review
            </Typography>
            <Box>
            {
                reviews.map((review, index) => (
                    <ReviewRead
                        key={index}
                        rating={review.rating}
                        comment={review.comment}
                        firstname={review.firstname}
                        lastname={review.lastname}
                        datetime={review.datetime}
                    />
                ))
            }
            </Box>
        </Box>
    )
}

export default CourseReview