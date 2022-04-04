import { Paper, StepLabel, Stepper, Step, Typography, StepContent } from "@mui/material"
import { Box } from "@mui/system";
import { useState } from 'react';
import ChapterDetailsForm from "./ChapterDetailsForm";
import CourseAssignmentForm from "./CourseAssignmentForm";
import CourseDetailsForm from "./CourseDetailsForm";
import SectionDetailsForm from "./SectionDetailsForm";

const CreateCourseForm = () => {
  const steps = [
    'Course details',
    'Section details',
    'Chapter details',
    'Course assignments',
    'Course materials',
    'Review your course'
  ]

  const [activeStep, setActiveStep] = useState(0);

  const handleNext = () => { setActiveStep(activeStep + 1) };
  const handleBack = () => { setActiveStep(activeStep - 1) };

  const getStepContent = (step) => {
    switch (step) {
      case 0:
        return <CourseDetailsForm handleNext={handleNext} />;
      case 1:
        return <SectionDetailsForm handleNext={handleNext} handleBack={handleBack} />;
      case 2:
        return <ChapterDetailsForm handleNext={handleNext} handleBack={handleBack} />;
      case 3:
        return <CourseAssignmentForm handleNext={handleNext} handleBack={handleBack} />;
      case 4:
        return;
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