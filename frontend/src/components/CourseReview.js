import React, { useState } from 'react'

// component
import ReviewRead from './ReviewRead'
import ReviewWrite from './ReviewWrite'
import ReviewEdit from './ReviewEdit'

// Material UI component
import Typography from '@mui/material/Typography'
import Box from '@mui/material/Box'
import Fab from '@mui/material/Fab'
import Grid from '@mui/material/Grid'
import IconButton from '@mui/material/IconButton'

// Material UI icon
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import CachedIcon from '@mui/icons-material/Cached';



const CourseReview = () => {
    
    const [ reviews, setReviews ] = useState([])

    const getReviewReadElement = () => {
        if (reviews?.length === 0) {
            return <div style={{ textAlign: 'center', color: 'gray' }}>No review</div>
        }

        return reviews
    }
    const [ elementReview, setElementReview ] = useState(getReviewReadElement())

    const [ isReview, setIsReview ] = useState(false)

    const [ labelPage, setLabelPage ] = useState('Review')
    const [ hideArrowBackIcon, setHideArrowBackIcon ] = useState(true)
    const [ hideActionReview, setHideActionReview ] = useState(false)

    const handleCreateReview = () => {
        setLabelPage('Write review')
        setHideArrowBackIcon(false)
        setElementReview(<ReviewWrite />)
        setHideActionReview(true)
    }

    const handleEditReview = () => {
        alert('edit review')
    }

    const handClickArrowIcon = () => {
        setLabelPage('Review')
        setHideArrowBackIcon(true)
        setElementReview(getReviewReadElement())
        setHideActionReview(false)
    }

    



    // const getListReivew = () => {
    //     return reviews.map((review, index) => (
    //         <ReviewRead
    //             key={index}
    //             rating={review.rating}
    //             comment={review.comment}
    //             firstname={review.firstname}
    //             lastname={review.lastname}
    //             datetime={review.datetime}
    //         />
    //     ))
    // }

    // const [ isReview, setIsReview ] = useState(isReviewAlready)

    

    // const [ reviews, setReview ] = useState([])
    // const notReview = <div style={{ textAlign: 'center', color: 'gray' }}>Not review.</div>


    // const handleClickCreateReview = () => {
        // setLabelPage('Write review')
        // setHideArrowBackIcon(false)
        // setElementReview(<ReviewWrite />)
        // setHideCreateReviewIcon(true)
        // setHideEditReviewIcon(true)
    // }

    // const handleClickEditReview = () => {
        // setLabelPage('Edit review')
        // setHideArrowBackIcon(false)
        // setElementReview(<ReviewEdit />)
        // setHideCreateReviewIcon(true)
        // setHideEditReviewIcon(true)
    // }

    // const [ hideCreateReviewIcon, setHideCreateReviewIcon ] = useState(!isReview)
    // const [ hideEditReviewIcon, setHideEditReviewIcon ] = useState(isReview)
    // const [hideArrowBackIcon, setHideArrowBackIcon] = useState(true)

    // const [  ]

    // const [ hideCreateOrEditButton, setHhideCreateOrEditButton ] = useState(false)

    // const handleClickArrowBackIcon = () => {
        // setHideArrowBackIcon(true)
        // setLabelPage('Review')
        // setElementReview(reviews?.length || notReview)
        // toggleIsReview()
    // }

    return (
        <Grid container>
            <Grid item xs={9}>
                <Typography variant='h6' sx={{ mb: 2 }}>
                    {labelPage}
                </Typography>
            </Grid>
            <Grid item xs={1} >
                <Box hidden={hideActionReview}>
                {
                    isReview ?
                        <Fab size="small" color="primary" onClick={handleEditReview}>
                            <EditIcon />
                        </Fab>
                        
                    :
                        <Fab size="small" color="primary" onClick={handleCreateReview} >
                            <AddIcon />
                        </Fab>
                }
                </Box>
            </Grid>
            <Grid item xs={2}>
                <Fab size="small" color="primary" onClick={() => setIsReview(!isReview)}>
                    <CachedIcon />
                </Fab>
            </Grid>
            <Grid item xs={2} hidden={hideArrowBackIcon}>
                <IconButton onClick={handClickArrowIcon}>
                    <ArrowBackIcon />
                </IconButton>
            </Grid>
            <Grid item xs={6}>
                {elementReview}
            </Grid>
        </Grid>
    )
}

export default CourseReview