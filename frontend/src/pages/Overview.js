import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import ReactPlayer from 'react-player'

// component
import AppBarSearchHeader from "../components/AppBarSearchHeader";
import BuyCourseOverview from "../components/BuyCourseOverview"
import OverviewDetail from '../components/OverviewDetail';

// Material UI
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid';

// service
import overviewService from '../services/overview';

const Overview = () => {

  const { courseId } = useParams()

  const [ overview, setOverview ] = useState({})

  useEffect(() => {

    const getOverviewCaourseAPI = async () =>{
      const result = await overviewService.getOverviewCaourse(courseId)
      setOverview(result)
    }
    getOverviewCaourseAPI()

  }, [])

  return (
    <Container maxWidth="lg">
      <AppBarSearchHeader />
      <Grid container rowSpacing={1} sx={{ marginTop: 1 }}>
        <Grid item xs={8}>
          <ReactPlayer
            url='https://objectstorage.ap-singapore-1.oraclecloud.com/p/XXvFzOhvxFRZFa-XPjnT-ueEIJx-m2F1HoAuYzhE90EaetI_xgn44BY-FE3lun1w/n/axjskktj5xlm/b/bucket-20220310-1506/o/bunny.mp4'
            controls={true}
            style={{ margin: 'auto' }}
          />
        </Grid>
        <Grid item xs={4}>
            <BuyCourseOverview data={overview} />
        </Grid>
        <Grid item xs={12}>
          <OverviewDetail data={overview} />
        </Grid>
      </Grid>
    </Container>
  )
}

export default Overview