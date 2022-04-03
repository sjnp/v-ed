import React from 'react'

import AppBarHeader from "../components/AppBarHeader";

import Container from '@mui/material/Container'
import Typography from '@mui/material/Typography';

const Review = () => {


  return (
    <Container maxWidth="lg">
      <AppBarHeader />
      <br/>
      <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
        Review
      </Typography>
      <br/>
      
      
    </Container>
  )
}

export default Review