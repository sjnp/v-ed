import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom';

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate';

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader';
import CourseCardWide from '../components/CourseCardWide'
import BankingPaymentSelect from '../components/BankingPaymentSelect'

// Material UI
import { Container, Box, Button, Typography } from '@mui/material'

// url
import { URL_GET_OVERVIEW_COURSE_CARD } from '../utils/url';

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

  const [ chargeData, setChargeData ] = useState({
    amount: null,
    currency: "thb",
    courseId: null,
    type: "internet_banking_ktb",
    returnUri: `payment/course/${courseId}/success`
  })

  useEffect(async () => {

    const url = URL_GET_OVERVIEW_COURSE_CARD.replace('{courseId}', courseId)
    const result = await axiosPrivate.get(url).then(res => res.data).catch(err => err.response)
    setCourseCard(result)
    setChargeData({...chargeData,
      amount: (result.price * 100),
      courseId: result.courseId,
      // returnUrl: result.pictureURL
      returnUrl: `payment/course/${courseId}/success`
    })
  }, [])

  const handlePayment = async () => {
    try {
      const response = await axiosPrivate.post('/api/students/purchase/course', chargeData );
      console.log(response.data.authorizeUri);
      window.open(response.data.authorizeUri, "_self")
      // window.open(response.data.authorizeUri, "_blank")
    } catch (err) {
      console.error(err);
      // navigate('/', { state: { from: location }, replace: true });
    }
  }

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
          reviewCount={courseCard.reviewCount}
          pathOnClick={`/overview/course/${courseCard.courseId}`}
          price={courseCard.price}
        />
        <Typography variant='h5' sx={{ alignSelf: 'flex-start', marginLeft: 10.5, marginBottom: 2, marginTop: 10 }}>
          Internet Banking :
        </Typography>
        <BankingPaymentSelect setChargeData={setChargeData} chargeData={chargeData}/>
        <Button
          type='submit'
          variant='contained'
          size='large'
          sx={{
            marginTop: 6,
            marginBottom: 2,
            width: 300
          }}
          onClick={handlePayment}
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