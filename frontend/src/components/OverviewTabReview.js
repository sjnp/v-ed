import React from 'react'

// component
import ReviewCard from '../components/ReviewCard'

// Materail UI component
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'

const OverviewTabReview = ({ data }) => {

    return (
        <Box minHeight={500} paddingTop={5} paddingLeft={20} paddingRight={20}>
        {
            data?.length > 0 ?
            data?.map((review, index) => (
                <ReviewCard 
                    key={index}
                    reviewId={review.id}
                    rating={review.rating}
                    comment={review.comment}
                    displayUrl={review.displayUrl}
                    reviewUsername={review.reviewUsername}
                    firstname={review.firstname}
                    lastname={review.lastname}
                    datetime={review.reviewDateTime}
                />
            ))
            :
            <Box display='flex' flexDirection='column' textAlign='center' paddingTop={10}>
                <Typography variant='button' color='gray'>No review now</Typography>
            </Box>
        }   
        </Box>
    )
}

export default OverviewTabReview