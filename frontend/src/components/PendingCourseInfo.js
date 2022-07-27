import Paper from "@mui/material/Paper";
import Grid from "@mui/material/Grid";
import {Divider, Typography} from "@mui/material";
import React from "react";

const PendingCourseInfo = (props) => {
  const {category, requirement, overview} = props;

  return (
    <Paper variant='outlined'>
      <Grid container minHeight={400}>
        <Grid item padding={5} xs={12}>
          <Typography variant='h5' fontWeight='bold'>Category</Typography>
          <Divider sx={{mb: 3}}/>
          <Typography sx={{pl: 5}}>{category}</Typography>
          <Typography variant='h5' fontWeight='bold' sx={{mt: 5}}>Overview</Typography>
          <Divider sx={{mb: 3}}/>
          <Typography sx={{pl: 5}}>{overview}</Typography>
          <Typography variant='h5' fontWeight='bold' sx={{mt: 5}}>Requirement</Typography>
          <Divider sx={{mb: 3}}/>
          <Typography sx={{pl: 5}}>{requirement}</Typography>

        </Grid>
      </Grid>
    </Paper>
  );
}

export default PendingCourseInfo;