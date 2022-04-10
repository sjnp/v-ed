import React from 'react'

import AppBarHeader from "../components/AppBarHeader"
import FilterSidebar from '../components/FilterSidebar'
import SearchResult from '../components/SearchResult'

// Material UI
import {Container,Box, Grid,Typography } from '@mui/material'

const Search = () => {
  return (
    <Container maxWidth="lg">
      <AppBarHeader />
     
      <Grid container>
        <Grid item xs={3} sx={{my : 3}}>
          <FilterSidebar/>
        </Grid>
        <Grid item xs={9} sx={{my : 3}}>
          <SearchResult/>
        </Grid>
    </Grid>
    </Container>
  )
}

export default Search