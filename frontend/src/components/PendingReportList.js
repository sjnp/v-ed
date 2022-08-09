import {useState} from "react";
import {Divider, Paper, Stack, ToggleButton, ToggleButtonGroup, Typography} from "@mui/material";
import PendingReviewReportList from "./PendingReviewReportList";
import PendingPostReportList from "./PendingPostReportList";
import PendingCommentReportList from "./PendingCommentReportList";

const PendingReportList = () => {
  const toggleButtons = [
    {
      label: "Reviews",
      element: <PendingReviewReportList/>
    },
    {
      label: "Posts",
      element: <PendingPostReportList/>
    },
    {
      label: "Comments",
      element: <PendingCommentReportList/>
    }
  ];

  const [type, setType] = useState(0);

  const handleTypeChange = (event, newType) => {
    if (newType !== null) {
      setType(newType);
    }
  };

  return (
    // <Stack spacing={3}>
      <Paper variant='outlined'>
        <Stack
          spacing={2}
          sx={{padding: 2}}
        >
          <Stack direction='row' justifyContent='space-between' alignItems='center'>
            <Typography variant='h6'>Reports</Typography>
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
    // </Stack>
  );
}

export default PendingReportList;