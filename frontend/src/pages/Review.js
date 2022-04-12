import React from 'react'

import AppBarSearchHeader from "../components/AppBarSearchHeader";

import Container from '@mui/material/Container'
import Typography from '@mui/material/Typography';

const Review = () => {


  return (
    <Container maxWidth="lg">
      <AppBarSearchHeader />
      <br/>
      <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
        Review
      </Typography>
      <br/>
      
      
    </Container>
  )
}

export default Review