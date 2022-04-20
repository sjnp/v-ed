import {useDispatch, useSelector} from "react-redux";
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Chip,
  Input, Modal,
  Paper,
  Stack,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography
} from "@mui/material";
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import AddIcon from '@mui/icons-material/Add';
import CancelIcon from '@mui/icons-material/Cancel';
import PlayArrowRoundedIcon from '@mui/icons-material/PlayArrowRounded';
import React, {useEffect, useState} from "react";
import Button from "@mui/material/Button";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import {URL_CREATE_PAR_FOR_READ_WRITE_COURSE_VID, URL_UPDATE_COURSE_MATERIAL} from "../utils/url";
import axios from "axios";
import {setVideoUri} from "../features/createdCourseSlice";
import LinearProgressWithLabel from "./LinearProgressWithLabel";
import {uploadUtility} from "../utils/uploadUtility.js";
import ReactPlayer from "react-player";
import {Box} from "@mui/system";

const UploadCourseVideoForm = (props) => {

  const {courseId, handleNext, handleBack} = props;

  const axiosPrivate = useAxiosPrivate();
  const dispatch = useDispatch();

  const createdCourseChapters = useSelector((state) => state.createdCourse.value.chapters);
  const createdCoursePictureUrl = useSelector((state) => state.createdCourse.value.pictureUrl);

  const [expanded, setExpanded] = useState(false);
  const [courseVideoUrl, setCourseVideoUrl] = useState("");
  const [openVideoModal, setOpenVideoModal] = useState(false);
  const [sectionUploadStates, setSectionUploadStates] = useState(createdCourseChapters.map(chapter => chapter.sections.map(section => {
    return {
      file: null, progress: 0, isLoading: false, isSuccess: !!section.videoUri, isError: false
    }
  })))

  useEffect(() => {
    const updateChapters = async () => {
      if (courseId && axiosPrivate && createdCourseChapters) {
        try {
          await axiosPrivate.put(`${URL_UPDATE_COURSE_MATERIAL}?id=${courseId}`,
            {chapters: createdCourseChapters})
        } catch (err) {
          console.error(err);
        }
      }
    }
    updateChapters().then(res => console.log(createdCourseChapters))

  }, [axiosPrivate, courseId, createdCourseChapters]);

  const handleExpandChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  }

  const handleVideoUpload = (chapterIndex, sectionIndex) => async (event) => {
    const newFile = event.target.files[0];
    console.log(`chapterIndex: ${chapterIndex}, sectionIndex: ${sectionIndex}`);
    try {
      let newSectionUploadStates = [...sectionUploadStates];
      newSectionUploadStates[chapterIndex][sectionIndex].file = newFile;
      newSectionUploadStates[chapterIndex][sectionIndex].isLoading = true;
      setSectionUploadStates(newSectionUploadStates);

      const parUrl = await uploadUtility.createPreauthenticatedRequestForCourse(
        axiosPrivate,
        URL_CREATE_PAR_FOR_READ_WRITE_COURSE_VID,
        courseId,
        {
          chapterIndex: chapterIndex,
          sectionIndex: sectionIndex,
          fileName: newFile.name
        });
      const multipartUploadUri = await uploadUtility.createMultipartUploadUri(parUrl);
      const chunks = uploadUtility.splitFile(newFile);
      await multipartUpload(chapterIndex, sectionIndex, chunks, multipartUploadUri);
    } catch (err) {
      let newSectionUploadStates = [...sectionUploadStates];
      newSectionUploadStates[chapterIndex][sectionIndex].file = null;
      newSectionUploadStates[chapterIndex][sectionIndex].isLoading = false;
      setSectionUploadStates(newSectionUploadStates);
      console.error(err);
    }
  }

  const multipartUpload = async (chapterIndex, sectionIndex, chunks, multipartUploadUri) => {
    let promises = [];
    for (let i = 0; i < chunks.length; i++) {
      promises.push(
        axios.put(
          `${multipartUploadUri}${i + 1}`,
          chunks[i])
          .then(response => {
            const prevProgress = sectionUploadStates[chapterIndex][sectionIndex].progress;
            if (prevProgress + 100 / chunks.length > 100) {
              let newSectionUploadStates = [...sectionUploadStates];
              newSectionUploadStates[chapterIndex][sectionIndex].progress = 100;
              setSectionUploadStates(newSectionUploadStates);
            } else {
              let newSectionUploadStates = [...sectionUploadStates];
              newSectionUploadStates[chapterIndex][sectionIndex].progress = prevProgress + 100 / chunks.length;
              setSectionUploadStates(newSectionUploadStates);
            }
            console.log(`chuck ${i}: complete`);
          })
          .catch(err => console.error(err))
      );
    }

    await Promise.all(promises).then(async () => {
      await axios.post(`${multipartUploadUri}`);
      console.log(`ch:${chapterIndex} sec:${sectionIndex} complete`);
      let newSectionUploadStates = [...sectionUploadStates]
      newSectionUploadStates[chapterIndex][sectionIndex].isLoading = false;
      newSectionUploadStates[chapterIndex][sectionIndex].isSuccess = true;
      newSectionUploadStates[chapterIndex][sectionIndex].isError = false;
      newSectionUploadStates[chapterIndex][sectionIndex].progress = 0;
      setSectionUploadStates(newSectionUploadStates);
      await updateCourseVideoMaterials(chapterIndex, sectionIndex, multipartUploadUri);

    })
  }

  const updateCourseVideoMaterials = async (chapterIndex, sectionIndex, multipartUploadUri) => {
    const videoUri = multipartUploadUri.split("/u/")[1].split("/id/")[0];
    dispatch(setVideoUri({
      chapterIndex: chapterIndex,
      sectionIndex: sectionIndex,
      videoUri: videoUri
    }));
  }

  const playVideo = (chapterIndex, sectionIndex) => async () => {
    setOpenVideoModal(true);
    const parUrl = await uploadUtility.createPreauthenticatedRequestForCourse(
      axiosPrivate,
      URL_CREATE_PAR_FOR_READ_WRITE_COURSE_VID,
      courseId,
      {
        chapterIndex: chapterIndex,
        sectionIndex: sectionIndex,
        fileName: createdCourseChapters[chapterIndex].sections[sectionIndex].videoUri
      });
    setCourseVideoUrl(parUrl);
  }
  const deleteCourseVideoMaterials = (chapterIndex, sectionIndex) => async () => {
    const emptyUri = "";
    dispatch(setVideoUri({
      chapterIndex: chapterIndex,
      sectionIndex: sectionIndex,
      videoUri: emptyUri
    }));
    let newSectionUploadStates = [...sectionUploadStates];
    newSectionUploadStates[chapterIndex][sectionIndex].file = null;
    newSectionUploadStates[chapterIndex][sectionIndex].isLoading = false;
    newSectionUploadStates[chapterIndex][sectionIndex].isSuccess = false;
    setSectionUploadStates(newSectionUploadStates);
  }

  const handleBackOnClick = () => {
    handleBack();
  }

  return (<>
    {createdCourseChapters.map((chapter, chapterIndex) => (<Accordion
      key={chapterIndex}
      expanded={expanded === chapterIndex}
      onChange={handleExpandChange(chapterIndex)}
      variant='outlined'
      sx={{
        borderColor: expanded === chapterIndex ? 'white' : null, bgcolor: expanded === chapterIndex ? 'grey.200' : null
      }}
    >
      <AccordionSummary expandIcon={<ExpandMoreIcon/>}>
        <Typography component='h3' variant='h6'>
          Chapter {chapterIndex + 1} : {chapter.name}
        </Typography>
      </AccordionSummary>
      <AccordionDetails>
        <TableContainer component={Paper}>
          <Table style={{tableLayout: 'fixed'}}>
            <TableHead>
              <TableRow>
                <TableCell>Section</TableCell>
                <TableCell>Video</TableCell>
                <TableCell>Handouts</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {chapter.sections.map((section, sectionIndex) => (
                <TableRow style={{verticalAlign: 'top'}}
                          key={sectionIndex}
                          sx={{'&:last-child td, &:last-child th': {border: 0}}}
                >
                  <TableCell component="th" sx={{pt: 3}}>
                    Section {sectionIndex + 1} : {section.name}
                  </TableCell>
                  <TableCell>
                    {sectionUploadStates[chapterIndex][sectionIndex].file === null && !section.videoUri &&
                      <Stack direction='row'>
                        <label htmlFor={`video-button-file-${chapterIndex}${sectionIndex}`}>
                          <Input
                            sx={{display: 'none'}}
                            type="file"
                            id={`video-button-file-${chapterIndex}${sectionIndex}`}
                            accept="video/*"
                            onChange={handleVideoUpload(chapterIndex, sectionIndex)}
                          />
                          <Chip
                            icon={<AddIcon/>}
                            color="primary"
                            label="Add"
                            component="span"
                            sx={{mr: 1, mb: 1}}
                            onClick={() => {
                            }}/>
                        </label>
                        {sectionUploadStates[chapterIndex][sectionIndex].isError &&
                          <Typography sx={{color: "grey.600", mt: 0.5}}>Must be .mkv or .mp4</Typography>}
                      </Stack>
                    }
                    {sectionUploadStates[chapterIndex][sectionIndex].isLoading &&
                      <LinearProgressWithLabel
                        value={sectionUploadStates[chapterIndex][sectionIndex].progress}
                        fileName={sectionUploadStates[chapterIndex][sectionIndex].file.name}
                      />
                    }
                    {sectionUploadStates[chapterIndex][sectionIndex].isSuccess &&
                      <>
                        <Chip
                          label={sectionUploadStates[chapterIndex][sectionIndex].file === null
                            ? section.videoUri
                            : sectionUploadStates[chapterIndex][sectionIndex].file.name
                          }
                          variant="outlined"
                          icon={<PlayArrowRoundedIcon/>}
                          onClick={playVideo(chapterIndex, sectionIndex)}
                          onDelete={deleteCourseVideoMaterials(chapterIndex, sectionIndex)}
                          deleteIcon={<CancelIcon/>}
                        />
                        <Modal
                          open={openVideoModal}
                          onClose={() => setOpenVideoModal(false)}
                        >
                          <Box
                            sx={{
                              position: 'absolute',
                              top: '50%',
                              left: '50%',
                              transform: 'translate(-50%, -50%)',
                            }}
                          >
                            <ReactPlayer
                              url={courseVideoUrl}
                              // light={createdCoursePictureUrl}
                              controls={true}
                              playing={true}
                              style={{ margin: 'auto' }}
                            />
                          </Box>
                        </Modal>
                      </>

                    }

                  </TableCell>
                  <TableCell>
                    <label htmlFor={`button-file-${chapterIndex}${sectionIndex}`}>
                      <Input
                        sx={{display: 'none'}}
                        type="file"
                        id={`button-file-${chapterIndex}${sectionIndex}`}
                        // onChange={handleUpload}
                      />
                      <Chip
                        icon={<AddIcon/>}
                        color="primary"
                        label="Add"
                        component="span"
                        sx={{mr: 1, mb: 1}}
                        onClick={() => {
                        }}/>
                    </label>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>

          </Table>
        </TableContainer>
      </AccordionDetails>

    </Accordion>))}
    <Button
      variant='contained'
      size='large'
      onClick={() => {
        console.log(sectionUploadStates)
        console.log(createdCourseChapters)
      }}
      sx={{mt: 2}}
    >
      Show all
    </Button>
    <Button
      size='large'
      onClick={handleBackOnClick}
      sx={{mt: 2}}
    >
      Back
    </Button>
  </>);
}

export default UploadCourseVideoForm;