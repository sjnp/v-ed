import {Divider, Paper, Stack, ToggleButton, ToggleButtonGroup, Typography} from "@mui/material";
import {useState} from "react";
import IncompleteCourseList from "./IncompleteCourseList";
import InstructorPendingCourseList from "./InstructorPendingCourseList";
import InstructorApprovedCourseList from "./InstructorApprovedCourseList";
import InstructorRejectedCourseList from "./InstructorRejectedCourseList";
import InstructorPublishedCourseList from "./InstructorPublishedCourseList";
import InstructorBilling from "./InstructorBilling";

const InstructorCourseList = () => {

  const toggleButtons = [
    {
      label: "Incomplete",
      element: <IncompleteCourseList/>
    },
    {
      label: "Pending",
      element: <InstructorPendingCourseList/>
    },
    {
      label: "Approved",
      element: <InstructorApprovedCourseList/>
    },
    {
      label: "Rejected",
      element: <InstructorRejectedCourseList/>
    }
  ];

  const [type, setType] = useState(0);

  const handleTypeChange = (event, newType) => {
    if (newType !== null) {
      setType(newType);
    }
  };

  return (
    <Stack spacing={3}>
      <Paper variant='outlined'>
        <Stack
          spacing={2}
          sx={{padding: 2}}
        >
          <Stack direction='row' justifyContent='space-between' alignItems='center'>
            <Typography variant='h5'>Your pending courses</Typography>
            <ToggleButtonGroup
              color='primary'
              size='medium'
              exclusive
              value={type}
              onChange={handleTypeChange}
            >
              {toggleButtons.map((button, index) =>
                <ToggleButton value={index} key={index}>
                  {button.label}
                </ToggleButton>
              )}
            </ToggleButtonGroup>
          </Stack>
          <Divider/>
          {toggleButtons[type].element}
        </Stack>
      </Paper>
      <Paper variant='outlined'>
        <Stack
          spacing={2}
          sx={{padding: 2}}
        >
          <Stack direction='row' justifyContent='space-between' alignItems='center'>
            <Typography variant='h5'>Your published courses</Typography>
          </Stack>
          <Divider/>
          {/*TO DO InstructorPublishedCourseList*/}
          <InstructorPublishedCourseList/>
        </Stack>
      </Paper>
    </Stack>

  )
}

export default InstructorCourseList;