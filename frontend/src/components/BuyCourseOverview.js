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
import Skeleton from '@mui/material/Skeleton'

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

  const {
    instructorPictureURI,
    courseName,
    instructorFirstname,
    instructorLastname,
    price,
    ratingCourse,
    totalReview,
    courseId,
    stateOfCourse
  } = data

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
    if (!username) {
      setRequiredLogin(true)
      return
    }

    setLoadingGetFreeCourse(true)
    const payload = {
      courseId: courseId
    }
    const response = await apiPrivate.post(axiosPrivate, URL_FREE_COURSE, payload)
    setLoadingGetFreeCourse(false)
    
    if (response.status === 201) {
      navigate(`/student/course`)
    } else {
      alert(JSON.stringify(response.message))
    }
  }

  const handleClickBuyNow = async () => {
    if (username) {
      navigate(`/payment/course/${courseId}`)
    } else {
      setRequiredLogin(true)
    }
  }

  const handleClickManageCourse = () => {
    navigate(`/instructor/course/${courseId}/assignment`)
  }

  const handleClickGotoThisCourse = () => {
    navigate(`/student/course/${courseId}/content`)
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
            alt={username}
            src={instructorPictureURI || "/static/images/avatar/1.jpg"} 
            sx={{ bgcolor: stringToColor(username), mt: 0.3 }}
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
              <Typography variant="body1" mt={2} textAlign='center'>{ratingCourse} ({totalReview})</Typography>
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
              stateOfCourse === 'instructor own' ?
                <Button variant="contained" onClick={handleClickManageCourse}>
                  MANAGE COURSE
                </Button>
              :
              stateOfCourse === 'student own' ?
                <Button variant="contained" onClick={handleClickGotoThisCourse}>
                  GO TO THIS COURSE
                </Button>
              :
              stateOfCourse === 'student' || stateOfCourse === 'public' ?
                price === 0 ?
                  <LoadingButton variant='contained' loading={loadingGetFreeCourse} onClick={handleClickGetFreeCourse}>
                    GET COURSE NOW
                  </LoadingButton>
                :
                  <Button variant="contained" onClick={handleClickBuyNow}>
                    BUY NOW
                  </Button>
              :
                <Skeleton variant='rectangular' width={100} height={30} />
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