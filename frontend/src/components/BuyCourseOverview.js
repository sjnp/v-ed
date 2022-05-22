import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useSelector } from 'react-redux'

// component
import stringToColor from './stringToColor'
import SignInForm from './SignInForm'
import LoadingBuyCourseOverview from './LoadingBuyCourseOverview'
import SignUpForm from './SignUpForm';
import SuccessAlertBox from './SuccessAlertBox';

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
import Container from '@mui/material/Container'
import Modal from '@mui/material/Modal'

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_BUY_COURSE } from "../utils/url"
import { URL_FREE_COURSE } from '../utils/url'

const BuyCourseOverview = ({ data }) => {

  const navigate = useNavigate()

  const axiosPrivate = useAxiosPrivate()

  const { instructorPictureURI, courseName, instructorFirstname, instructorLastname} = data
  const { price, ratingCourse, totalReview, courseId } = data

  const [ loadingGetFreeCourse, setLoadingGetFreeCourse ] = useState(false)

  const username = useSelector(state => state.auth.value.username)
  const [ requiredLogin, setRequiredLogin ] = useState(false)
  const [ callSignUpForm, setCallSignUpForm ] = useState(false)
  const [ openSignUpSuccess, setOpenSignUpSuccess ] = useState(false)

  const handleClickCallSignUpForm = () => {
    setRequiredLogin(false)
    setCallSignUpForm(true)
  }

  const handleSignUpSuccess = () => {
    setCallSignUpForm(false)
    setOpenSignUpSuccess(true)
  }

  const handleClickGetFreeCourse = async () => {
    if (username) {
      setLoadingGetFreeCourse(true)

      // const payload = {
      //   courseId: courseId
      // }
      const payload = courseId
      const response = await apiPrivate.post(axiosPrivate, URL_FREE_COURSE, payload)
      
      setLoadingGetFreeCourse(false)
      
      if (response.status === 201) {
        // navigate(`/payment/course/${courseId}/success`)
        alert('success')
      } else {
        alert(JSON.stringify(response.message))
      }
      
    } else {
      setRequiredLogin(true)
    }
  }

  const handleClickBuyNow = async () => {
    if (username) {
      navigate(`/payment/course/${courseId}`)
    } else {
      setRequiredLogin(true)
    }
  }
  
  return (
    <Paper>
    {
      courseId === undefined ? 
      <LoadingBuyCourseOverview />
      :
      <Grid container p={3}>
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
              <Typography variant='body2' color='gray'>
                {`${instructorFirstname} ${instructorLastname}`}
              </Typography>
            </Grid>
        </Grid>
        <Grid item xs={12} pt={5}>
          <Grid container direction="row" alignItems="center" justifyContent="center">
          {
            ratingCourse === 0 && totalReview === 0 ?
            <Typography variant='button' color='gray' mt={2}>No review now</Typography>
            :
            <Box>
              <Rating value={ratingCourse} size="large" readOnly emptyIcon={<StarIcon fontSize="inherit" />} />
              <Typography variant="body1" mt={2}>{ratingCourse} {totalReview}</Typography>
            </Box>
          }
          </Grid>
        </Grid>
        <Grid item xs={12} pt={5}>
          <Grid container direction="column" alignItems="center" justifyContent="center" marginBottom={2}>
            <Typography variant="h6" color='primary' sx={{ mb: 3 }}>
              {price === 0 ? 'FREE' : `${price} THB`}
            </Typography>
            {
              price === 0 ?
              <LoadingButton variant='contained' loading={loadingGetFreeCourse} onClick={handleClickGetFreeCourse}>
                GET COURSE NOW
              </LoadingButton>
              :
              <Button variant="contained" onClick={handleClickBuyNow}>
                BUY NOW
              </Button>
            }
          </Grid>
        </Grid>
      </Grid>
    }
      <Modal open={requiredLogin} onClose={() => setRequiredLogin(false)}>
        <Container component='main' maxWidth='xs'>
          <SignInForm onLoginSuccess={() => setRequiredLogin(false)} onSignUp={handleClickCallSignUpForm} />
        </Container>
      </Modal>

      <Modal open={callSignUpForm}>
        <Container component='main' maxWidth='xs'>
          <SignUpForm onClose={() => setCallSignUpForm(false)} onSuccess={handleSignUpSuccess} />
        </Container>
      </Modal>
      
      <Modal open={openSignUpSuccess} onClose={() => setOpenSignUpSuccess(false)}>
        <Container component="main" maxWidth="xs">
          <SuccessAlertBox handleClick={() => setOpenSignUpSuccess(false)} text='Register successful' />
        </Container>
      </Modal>
    </Paper>
  )
}

export default BuyCourseOverview