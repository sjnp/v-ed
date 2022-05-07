import {Divider, Paper, Stack, ToggleButton, ToggleButtonGroup, Typography} from "@mui/material";
import {useState} from "react";
import IncompleteCourseList from "./IncompleteCourseList";

const InstructorCourseList = () => {

  const toggleButtons = [
    {
      label: "Incomplete",
      element: <IncompleteCourseList />
    },
    {
      label: "Pending",
      element: null
    },
    {
      label: "Approved",
      element: null
    },
    {
      label: "Rejected",
      element: null
    }
  ];

  const [type, setType] = useState(0);

  const handleTypeChange = (event, newType) => {
    setType(newType);
  };

  return (
    <Paper variant='outlined'>
      <Stack
        spacing={2}
        sx={{padding: 3}}
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
  )
}

export default InstructorCourseList;