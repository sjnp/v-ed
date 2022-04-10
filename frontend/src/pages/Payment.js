import React from 'react'

import AppBarHeader from "../components/AppBarHeader"
import FilterSidebar from '../components/FilterSidebar'
import SearchResult from '../components/SearchResult'
import CourseCardWide from '../components/CourseCardWide'
import BankingSelect from '../components/BankingSelect'

// Material UI
import { Container, Box, Button , Grid, Typography } from '@mui/material'

const Payment = () => {

  const data = {
    image: `https://picsum.photos/200/300?random=${0}`,
    courseName: `Java programming ${0}`,
    instructorName: `pradinan benjanavee ${0}`,
    rating: 4.7,
    reviewTotal: 125,
    pathOnClick: '/overview'
  }

  return (
    <Container maxWidth="lg">
      <AppBarHeader />
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
          key={0}
          image={data.image}
          courseName={data.courseName}
          instructorName={data.instructorName}
          rating={data.rating}
          reviewTotal={data.reviewTotal}
          pathOnClick={data.pathOnClick}
          price={500}
        />
        <Typography variant='h5' sx={{ alignSelf: 'flex-start', marginLeft: 10.5, marginBottom: 2, marginTop: 10 }}>
          Internet Banking :
        </Typography>
        <BankingSelect />
        <Button
          type='submit'
          // fullWidth
          variant='contained'
          size='large'
          sx={{
            marginTop: 6,
            marginBottom: 2,
            width: 300
          }}
        // startIcon={<Login />}
        >
          Payment
        </Button>
      </Box>
    </Container>
  )
}

export default Payment