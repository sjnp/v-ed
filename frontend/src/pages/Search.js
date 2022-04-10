import React from 'react'

import AppBarSearchHeader from "../components/AppBarSearchHeader";
import FilterSidebar from '../components/FilterSidebar'
import SearchResult from '../components/SearchResult'

// Material UI
import {Container,Box, Grid,Typography } from '@mui/material'

const Search = () => {
  return (
    <Container maxWidth="lg">
      <AppBarSearchHeader />
     
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