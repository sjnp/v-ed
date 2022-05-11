import LoadingButton from "@mui/lab/LoadingButton";
import CloudUploadIcon from "@mui/icons-material/CloudUpload";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import {Alert, Paper, Stack, Typography} from "@mui/material";
import React, {useState} from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import {URL_SUBMIT_INCOMPLETE_COURSE} from "../utils/url";
import {useNavigate} from "react-router-dom";

const UploadCourseConfirmationForm = (props) => {
  const {courseId, handleBack} = props;
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();

  const [isLoading, setIsLoading] = useState(false)

  const handleCourseSubmit = async () => {
    setIsLoading(true);
    try {
      const response = await axiosPrivate.put(
        URL_SUBMIT_INCOMPLETE_COURSE.replace('{courseId}', courseId)
      )
      console.log(response.data);
      navigate(`/instructor`);
    } catch (err) {
      setIsLoading(false);
      console.error(err);
    }
  }

  return (<>
    <Paper sx={{padding: 2}}>
      <Typography variant='h6' sx={{pl: 2, pb: 1}}>Submit this course?</Typography>
      <Alert severity="info">You cannot edit this course after submitting.</Alert>
    </Paper>
    <Stack spacing={2} direction='row' sx={{mt: 2}}>
      <LoadingButton
        variant='contained'
        size='large'
        onClick={handleCourseSubmit}
        loading={isLoading}
        loadingPosition="start"
        startIcon={<CloudUploadIcon/>}
      >
        Confirm
      </LoadingButton>
      <LoadingButton
        size='large'
        variant='outlined'
        onClick={handleBack}
        loading={isLoading}
        loadingPosition="start"
        startIcon={<ArrowBackIcon/>}
      >
        Back
      </LoadingButton>
    </Stack>
  </>);
}

export default UploadCourseConfirmationForm;