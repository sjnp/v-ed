import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom';

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate';

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader';
import CourseCardWide from '../components/CourseCardWide'
import BankingSelect from '../components/BankingSelect'

// Material UI
import { Container, Box, Button, Typography } from '@mui/material'

// url
import { URL_OVERVIEW_COURSE_ID_CARD } from '../utils/url';

const Payment = () => {

  const { courseId } = useParams()

  const navigate = useNavigate()

  const axiosPrivate = useAxiosPrivate()

  const [ courseCard, setCourseCard ] = useState({
      courseName: null,
      instructorName: null,
      rating: null,
      reviewCount: null,
      pictureURL: null,
      price: null,
      courseId: null
  })

  useEffect(async () => {

    const url = URL_OVERVIEW_COURSE_ID_CARD.replace('{courseId}', courseId)
    const result = await axiosPrivate.get(url).then(res => res.data).catch(err => err.response)
    setCourseCard(result)
  
  }, [])

  return (
    <Container maxWidth='lg'>
      <AppBarSearchHeader />
      <Box
        sx={{
          marginTop: 3,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Typography variant='h3' sx={{ alignSelf: 'flex-start', marginLeft: 10, marginBottom: 2 }}>
          Checkout
        </Typography>
        <Typography variant='h5' sx={{ alignSelf: 'flex-start', marginLeft: 10.5, marginBottom: 2 }}>
          Course Select :
        </Typography>
        <CourseCardWide
          image={courseCard.pictureURL}
          courseName={courseCard.courseName}
          instructorName={courseCard.instructorName}
          rating={courseCard.rating}
          reviewTotal={courseCard.reviewCount}
          pathOnClick={`/overview/course/${courseCard.courseId}`}
          price={courseCard.price}
        />
        <Typography variant='h5' sx={{ alignSelf: 'flex-start', marginLeft: 10.5, marginBottom: 2, marginTop: 10 }}>
          Internet Banking :
        </Typography>
        <BankingSelect />
        <Button
          type='submit'
          variant='contained'
          size='large'
          sx={{
            marginTop: 6,
            marginBottom: 2,
            width: 300
          }}
        >
          Payment
        </Button>

        <Button variant='contained' onClick={() => navigate(`/payment/course/${courseId}/success`)}>
          Go to payment success page
        </Button>

      </Box>
    </Container>
  )
}

export default Payment