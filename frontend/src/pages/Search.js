import React from 'react'

import AppBarHeader from "../components/AppBarHeader"

// Material UI
import Container from '@mui/material/Container'
import Typography from '@mui/material/Typography';

const Search = () => {
  return (
    <Container maxWidth="lg">
      <AppBarHeader />
      <Typography variant="h5" sx={{ fontWeight: 'bold', marginTop: 1 }}>
        Search
      </Typography>
      
      
    </Container>
  )
}

export default Search