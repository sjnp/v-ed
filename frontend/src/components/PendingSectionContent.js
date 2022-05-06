import {
  CircularProgress,
  Divider,
  Grid,
  IconButton,
  List,
  ListItem, ListItemButton, ListItemIcon, ListItemText,
  Paper,
  Stack,
  Typography
} from "@mui/material";

import ArrowBackIcon from '@mui/icons-material/ArrowBack'
import DownloadIcon from '@mui/icons-material/Download'
import ReactPlayer from "react-player";
import React, {useEffect, useState} from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import {saveAs} from 'file-saver'
import {URL_GET_PENDING_COURSE_HANDOUT, URL_GET_PENDING_COURSE_VIDEO} from "../utils/url";

const PendingSectionContent = (props) => {
  const {courseId, selectedSection, handleBackToCourseContents} = props;
  const axiosPrivate = useAxiosPrivate()

  const [isFinishFetching, setIsFinishFetching] = useState(false);
  const [videoUrl, setVideoUrl] = useState('');

  useEffect(() => {
    const handleBack = () => {
      setVideoUrl('');
      setIsFinishFetching(false);
      handleBackToCourseContents();
    }
    axiosPrivate.get(`${URL_GET_PENDING_COURSE_VIDEO}`,
      {
        params: {
          id: courseId,
          chapterIndex: selectedSection.chapterIndex,
          sectionIndex: selectedSection.sectionIndex,
          videoUri: selectedSection.section.videoUri
        }
      })
      .then(response => {
        setVideoUrl(response.data.videoUrl);
        setIsFinishFetching(true);
      })
      .catch(err => {
        console.error(err);
        handleBack();
      })
  }, [axiosPrivate, courseId, handleBackToCourseContents, selectedSection])

  const handleBack = () => {
    setVideoUrl('');
    setIsFinishFetching(false);
    handleBackToCourseContents();
  }

  const handleDownload = async (handoutUri) => {
    const response = await axiosPrivate.get(
      `${URL_GET_PENDING_COURSE_HANDOUT}`,
      {
        params: {
          id: courseId,
          chapterIndex: selectedSection.chapterIndex,
          sectionIndex: selectedSection.sectionIndex,
          handoutUri: handoutUri
        }
      }
    );
    const handoutUrl = response.data.handoutUrl;
    await saveAs(handoutUrl, handoutUri);
  }

  return (
    <Paper variant='outlined' sx={{padding: 2, pr: 3}}>
      <Grid container alignItems='center'>
        <Grid item xs={1}>
          <IconButton title="Back to course's contents" onClick={() => handleBack()}>
            <ArrowBackIcon/>
          </IconButton>
        </Grid>
        <Grid item xs={11}>
          <Typography variant='subtitle2' color='grey.500'>
            {`${selectedSection.chapterTitle}`}
          </Typography>
        </Grid>
        <Grid item xs={1}/>
        <Grid item xs={11}>
          <Typography variant='h6'>
            {`Section ${selectedSection.sectionIndex + 1}: ${selectedSection.section.name}`}
          </Typography>
          <Divider sx={{mt: 2, mb: 3}}/>
        </Grid>
        <Grid item xs={1}/>
        <Grid item xs={11}>
          {!isFinishFetching
            ? <Stack alignItems='center' sx={{mt: 15, mb: 20}}>
              <CircularProgress/>
            </Stack>
            : <Stack alignItems='stretch'>
              <ReactPlayer url={videoUrl} controls={true} width='100%' height='100%'/>
              { selectedSection.section.handouts && selectedSection.section.handouts.length
                ? <>
                  <Divider sx={{mt: 3, mb: 2}}/>
                  <Typography variant="subtitle1" sx={{fontWeight: 'bold'}}>
                    Handouts
                  </Typography>
                  <List>
                    {selectedSection.section.handouts.map((handout, handoutIndex) => (
                      <ListItem key={handoutIndex}>
                        <ListItemText primary={handout.handoutUri}></ListItemText>
                        <ListItemIcon>
                          <ListItemButton onClick={() => handleDownload(handout.handoutUri)}>
                            <DownloadIcon/>
                          </ListItemButton>
                        </ListItemIcon>
                      </ListItem>
                    ))}
                  </List>
                </>
                : null
              }

            </Stack>
          }
        </Grid>
      </Grid>
    </Paper>
  );
}

export default PendingSectionContent;