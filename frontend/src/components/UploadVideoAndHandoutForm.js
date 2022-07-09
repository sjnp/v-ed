import {useDispatch, useSelector} from "react-redux";
import LoadingButton from '@mui/lab/LoadingButton';
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Box,
  Chip, CircularProgress, Divider,
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
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import ErrorIcon from '@mui/icons-material/Error';
import AddIcon from '@mui/icons-material/Add';
import CancelIcon from '@mui/icons-material/Cancel';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import PlayArrowRoundedIcon from '@mui/icons-material/PlayArrowRounded';
import React, {useEffect, useState} from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import {
  URL_CREATE_PAR_FOR_READ_WRITE_COURSE_HANDOUT,
  URL_CREATE_PAR_FOR_WRITE_COURSE_VID,
  URL_CREATE_PAR_FOR_READ_COURSE_VID,
  URL_UPDATE_COURSE_MATERIAL, URL_DELETE_INCOMPLETE_COURSE_HANDOUT
} from "../utils/url";
import axios from "axios";
import {removeHandoutUri, setHandoutUri, setVideoUri} from "../features/createdCourseSlice";
import LinearProgressWithLabel from "./LinearProgressWithLabel";
import {uploadUtility} from "../utils/uploadUtility.js";
import ReactPlayer from "react-player";
import {deleteUtility} from "../utils/deleteUtility";

const UploadVideoAndHandoutForm = (props) => {

  const {courseId, handleNext, handleBack} = props;

  const axiosPrivate = useAxiosPrivate();
  const dispatch = useDispatch();

  const createdCourseChapters = useSelector((state) => state.createdCourse.value.chapters);

  const [isLoadingVideo, setIsLoadingVideo] = useState(Array(createdCourseChapters.length).fill(false));
  const [isLoadingHandout, setIsLoadingHandout] = useState(Array(createdCourseChapters.length).fill(false));
  const [expanded, setExpanded] = useState(false);
  const [courseVideoUrl, setCourseVideoUrl] = useState("");
  const [openVideoModal, setOpenVideoModal] = useState(false);
  const [isFetchingVideoUrl, setIsFetchingVideoUrl] = useState(false);
  const [sectionVideoUploadStates, setSectionVideoUploadStates] = useState(createdCourseChapters
    .map(chapter => chapter.sections
      .map(section => {
        return {
          file: null,
          progress: 0,
          isLoading: false,
          isDeleting: false,
          isSuccess: !!section.videoUri,
          isError: false
        }
      })));
  const [sectionHandoutUploadStates, setSectionHandoutUploadStates] = useState(createdCourseChapters
    .map(chapter => chapter.sections
      .map(section => {
        if (section.handouts) {
          return section.handouts.map(handout => {
            return {
              file: null,
              objectName: handout.handoutUri,
              progress: 0,
              isLoading: false,
              isDeleting: false,
              isSuccess: !!handout.handoutUri,
              isError: false
            }
          });
        } else {
          return [];
        }
      })
    )
  );

  useEffect(() => {
    const updateChapters = async () => {
      if (courseId && axiosPrivate && createdCourseChapters) {
        try {
          const url = URL_UPDATE_COURSE_MATERIAL.replace('{courseId}', courseId)
          await axiosPrivate.put(url,
            {chapters: createdCourseChapters})
        } catch (err) {
          console.error(err);
        }
      }
    }
    updateChapters().then(res => console.log(createdCourseChapters))

  }, [axiosPrivate, courseId, createdCourseChapters]);

  useEffect(() => {
    if (sectionVideoUploadStates) {
      const newIsLoading = sectionVideoUploadStates
        .map(chapter => chapter
          .some(section => section.isLoading || section.isDeleting));
      setIsLoadingVideo(newIsLoading);
    }
  }, [sectionVideoUploadStates]);

  useEffect(() => {
    if (sectionHandoutUploadStates) {
      const newIsLoading = sectionHandoutUploadStates
        .map(chapter => chapter
          .some(section => section
            .some(handout => handout.isLoading || handout.isDeleting)));
      setIsLoadingHandout(newIsLoading);
    }
  }, [sectionHandoutUploadStates])

  const handleExpandChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  }

  const handleVideoUpload = (chapterIndex, sectionIndex) => async (event) => {
    const newFile = event.target.files[0];
    console.log(`chapterIndex: ${chapterIndex}, sectionIndex: ${sectionIndex}`);
    try {
      let newSectionUploadStates = [...sectionVideoUploadStates];
      newSectionUploadStates[chapterIndex][sectionIndex].file = newFile;
      newSectionUploadStates[chapterIndex][sectionIndex].isLoading = true;
      setSectionVideoUploadStates(newSectionUploadStates);

      const parUrl = await uploadUtility.createPreauthenticatedRequestForCourse(
        axiosPrivate,
        URL_CREATE_PAR_FOR_WRITE_COURSE_VID,
        courseId,
        {
          chapterIndex: chapterIndex,
          sectionIndex: sectionIndex,
          fileName: newFile.name
        });
      const multipartUploadUri = await uploadUtility.createMultipartUploadUri(parUrl);
      const chunks = uploadUtility.splitFile(newFile);
      await multipartVideoUpload(chapterIndex, sectionIndex, chunks, multipartUploadUri);
    } catch (err) {
      let newSectionUploadStates = [...sectionVideoUploadStates];
      newSectionUploadStates[chapterIndex][sectionIndex].file = null;
      newSectionUploadStates[chapterIndex][sectionIndex].isLoading = false;
      setSectionVideoUploadStates(newSectionUploadStates);
      console.error(err);
    }
  }

  const multipartVideoUpload = async (chapterIndex, sectionIndex, chunks, multipartUploadUri) => {
    let promises = [];
    for (let i = 0; i < chunks.length; i++) {
      promises.push(
        axios.put(
          `${multipartUploadUri}${i + 1}`,
          chunks[i])
          .then(response => {
            const prevProgress = sectionVideoUploadStates[chapterIndex][sectionIndex].progress;
            if (prevProgress + 100 / chunks.length > 100) {
              let newSectionUploadStates = [...sectionVideoUploadStates];
              newSectionUploadStates[chapterIndex][sectionIndex].progress = 100;
              setSectionVideoUploadStates(newSectionUploadStates);
            } else {
              let newSectionUploadStates = [...sectionVideoUploadStates];
              newSectionUploadStates[chapterIndex][sectionIndex].progress = prevProgress + 100 / chunks.length;
              setSectionVideoUploadStates(newSectionUploadStates);
            }
            console.log(`chuck ${i}: complete`);
          })
          .catch(err => console.error(err))
      );
    }

    await Promise.all(promises).then(async () => {
      await axios.post(`${multipartUploadUri}`);
      console.log(`ch:${chapterIndex} sec:${sectionIndex} complete`);
      let newSectionUploadStates = [...sectionVideoUploadStates]
      newSectionUploadStates[chapterIndex][sectionIndex].isLoading = false;
      newSectionUploadStates[chapterIndex][sectionIndex].isSuccess = true;
      newSectionUploadStates[chapterIndex][sectionIndex].isError = false;
      newSectionUploadStates[chapterIndex][sectionIndex].progress = 0;
      setSectionVideoUploadStates(newSectionUploadStates);
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
    setIsFetchingVideoUrl(true);
    // const parUrl = await uploadUtility.createPreauthenticatedRequestForCourse(
    //   axiosPrivate,
    //   URL_CREATE_PAR_FOR_READ_WRITE_COURSE_VID,
    //   courseId,
    //   {
    //     chapterIndex: chapterIndex,
    //     sectionIndex: sectionIndex,
    //     fileName: createdCourseChapters[chapterIndex].sections[sectionIndex].videoUri
    //   });
    const fileName = createdCourseChapters[chapterIndex].sections[sectionIndex].videoUri;
    const createParUrl = URL_CREATE_PAR_FOR_READ_COURSE_VID
      .replace('{courseId}', courseId)
      .replace('{chapterIndex}', chapterIndex)
      .replace('{sectionIndex}', sectionIndex)
      .replace('{fileName}', fileName);
    const response = await axiosPrivate.get(createParUrl);
    const parUrl = response.data.preauthenticatedRequestUrl;
    setCourseVideoUrl(parUrl);
    setIsFetchingVideoUrl(false);
  }

  const handleCloseVideoModal = () => {
    setCourseVideoUrl("");
    setOpenVideoModal(false);
  }

  const deleteCourseVideoMaterials = (chapterIndex, sectionIndex) => async () => {
    const emptyUri = "";
    dispatch(setVideoUri({
      chapterIndex: chapterIndex,
      sectionIndex: sectionIndex,
      videoUri: emptyUri
    }));
    let newSectionUploadStates = [...sectionVideoUploadStates];
    newSectionUploadStates[chapterIndex][sectionIndex].file = null;
    newSectionUploadStates[chapterIndex][sectionIndex].isLoading = false;
    newSectionUploadStates[chapterIndex][sectionIndex].isSuccess = false;
    setSectionVideoUploadStates(newSectionUploadStates);
  }

  const handleHandoutUpload = (chapterIndex, sectionIndex) => async (event) => {
    const newFiles = event.target.files;
    for (const newFile of newFiles) {
      try {
        let newSectionHandoutUploadStates = [...sectionHandoutUploadStates];
        const newHandout = {
          file: newFile,
          objectName: null,
          progress: 0,
          isLoading: true,
          isDeleting: false,
          isSuccess: false,
          isError: false
        }
        newSectionHandoutUploadStates[chapterIndex][sectionIndex].push(newHandout);
        const handoutIndex = newSectionHandoutUploadStates[chapterIndex][sectionIndex].length - 1;
        setSectionHandoutUploadStates(newSectionHandoutUploadStates);

        const parUrl = await uploadUtility.createPreauthenticatedRequestForCourse(
          axiosPrivate,
          URL_CREATE_PAR_FOR_READ_WRITE_COURSE_HANDOUT,
          courseId,
          {
            chapterIndex: chapterIndex,
            sectionIndex: sectionIndex,
            fileName: newFile.name
          }
        );
        const multipartUploadUri = await uploadUtility.createMultipartUploadUri(parUrl);
        const chunks = uploadUtility.splitFile(newFile);
        await multipartHandoutUpload(chapterIndex, sectionIndex, handoutIndex, chunks, multipartUploadUri);
      } catch (err) {
        let newSectionHandoutUploadStates = [...sectionHandoutUploadStates];
        newSectionHandoutUploadStates[chapterIndex][sectionIndex] = newSectionHandoutUploadStates[chapterIndex][sectionIndex]
          .filter(handout => handout.file !== newFile);
        setSectionHandoutUploadStates(newSectionHandoutUploadStates);
        console.error(err);
      }
    }
    event.target.value = null;
  }

  const multipartHandoutUpload = async (chapterIndex, sectionIndex, handoutIndex, chunks, multipartUploadUri) => {
    let promises = [];
    for (let i = 0; i < chunks.length; i++) {
      promises.push(
        axios.put(
          `${multipartUploadUri}${i + 1}`,
          chunks[i])
          .then(response => {
            const prevProgress = sectionHandoutUploadStates[chapterIndex][sectionIndex][handoutIndex].progress;
            if (prevProgress + 100 / chunks.length > 100) {
              let newSectionHandoutUploadStates = [...sectionHandoutUploadStates];
              newSectionHandoutUploadStates[chapterIndex][sectionIndex][handoutIndex].progress = 100;
              setSectionHandoutUploadStates(newSectionHandoutUploadStates);
            } else {
              let newSectionHandoutUploadStates = [...sectionHandoutUploadStates];
              newSectionHandoutUploadStates[chapterIndex][sectionIndex][handoutIndex].progress = prevProgress + 100 / chunks.length;
              setSectionHandoutUploadStates(newSectionHandoutUploadStates);
            }
            console.log(`handout chunk ${i}: complete`);
          })
          .catch(err => console.error(err))
      );
    }
    await Promise.all(promises).then(async () => {
      await axios.post(`${multipartUploadUri}`);
      console.log(`ch:${chapterIndex} sec:${sectionIndex} handout:${handoutIndex} complete`);
      const handoutUri = multipartUploadUri.split("/u/")[1].split("/id/")[0];
      let newSectionHandoutUploadStates = [...sectionHandoutUploadStates];
      newSectionHandoutUploadStates[chapterIndex][sectionIndex][handoutIndex].isLoading = false;
      newSectionHandoutUploadStates[chapterIndex][sectionIndex][handoutIndex].isSuccess = true;
      newSectionHandoutUploadStates[chapterIndex][sectionIndex][handoutIndex].isError = false;
      newSectionHandoutUploadStates[chapterIndex][sectionIndex][handoutIndex].progress = 0;
      newSectionHandoutUploadStates[chapterIndex][sectionIndex][handoutIndex].objectName = handoutUri;
      setSectionHandoutUploadStates(newSectionHandoutUploadStates);
      dispatch(setHandoutUri({
        chapterIndex: chapterIndex,
        sectionIndex: sectionIndex,
        handoutUri: handoutUri
      }));
    });
  }

  const deleteCourseHandoutMaterials = (handout, chapterIndex, sectionIndex) => async () => {
    try {
      // const newIsUploading = [...isUploading];
      // newIsUploading[chapterIndex] = true;
      // console.log(newIsUploading);
      // setIsUploading(newIsUploading);
      let newSectionHandoutUploadStates = [...sectionHandoutUploadStates];
      const handoutIndex = newSectionHandoutUploadStates[chapterIndex][sectionIndex].findIndex(item => item.objectName === handout.objectName);
      if (handoutIndex !== -1) {
        newSectionHandoutUploadStates[chapterIndex][sectionIndex][handoutIndex].isDeleting = true;
        setSectionHandoutUploadStates(newSectionHandoutUploadStates);
        await deleteUtility.deleteCourseHandout(
          axiosPrivate,
          URL_DELETE_INCOMPLETE_COURSE_HANDOUT,
          courseId,
          chapterIndex,
          sectionIndex,
          handout.objectName
        );
        console.log(`Delete success: ${handout.objectName}`)
      }
    } catch (err) {
      console.error(err);
    } finally {
      let newSectionHandoutUploadStates = [...sectionHandoutUploadStates];
      newSectionHandoutUploadStates[chapterIndex][sectionIndex] = newSectionHandoutUploadStates[chapterIndex][sectionIndex]
        .filter(item => item.objectName !== handout.objectName);
      setSectionHandoutUploadStates(newSectionHandoutUploadStates);
      dispatch(removeHandoutUri({
        chapterIndex: chapterIndex,
        sectionIndex: sectionIndex,
        handoutUri: handout.objectName
      }));
      // const newIsUploading = [...isUploading];
      // newIsUploading[chapterIndex] = false;
      // console.log(newIsUploading);
      // setIsUploading(newIsUploading);
    }
  }

  const handleBackOnClick = () => {
    handleBack();
  }

  const handleSubmit = async () => {
    if (createdCourseChapters.some(chapter => chapter.sections.some(section => !!section.videoUri === false)) === false) {
      try {
        const url = URL_UPDATE_COURSE_MATERIAL.replace('{courseId}', courseId)
        await axiosPrivate.put(url,
          {chapters: createdCourseChapters})
        handleNext();
      } catch (err) {
        console.error(err);
      }
    }
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
        <Stack direction='row' alignItems='center' spacing={1}>
          <Typography component='h3' variant='h6'>
            Chapter {chapterIndex + 1} : {chapter.name}
          </Typography>

          {(isLoadingVideo[chapterIndex] || isLoadingHandout[chapterIndex]) && <CircularProgress size='1.5em'/>}
          {(!isLoadingVideo[chapterIndex] && !isLoadingHandout[chapterIndex])
            ? chapter.sections.some(section => !!section.videoUri === false)
              ? <ErrorIcon color="primary"/>
              : <CheckCircleIcon sx={{color: 'grey.400'}}/>
            : null
          }
        </Stack>
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
                    {sectionVideoUploadStates[chapterIndex][sectionIndex].file === null && !section.videoUri &&
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
                        {sectionVideoUploadStates[chapterIndex][sectionIndex].isError &&
                          <Typography sx={{color: "grey.600", mt: 0.5}}>Must be .mkv or .mp4</Typography>}
                      </Stack>
                    }
                    {sectionVideoUploadStates[chapterIndex][sectionIndex].isLoading &&
                      <LinearProgressWithLabel
                        value={sectionVideoUploadStates[chapterIndex][sectionIndex].progress}
                        fileName={sectionVideoUploadStates[chapterIndex][sectionIndex].file.name}
                      />
                    }
                    {sectionVideoUploadStates[chapterIndex][sectionIndex].isSuccess &&
                      <>
                        <Chip
                          label={sectionVideoUploadStates[chapterIndex][sectionIndex].file === null
                            ? section.videoUri
                            : sectionVideoUploadStates[chapterIndex][sectionIndex].file.name
                          }
                          variant="outlined"
                          icon={<PlayArrowRoundedIcon/>}
                          onClick={playVideo(chapterIndex, sectionIndex)}
                          onDelete={deleteCourseVideoMaterials(chapterIndex, sectionIndex)}
                          deleteIcon={<CancelIcon/>}
                        />
                        <Modal
                          open={openVideoModal}
                          onClose={() => handleCloseVideoModal()}
                        >
                          <Box
                            sx={{
                              position: 'absolute',
                              top: '50%',
                              left: '50%',
                              transform: 'translate(-50%, -50%)',
                              bgcolor: 'grey.800'
                            }}
                          >
                            {isFetchingVideoUrl &&
                              <Box
                                sx={{
                                  position: 'absolute',
                                  top: '50%',
                                  left: '50%',
                                  transform: 'translate(-50%, -50%)',
                                }}
                              >
                                <CircularProgress size='5em' color="inherit"/>
                              </Box>
                            }
                            <ReactPlayer
                              url={courseVideoUrl}
                              // light={createdCoursePictureUrl}
                              controls={true}
                              playing={false}
                              style={{margin: 'auto'}}
                            />
                          </Box>
                        </Modal>
                      </>
                    }
                  </TableCell>
                  <TableCell>
                    <Stack spacing={1} alignItems='flex-start'>
                      <label htmlFor={`handout-button-file-${chapterIndex}${sectionIndex}`}>
                        <Input
                          sx={{display: 'none'}}
                          type="file"
                          multiple
                          id={`handout-button-file-${chapterIndex}${sectionIndex}`}
                          onChange={handleHandoutUpload(chapterIndex, sectionIndex)}
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
                      {sectionHandoutUploadStates[chapterIndex][sectionIndex]
                        .filter((handout) => handout.isSuccess)
                        .map((handout, handoutIndex) => (
                          <Chip
                            key={handoutIndex}
                            label={handout.file === null ? handout.objectName : handout.file.name}
                            variant='outlined'
                            onDelete={deleteCourseHandoutMaterials(handout, chapterIndex, sectionIndex)}
                            deleteIcon={<CancelIcon/>}
                          />
                        ))}
                    </Stack>
                    {sectionHandoutUploadStates[chapterIndex][sectionIndex]
                      .filter((handout) => handout.isLoading)
                      .map((handout, handoutIndex) => (
                        <Stack sx={{mt: 1}} key={handoutIndex} spacing={1} alignItems='stretch'>
                          <Divider/>
                          <LinearProgressWithLabel
                            sx={{mt: 3}}
                            value={handout.progress}
                            fileName={handout.file.name}
                          />
                        </Stack>
                      ))
                    }
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </AccordionDetails>
    </Accordion>))}
    <Stack spacing={2} direction='row' sx={{mt: 2}}>
      <LoadingButton
        variant='contained'
        size='large'
        onClick={handleSubmit}
        loading={isLoadingVideo.some(state => state === true) || isLoadingHandout.some(state => state === true)}
        loadingPosition="start"
        startIcon={<CloudUploadIcon/>}
        disabled={createdCourseChapters.some(chapter => chapter.sections.some(section => !!section.videoUri === false))}
      >
        Submit
      </LoadingButton>
      <LoadingButton
        size='large'
        variant='outlined'
        onClick={handleBackOnClick}
        loading={isLoadingVideo.some(state => state === true) || isLoadingHandout.some(state => state === true)}
        loadingPosition="start"
        startIcon={<ArrowBackIcon/>}
      >
        Back
      </LoadingButton>
    </Stack>
  </>);
}

export default UploadVideoAndHandoutForm;