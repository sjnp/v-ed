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

  const [ videoExampleURI, setVideoExampleURI ] = useState('')

  useEffect(() => {

    const getOverviewCaourseAPI = async () =>{
      let response = await overviewService.getOverviewCourse(courseId)
      if (response.status === 200) {
        setOverview(response.data)

        const fileName = response.data.chapterList[0].sections[0].videoUri
        response = await overviewService.getAccessURI(fileName)
        if (response.status === 200) {
          setVideoExampleURI(response.data)
        }
      }
    }
    getOverviewCaourseAPI()

  }, [])

  return (
    <Container maxWidth="lg">
      <AppBarSearchHeader />
      <Grid container rowSpacing={1} sx={{ marginTop: 1 }}>
        <Grid item xs={8}>
          <ReactPlayer
            light={overview?.pictureURL}
            url={videoExampleURI}
            playing={true}
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