import {Paper, StepLabel, Stepper, Step, Typography, StepContent, CircularProgress, Stack} from "@mui/material"
import {Box} from "@mui/system";
import {useEffect, useState} from 'react';
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import {URL_GET_INCOMPLETE_COURSE} from "../utils/url";
import {useDispatch} from "react-redux";
import {resetCourse, setChapters, setName, setPictureUrl, setPrice} from "../features/createdCourseSlice";
import UploadCoursePictureUrlForm from "./UploadCoursePictureUrlForm";
import UploadVideoAndHandoutForm from "./UploadVideoAndHandoutForm";
import {useLocation, useNavigate} from "react-router-dom";
import UploadCourseConfirmationForm from "./UploadCourseConfirmationForm";

const UploadCourseMaterialForm = (props) => {

  const {courseId} = props;
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();
  const location = useLocation();
  const dispatch = useDispatch();
  const [isFinishFetching, setIsFinishFetching] = useState(false);

  useEffect(() => {
    dispatch(resetCourse());
    const url = URL_GET_INCOMPLETE_COURSE
      .replace('{courseId}', courseId)
    axiosPrivate.get(url)
      .then(response => {
        dispatch(setName({name: response.data.name}));
        dispatch(setPrice({price: response.data.price}));
        dispatch(setPictureUrl({pictureUrl: response.data.pictureUrl}));
        dispatch(setChapters({chapters: response.data.chapters}));
        setIsFinishFetching(true);
      })
      .catch(err => {
        console.error(err);
        navigate('/', { state: { from: location }, replace: true });
      })
  }, [axiosPrivate, courseId, dispatch, location, navigate]);

  const steps = [
    'Course Picture',
    'Course Videos & Handouts',
    'Confirmation'
  ]

  const [activeStep, setActiveStep] = useState(0);

  const handleNext = () => {
    setActiveStep(activeStep + 1)
  };
  const handleBack = () => {
    setActiveStep(activeStep - 1)
  };

  const getStepContent = (step) => {
    switch (step) {
      case 0:
        return <UploadCoursePictureUrlForm courseId={courseId} handleNext={handleNext}/>;
      case 1:
        return <UploadVideoAndHandoutForm courseId={courseId} handleNext={handleNext} handleBack={handleBack}/>;
      case 2:
        return <UploadCourseConfirmationForm courseId={courseId} handleBack={handleBack} />
      default:
        throw new Error('Unknown step');
    }
  }

  if (!isFinishFetching) {
    return (
      <Stack alignItems='center' sx={{mt: 5}}>
        <CircularProgress/>
      </Stack>
    )
  }

  return (
    <Paper elevation={3}>
      <Box
        sx={{
          marginTop: 2,
          padding: 3,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'stretch'
        }}
      >
        <Typography
          marginBottom={3}
          component='h2'
          variant='h4'>
          Upload course materials
        </Typography>
        <Stepper activeStep={activeStep} orientation='vertical' sx={{maxWidth: '100'}}>
          {steps.map((step, index) => (
            <Step key={index}>
              <StepLabel>
                <Typography component='h2' variant='h6'>
                  {step}
                </Typography>
              </StepLabel>
              <StepContent>
                {getStepContent(activeStep)}
              </StepContent>
            </Step>
          ))}
        </Stepper>
      </Box>


    </Paper>
  )
}

export default UploadCourseMaterialForm;