import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { useSelector } from 'react-redux'
import ReactPlayer from 'react-player'

// component
import AppBarSearchHeader from "../components/AppBarSearchHeader";
import BuyCourseOverview from "../components/BuyCourseOverview"
import OverviewDetail from '../components/OverviewDetail';

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid';
import Skeleton from '@mui/material/Skeleton';

// service
import overviewService from '../services/overview';

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'
import useReasonReport from '../hooks/useReasonReport'

// url
import { URL_GET_OVERVIEW_COURSE } from '../utils/url';


const Overview = () => {

  const { courseId } = useParams()
  const apiPrivate = useApiPrivate()
  const createReasonReportRedux = useReasonReport()

  const username = useSelector((state) => state.auth.value.username);

  const [ overview, setOverview ] = useState({})
  const [ videoExampleURI, setVideoExampleURI ] = useState('')

  useEffect(async () => {
    let response;
    if (username) {
      const url = URL_GET_OVERVIEW_COURSE.replace('{courseId}', courseId)
      response = await apiPrivate.get(url)
    } else {
      response = await overviewService.getOverviewCourse(courseId)
    }

    if (response.status === 200) {
      setOverview(response.data)
      response = await overviewService.getExampleVideoCourse(courseId)
      if (response.status === 200) {
        setVideoExampleURI(response.data)
      }
    }

    createReasonReportRedux()
  }, [username])

  return (
    <Container maxWidth="lg">
      <AppBarSearchHeader />
      <Grid container rowSpacing={1} marginTop={1}>
        <Grid item xs={8}>
          <Grid container height='100%' direction="column" alignItems="center" justifyContent="center">
          {
            videoExampleURI ?
            <ReactPlayer light={overview?.pictureURL} url={videoExampleURI} playing={true} controls={true} />
            :
            <Skeleton variant='rectangular' width='90%' height='90%' />
          }
          </Grid>
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