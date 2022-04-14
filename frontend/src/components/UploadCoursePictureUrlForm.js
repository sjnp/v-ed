import {Divider, IconButton, Input, Paper, Stack} from "@mui/material";
import {useEffect, useState} from "react";
import axios from "axios";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import {URL_CREATE_PAR_FOR_COURSE_PIC, URL_SAVE_COURSE_PICTURE} from "../utils/url";
import PropTypes from "prop-types";
import Button from "@mui/material/Button";
import LinearProgressWithLabel from "./LinearProgressWithLabel";

import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import CancelIcon from '@mui/icons-material/Cancel';
import Typography from "@mui/material/Typography";
import {useDispatch, useSelector} from "react-redux";
import InstructorCourseCard from "./InstructorCourseCard";
import {Box} from "@mui/system";
import Grid from "@mui/material/Grid";
import {setPictureUrl} from "../features/createdCourseSlice";


LinearProgressWithLabel.propTypes = {
  /**
   * The value of the progress indicator for the determinate and buffer variants.
   * Value between 0 and 100.
   */
  value: PropTypes.number.isRequired,
};

const UploadCoursePictureUrlForm = (props) => {

  const {courseId} = props;
  const axiosPrivate = useAxiosPrivate();
  const dispatch = useDispatch();
  const createdCourseName = useSelector((state) => state.createdCourse.value.name);
  const createdCoursePrice = useSelector((state) => state.createdCourse.value.price);
  const createdCoursePictureUrl = useSelector((state) => state.createdCourse.value.pictureUrl);

  const [file, setFile] = useState(null);
  const [progress, setProgress] = useState(0);
  const [isLoading, setIsLoading] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);
  const [rerenderPictureUrl, setRerenderPictureUrl] = useState('');

  useEffect(() => {
    setRerenderPictureUrl(createdCoursePictureUrl)
    if (createdCoursePictureUrl) {
      setIsSuccess(true);
    }
  }, [createdCoursePictureUrl])

  const handleUpload = async (event) => {
    const newFile = event.target.files[0];

    try {
      setFile(newFile);
      setIsLoading(true);
      const parUrl = await createPreauthenticatedRequest(newFile.name);
      const multipartUploadUri = parUrl.split("/p/")[0] + await createMultipartUploadUri(parUrl);
      const chunks = splitFile(newFile);
      await multipartUpload(chunks, multipartUploadUri);
      setTimeout(async () => {
        setIsLoading(false)
        setIsSuccess(true)
        const pictureUrl = await saveCoursePictureUrl(parUrl.split("/o/")[1])
        console.log(pictureUrl)
        dispatch(setPictureUrl({pictureUrl: pictureUrl}))
        setRerenderPictureUrl(parUrl);
        setRerenderPictureUrl(pictureUrl)
      }, 1000);
    } catch (err) {
      console.error(err);
    }
  }

  const splitFile = (newFile) => {
    const chunkSize = 1024 * 1024;
    const chunks = []
    for (let start = 0; start < newFile.size; start += chunkSize) {
      const chunk = newFile.slice(start, start + chunkSize);
      chunks.push(chunk);
    }
    console.log(newFile);
    console.log(chunks);
    return chunks;
  }

  const createPreauthenticatedRequest = async (fileName) => {
    const response = await axiosPrivate.post(`${URL_CREATE_PAR_FOR_COURSE_PIC}?id=${courseId}`,
      {fileName: fileName});
    return response.data.preauthenticatedRequestUrl;
  }

  const createMultipartUploadUri = async (parUrl) => {
    const config = {
      method: 'put',
      url: parUrl,
      headers: {
        'opc-multipart': 'true'
      }
    };
    const response = await axios(config);
    return response.data.accessUri;
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
      const response = await axiosPrivate.put(`${URL_SAVE_COURSE_PICTURE}?id=${courseId}`,
        {objectName: objectName})
      return response.data.pictureUrl;
    } catch (err) {
      console.error(err);
    }
  }

  return (
    <Paper
      variant="outlined"
      sx={{padding: 2}}
    >
      <Stack alignItems="stretch">
        <Stack direction="row" alignItems="center" spacing={2}>
          {file === null && !createdCoursePictureUrl
            ? <label htmlFor="contained-button-file">
              <Input sx={{display: 'none'}} accept="image/*" id="contained-button-file" type="file"
                     onChange={handleUpload}/>
              <Button component="span" variant="contained" startIcon={<CloudUploadIcon/>}>
                Upload Picture
              </Button>
            </label>
            : <Stack direction="row" alignItems="center" justifyContent='space-between'>
              <Typography>
                {file === null
                  ? createdCoursePictureUrl.split("/o/")[1]
                  : file.name
                }
              </Typography>
              {isSuccess &&
                <IconButton>
                  <CancelIcon/>
                </IconButton>
              }
            </Stack>
          }
        </Stack>
        {isLoading && <LinearProgressWithLabel value={progress}/>}
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
                  pictureUrl={rerenderPictureUrl}
                  isIncomplete={true}
                />
              </Grid>
            </Grid>
          </>
        }

      </Stack>
    </Paper>
  )
}

export default UploadCoursePictureUrlForm;