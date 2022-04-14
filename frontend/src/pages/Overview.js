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
            url='https://objectstorage.ap-singapore-1.oraclecloud.com/p/XXvFzOhvxFRZFa-XPjnT-ueEIJx-m2F1HoAuYzhE90EaetI_xgn44BY-FE3lun1w/n/axjskktj5xlm/b/bucket-20220310-1506/o/bunny.mp4'
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