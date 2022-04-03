import React from 'react'
import ReactPlayer from 'react-player'

// component
import AppBarHeader from "../components/AppBarHeader";
import BuyCourseOverview from "../components/BuyCourseOverview"
import OverviewDetail from '../components/OverviewDetail';

// Material UI
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid';

const Overview = () => {

  return (
    <Container maxWidth="lg">
      <AppBarHeader />
      <Grid container rowSpacing={1} sx={{ marginTop: 1 }}>

        <Grid item xs={8}>
          <ReactPlayer
            url='https://www.youtube.com/watch?v=u798bXYXz2w'
            controls={true}
            style={{ margin: 'auto' }}
          />
        </Grid>
        
        <Grid item xs={4}>
            <BuyCourseOverview />
        </Grid>

        <Grid item xs={12}>
          <OverviewDetail />
        </Grid>
        
      </Grid>

    </Container>
  )
}

export default Overview