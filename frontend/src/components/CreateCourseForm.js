import { Paper, StepLabel, Stepper, Step, Typography, StepContent } from "@mui/material"
import { Box } from "@mui/system";
import { useState } from 'react';

const CreateCourseForm = () => {
  const steps = [
    'Course details',
    'Section details',
    'Chapter details',
    'Course materials',
    'Review your course'
  ]

  const [activeStep, setActiveStep] = useState(0);

  const handleNext = () => { setActiveStep(activeStep + 1) };
  const handleBack = () => { setActiveStep(activeStep - 1) };

  const getStepContent = (step) => {
    switch (step) {
      case 0:
        return;
      case 1:
        return;
      case 2:
        return;
      case 3:
        return;
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
          margin: 3,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center'
        }}
      >
        <Typography
          component='h1'
          variant='h5'>
          Create Course
        </Typography>
        <Stepper activeStep={activeStep} orientation='vertical'>
          {steps.map(step => (
            <Step key={step}>
              <StepLabel>
                {step}
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