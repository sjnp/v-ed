import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'

// component
import stringToColor from './stringToColor';

// Material UI component
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import Avatar from '@mui/material/Avatar'
import Typography from '@mui/material/Typography'
import Rating from "@mui/material/Rating"
import StarIcon from '@mui/icons-material/Star'
import Paper from '@mui/material/Paper'
import Grid from '@mui/material/Grid'
import LoadingButton from '@mui/lab/LoadingButton'
import Skeleton from '@mui/material/Skeleton';

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'
import {URL_BUY_COURSE} from "../utils/url";

const BuyCourseOverview = ({ data }) => {

  const { instructorPictureURI, courseName, instructorFirstname, instructorLastname} = data
  const { price, ratingCourse, totalReview, courseId } = data

  const navigate = useNavigate()

  const axiosPrivate = useAxiosPrivate()

  const [ loading, setLoading ] = useState(false)

  const handleClickGetFreeCourse = async () => {
    setLoading(true)
    // const response = await apiPrivate.post(axiosPrivate, URL_BUY_COURSE.replace('{courseId}', courseId))
    
    // if (response.status === 201) {
    //   navigate(`/payment/course/${courseId}/success`)
    // }
    // setLoading(false)
  }
  
  return (
    <Paper>
    {
      courseId === undefined ? 

      <Grid container padding={3}>
        <Grid item xs={2}>
          <Skeleton variant="circular" width={40} height={40} />
        </Grid>
        <Grid item xs={10}>
            <Grid item xs={10}> <Skeleton variant="text" /> </Grid>
            <Grid item xs={8}> <Skeleton variant="text" /> </Grid>
        </Grid>
        <Grid item xs={12} paddingTop={5}>
          <Grid container direction="row" alignItems="center" justifyContent="center">
          {
            Array(5).fill().map((element, index) => (
              <Skeleton key={index} variant="circular" width={30} height={30} sx={{ m: 0.5 }} />
            ))
          } 
          </Grid>
        </Grid>
        <Grid item xs={12} paddingTop={5}>
          <Grid container direction="column" alignItems="center" justifyContent="center">
            <Skeleton variant='rectangular' width={70} height={30}  />
            <Skeleton variant='rectangular' width={100} height={30} sx={{ mt: 3, mb:2 }}/>
          </Grid>
        </Grid>
      </Grid>
     :
      <Grid container padding={3}>
        <Grid item xs={2}>
          <Avatar
            alt={instructorFirstname}
            src={instructorPictureURI || "/static/images/avatar/2.jpg"} 
            sx={{ bgcolor: stringToColor(instructorFirstname), mt: 0.3 }}
          /> 
        </Grid>
        <Grid item xs={10}>
            <Grid item xs={10}>{courseName}</Grid>
            <Grid item xs={8}>
              <Typography variant='body2' color='gray'>{instructorFirstname} {instructorLastname}</Typography>
            </Grid>
        </Grid>
        <Grid item xs={12} paddingTop={5}>
          <Grid container direction="row" alignItems="center" justifyContent="center">
          {
            ratingCourse === 0 && totalReview === 0 ?
            <Typography variant='button' color='gray' marginTop={2}>No review now</Typography>
            :
            <Box>
              <Rating value={ratingCourse} size="large" readOnly emptyIcon={<StarIcon fontSize="inherit" />} />
              <Typography variant="body1" marginTop={2}>{ratingCourse} {totalReview}</Typography>
            </Box>
          }
          </Grid>
        </Grid>
        <Grid item xs={12} paddingTop={5}>
          <Grid container direction="column" alignItems="center" justifyContent="center" marginBottom={2}>
            <Typography variant="h6" color='primary' sx={{ marginBottom: 3 }}>
              {price === 0 ? 'FREE' : `${price} THB`}
            </Typography>
            {
              price === 0 ?
              <LoadingButton loading={loading} variant='contained' onClick={handleClickGetFreeCourse}>
                GET COURSE NOW
              </LoadingButton>
              :
              <Button variant="contained" onClick={() => navigate(`/payment/course/${courseId}`)}>
                BUY NOW
              </Button>
            }
          </Grid>
        </Grid>
      </Grid>
    }
    </Paper>
  )
}

export default BuyCourseOverview