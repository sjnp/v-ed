import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'

// Material UI
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import CardHeader from '@mui/material/CardHeader'
import Avatar from '@mui/material/Avatar'
import Typography from '@mui/material/Typography'
import Rating from "@mui/material/Rating"
import StarIcon from '@mui/icons-material/Star'
import Paper from '@mui/material/Paper'
import LoadingButton from '@mui/lab/LoadingButton'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

const BuyCourseOverview = ({ data }) => {

  // const { instructorPictureURI, courseName, instructorFirstname, instructorLastname, price } = data
  // const { ratingCourse, totalReview } = data
  // const { courseId } = data

  const {
    instructorPictureURI, 
    courseName, 
    instructorFirstname, 
    instructorLastname, 
    price,
    ratingCourse, 
    totalReview, 
    courseId
  } = data

  const navigate = useNavigate()

  const axiosPrivate = useAxiosPrivate()

  // const handleClickBuyCourse = () => {
  //   navigate(`/payment/course/${courseId}`)
  // }

  const [ isBuyFree, setIsBuyFree ] = useState(false)

  const handleClickGetCourse = async () => {
    setIsBuyFree(true)
    const response = await apiPrivate.post(axiosPrivate, '/api/student-course/buy-free-course/' + courseId)
    
    if (response.status === 201) {
      navigate(`/payment/course/${courseId}/success`)
    }
    setIsBuyFree(false)
    
  }

  const imageURL = 'https://www.cats.org.uk/media/2297/tabby-cat-looking-up.jpg?width=1600'

  return (
    <Paper>
      <CardHeader
        avatar={ <Avatar src={instructorPictureURI || imageURL} /> } 
        title={courseName}
        subheader={`${instructorFirstname || ''} ${instructorLastname || ''}`}
      />
      <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
      {
        ratingCourse === 0 && totalReview === 0 ?
          <Typography variant='button' color='gray' marginBottom={5} marginTop={2}>
            No review now
          </Typography>
          :
          <Box>
            <Rating
              value={ratingCourse} 
              size="large" 
              readOnly 
              precision={0.1} 
              emptyIcon={<StarIcon fontSize="inherit" />}
              sx={{ marginTop: 2, marginBottom: 1 }}
            />
            <Typography variant="body1" marginBottom={3} textAlign='center'>
              {ratingCourse} ({totalReview})
            </Typography>
          </Box>
      }
        <Typography variant="h6" sx={{ marginBottom: 3 }}>
            {price === 0 ? 'FREE' : `${price} THB`}
        </Typography>
      {
        price === 0 ?
          <LoadingButton
            loading={isBuyFree} 
            variant='contained' 
            sx={{ mb: 5 }} 
            onClick={handleClickGetCourse}
          >
            GET COURSE NOW
          </LoadingButton>
          :
          <Button 
            variant="contained" 
            onClick={() => navigate(`/payment/course/${courseId}`)} 
            sx={{ mb: 5 }}
          >
            BUY NOW
          </Button>
      }
      </Box>
    </Paper>
  )
}

export default BuyCourseOverview