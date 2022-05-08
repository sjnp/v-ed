import { Paper, StepLabel, Stepper, Step, Typography, StepContent } from "@mui/material"
import { Box } from "@mui/system";
import {useEffect, useState} from 'react';
import SectionDetailsForm from "./SectionDetailsForm";
import CourseAssignmentForm from "./CourseAssignmentForm";
import CourseDetailsForm from "./CourseDetailsForm";
import ChapterDetailsForm from "./ChapterDetailsForm";
import service from "../services/service";
import {useDispatch} from "react-redux";
import {resetCourse} from "../features/createdCourseSlice";

const CreateCourseForm = () => {

  const [categories, setCategories] = useState(null);
  const dispatch = useDispatch();

  useEffect(() => {
    service.getAllCategories()
      .then(res => setCategories(res.data))
      .catch(err => console.error(err));
    dispatch(resetCourse());
  }, [dispatch])


  const steps = [
    'Course details',
    'Chapter details',
    'Section details',
    'Course assignments'
  ]

  const [activeStep, setActiveStep] = useState(0);

  const handleNext = () => { setActiveStep(activeStep + 1) };
  const handleBack = () => { setActiveStep(activeStep - 1) };

  const getStepContent = (step) => {
    switch (step) {
      case 0:
        return <CourseDetailsForm handleNext={handleNext} categories={categories} />;
      case 1:
        return <ChapterDetailsForm handleNext={handleNext} handleBack={handleBack} />;
      case 2:
        return <SectionDetailsForm handleNext={handleNext} handleBack={handleBack} />;
      case 3:
        return <CourseAssignmentForm handleNext={handleNext} handleBack={handleBack} />;
      default:
        throw new Error('Unknown step');
    }
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
          Create course
        </Typography>
        <Stepper activeStep={activeStep} orientation='vertical' sx={{ maxWidth: '100' }}>
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

export default CreateCourseForm;