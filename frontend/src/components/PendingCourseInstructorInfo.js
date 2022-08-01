import Paper from "@mui/material/Paper";
import Grid from "@mui/material/Grid";
import {Avatar, Divider, Typography} from "@mui/material";
import stringToColor from "./stringToColor";
import React from "react";

const PendingCourseInstructorInfo = (props) => {
  const {instructorInfo} = props;
  const NAElement = <Typography variant='caption' color='gray'>N/A</Typography>

  return (
    <Paper variant='outlined'>
      <Grid container minHeight={400}>
        <Grid item xs={3} padding={5}>
          <Avatar
            alt={instructorInfo.firstName}
            src={instructorInfo.profilePicUri || "/static/images/avatar/2.jpg"}
            sx={{bgcolor: stringToColor(instructorInfo.firstName), width: 150, height: 150}}
          />
        </Grid>
        <Grid item xs={9} paddingLeft={2} paddingRight={5} paddingTop={5}>
          <Typography variant='h5' fontWeight='bold'>Instructor</Typography>
          <Divider sx={{mb: 3}}/>
          <Typography>{`${instructorInfo.firstName} ${instructorInfo.lastName}`}</Typography>
          <Typography variant='h5' fontWeight='bold' sx={{mt: 5}}>Biography</Typography>
          <Divider sx={{mb: 3}}/>
          {instructorInfo.biography
            ? <Typography>{instructorInfo.biography}</Typography>
            : NAElement
          }
          <Typography variant='h5' fontWeight='bold' sx={{mt: 5}}>Occupation</Typography>
          <Divider sx={{mb: 3}}/>
          {instructorInfo.occupation
            ? <Typography>{instructorInfo.occupation}</Typography>
            : NAElement
          }
        </Grid>
      </Grid>
    </Paper>
  );
}

export default PendingCourseInstructorInfo;