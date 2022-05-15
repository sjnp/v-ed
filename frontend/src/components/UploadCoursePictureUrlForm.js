import {Chip, Divider, Input, Paper, Stack} from "@mui/material";
import React, {useState} from "react";
import axios from "axios";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import {
  URL_CREATE_PAR_FOR_READ_WRITE_COURSE_PIC,
  URL_DELETE_COURSE_PICTURE,
  URL_SAVE_COURSE_PICTURE
} from "../utils/url";
import PropTypes from "prop-types";
import Button from "@mui/material/Button";
import LinearProgressWithLabel from "./LinearProgressWithLabel";
import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import CancelIcon from '@mui/icons-material/Cancel';
import Typography from "@mui/material/Typography";
import Grid from "@mui/material/Grid";
import {useDispatch, useSelector} from "react-redux";
import InstructorCourseCard from "./InstructorCourseCard";
import {setPictureUrl} from "../features/createdCourseSlice";
import {uploadUtility} from "../utils/uploadUtility";

LinearProgressWithLabel.propTypes = {
  /**
   * The value of the progress indicator for the determinate and buffer variants.
   * Value between 0 and 100.
   */
  value: PropTypes.number.isRequired,
};

const UploadCoursePictureUrlForm = (props) => {

  const {courseId, handleNext} = props;
  const axiosPrivate = useAxiosPrivate();
  const dispatch = useDispatch();
  const createdCourseName = useSelector((state) => state.createdCourse.value.name);
  const createdCoursePrice = useSelector((state) => state.createdCourse.value.price);
  const createdCoursePictureUrl = useSelector((state) => state.createdCourse.value.pictureUrl);

  const [file, setFile] = useState(null);
  const [progress, setProgress] = useState(0);
  const [isLoading, setIsLoading] = useState(false);
  const [isSuccess, setIsSuccess] = useState(!!createdCoursePictureUrl);
  const [isError, setIsError] = useState(false);

  const handleUpload = async (event) => {
    const newFile = event.target.files[0];

    try {
      setFile(newFile);
      setIsLoading(true);
      const parUrl = await uploadUtility.createPreauthenticatedRequestForCourse(
        axiosPrivate,
        URL_CREATE_PAR_FOR_READ_WRITE_COURSE_PIC,
        courseId,
        {fileName: newFile.name}
      );
      const multipartUploadUri = await uploadUtility.createMultipartUploadUri(parUrl);
      const chunks = uploadUtility.splitFile(newFile);
      await multipartUpload(chunks, multipartUploadUri);
      setTimeout(async () => {
        setIsLoading(false)
        setIsSuccess(true)
        setIsError(false);
        const pictureUrl = await saveCoursePictureUrl(parUrl.split("/o/")[1])
        console.log(pictureUrl)
        dispatch(setPictureUrl({pictureUrl: pictureUrl}))
        setProgress(0);
      }, 1000);
    } catch (err) {
      setFile(null);
      setIsLoading(false);
      console.error(err);
    }
  }

  const multipartUpload = async (chunks, multipartUploadUri) => {
    let promises = [];
    for (let i = 0; i < chunks.length; i++) {
      promises.push(
        axios.put(
          `${multipartUploadUri}${i + 1}`,
          chunks[i])
          .then(response => {
            setProgress((prevProgress) => {
              if (prevProgress + 100 / chunks.length > 100) {
                return 100;
              }
              return prevProgress + 100 / chunks.length;
            })
            console.log(`chuck ${i}: complete`);
          })
          .catch(err => console.error(err))
      );
    }

    await Promise.all(promises).then(async () => {
      await axios.post(`${multipartUploadUri}`);
      console.log("complete")
    });
  }

  const saveCoursePictureUrl = async (objectName) => {
    try {
      const url = URL_SAVE_COURSE_PICTURE
        .replace('{courseId}', courseId)
        .replace('{pictureName}', objectName)
      const response = await axiosPrivate.put(url)
      return response.data.pictureUrl;
    } catch (err) {
      console.error(err);
    }
  }

  const handleDeletePicture = async () => {
    try {
      await axiosPrivate.delete(URL_DELETE_COURSE_PICTURE.replace('{courseId}', courseId))
      setIsSuccess(false);
      setFile(null);
      dispatch(setPictureUrl({pictureUrl: ""}));
    } catch (err) {
      console.error(err);
    }
  }

  const handleSubmit = () => {
    if (!isSuccess) {
      setIsError(true);
    } else {
      handleNext();
    }
  }

  return (
    <>
      <Paper
        variant="outlined"
        sx={{padding: 2, borderColor: isError ? 'red' : null}}
      >
        <Stack alignItems="stretch">
          <Stack direction="row" alignItems="center" spacing={2}>
            {file === null && !createdCoursePictureUrl &&
              <Stack direction="row" alignItems="center" spacing={2}>
                <label htmlFor="contained-button-file">
                  <Input
                    sx={{display: 'none'}}
                    type="file"
                    id="contained-button-file"
                    accept="image/*"
                    onChange={handleUpload}/>
                  <Button component="span" variant="contained" startIcon={<CloudUploadIcon/>}>
                    Upload Picture
                  </Button>
                </label>
                <Typography sx={{color: "grey.600"}}>Must be .png, .jpg or .jpeg</Typography>
              </Stack>
            }

            {isSuccess &&
              <Chip
                label={file === null ? createdCoursePictureUrl.split("/o/")[1] : file.name}
                variant="outlined"
                onDelete={handleDeletePicture}
                deleteIcon={<CancelIcon/>}
              />
            }
          </Stack>
          {isLoading && <LinearProgressWithLabel value={progress} fileName={file.name}/>}
          {isSuccess &&
            <>
              <Divider sx={{mt: 2, mb: 2}}/>
              <Typography variant='h6'>
                Preview
              </Typography>
              <Grid container justifyContent="center">
                <Grid item xs={3}>
                  <InstructorCourseCard
                    courseName={createdCourseName}
                    price={createdCoursePrice}
                    pictureUrl={createdCoursePictureUrl}
                    isIncomplete={true}
                  />
                </Grid>
              </Grid>
            </>
          }

        </Stack>
      </Paper>
      <Button
        variant='contained'
        size='large'
        onClick={handleSubmit}
        sx={{mt: 2}}
      >
        Continue
      </Button>
    </>
  )
}

export default UploadCoursePictureUrlForm;