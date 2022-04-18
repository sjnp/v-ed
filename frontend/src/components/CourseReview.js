import React, { useState } from 'react'

// component
import ReviewRead from './ReviewRead'
import ReviewWrite from './ReviewWrite'

// Material UI
import Typography from '@mui/material/Typography'
import Box from '@mui/material/Box'
import Fab from '@mui/material/Fab'
import Grid from '@mui/material/Grid'
import IconButton from '@mui/material/IconButton'

// icon
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import CachedIcon from '@mui/icons-material/Cached';

const CourseReview = () => {

    let index = 0

    const [ labelPage, setLabelPage ] = useState('Review')

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
        // {
        //     rating: 2.6,
        //     comment: `this test commment from data index ${++index}`,
        //     firstname: `firstname ${index}`,
        //     lastname: `lastname ${index}`,
        //     datetime: new Date().toISOString()
        // },
        // {
        //     rating: 2.6,
        //     comment: `this test commment from data index ${++index}`,
        //     firstname: `firstname ${index}`,
        //     lastname: `lastname ${index}`,
        //     datetime: new Date().toISOString()
        // },
        // {
        //     rating: 2.6,
        //     comment: `this test commment from data index ${++index}`,
        //     firstname: `firstname ${index}`,
        //     lastname: `lastname ${index}`,
        //     datetime: new Date().toISOString()
        // },
        // {
        //     rating: 2.6,
        //     comment: `this test commment from data index ${++index}`,
        //     firstname: `firstname ${index}`,
        //     lastname: `lastname ${index}`,
        //     datetime: new Date().toISOString()
        // },
        // {
        //     rating: 2.6,
        //     comment: `this test commment from data index ${++index}`,
        //     firstname: `firstname ${index}`,
        //     lastname: `lastname ${index}`,
        //     datetime: new Date().toISOString()
        // },
        // {
        //     rating: 2.6,
        //     comment: `this test commment from data index ${++index}`,
        //     firstname: `firstname ${index}`,
        //     lastname: `lastname ${index}`,
        //     datetime: new Date().toISOString()
        // },
        // {
        //     rating: 2.6,
        //     comment: `this test commment from data index ${++index}`,
        //     firstname: `firstname ${index}`,
        //     lastname: `lastname ${index}`,
        //     datetime: new Date().toISOString()
        // },
        // {
        //     rating: 2.6,
        //     comment: `this test commment from data index ${++index}`,
        //     firstname: `firstname ${index}`,
        //     lastname: `lastname ${index}`,
        //     datetime: new Date().toISOString()
        // },
        // {
        //     rating: 2.6,
        //     comment: `this test commment from data index ${++index}`,
        //     firstname: `firstname ${index}`,
        //     lastname: `lastname ${index}`,
        //     datetime: new Date().toISOString()
        // },
        // {
        //     rating: 2.6,
        //     comment: `this test commment from data index ${++index}`,
        //     firstname: `firstname ${index}`,
        //     lastname: `lastname ${index}`,
        //     datetime: new Date().toISOString()
        // },
        // {
        //     rating: 2.6,
        //     comment: `this test commment from data index ${++index}`,
        //     firstname: `firstname ${index}`,
        //     lastname: `lastname ${index}`,
        //     datetime: new Date().toISOString()
        // },
    ]

    const getListReivew = () => {
        return reviews.map((review, index) => (
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
    
    const [ isReview, setIsReview ] = useState(true)

    const showCreateOrEditReviewButton = () => {
        if (isReview) {
            return (
                <Box hidden={hideCreateOrEditButton} title="Edit your review.">
                    <Fab size="small" color="primary" onClick={handleClickEditReview}>
                        <EditIcon />
                    </Fab>
                </Box>
            )
        } else {
            return (
                <Box hidden={hideCreateOrEditButton} title="Let write review.">
                    <Fab size="small" color="primary" onClick={handleClickCreateReview}>
                        <AddIcon />
                    </Fab>
                </Box>
            )
        }
    }

    const [ elementReview, setElementReview ] = useState(getListReivew())


    const handleClickCreateReview = () => {
        setLabelPage('Write review')
        setHideArrowBackIcon(false)
        setElementReview(<ReviewWrite />)
        setHhideCreateOrEditButton(true)
    }

    const handleClickEditReview = () => {
        setLabelPage('Edit review')
        setHideArrowBackIcon(false)
        setElementReview(<ReviewWrite dataRating={4.2} dataComment={"test comment edit" } />)
        setHhideCreateOrEditButton(true)
    }


    const [ hideArrowBackIcon, setHideArrowBackIcon ] = useState(true)

    const [ hideCreateOrEditButton, setHhideCreateOrEditButton ] = useState(false)

    const handleClickArrowBackIcon = () => {
        setHideArrowBackIcon(true)
        setLabelPage('Review')
        setElementReview(getListReivew())
        setHhideCreateOrEditButton(false)
    }

    const toggleIsReview = () => {
        setIsReview(!isReview)
    }

    return (
        <Grid container>

            <Grid item xs={1}>
                <Box hidden={hideArrowBackIcon}>
                    <IconButton onClick={handleClickArrowBackIcon}>
                        <ArrowBackIcon />
                    </IconButton>
                </Box>
            </Grid>


            <Grid item xs={8}>
                <Typography variant='h6'>
                    {labelPage}
                </Typography>
            </Grid>
            <Grid item xs={1}>
                {showCreateOrEditReviewButton()}   
            </Grid>
            <Grid item xs={2}>
                {/* space next write button */}
                <Box hidden={false} title="Toggle is review.">
                    <Fab size="small" color="primary" onClick={toggleIsReview}>
                        <CachedIcon />
                    </Fab>
                </Box>
            </Grid>

            <Grid item xs={1}>{/* space before list review */}</Grid>
            <Grid item xs={6}>
                {elementReview}
            </Grid>
            <Grid item xs={5}>{/* space before list review */}</Grid>


            <Grid item xs={1}>{/* space before list review */}</Grid>
            <Grid item xs={6}>
            {
                
            }
            </Grid>

        </Grid>
    )
}

export default CourseReview