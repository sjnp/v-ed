import AppBarHeader from "../components/AppBarHeader";
import Container from "@mui/material/Container";
import {useNavigate, useParams} from "react-router-dom";
import {Box, CircularProgress, Stack, Typography} from "@mui/material";
import React, {useEffect, useState} from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import {URL_GET_ALL_PENDING_COURSES, URL_GET_PENDING_COURSE, URL_PUT_PENDING_COURSE} from "../utils/url";
import Grid from "@mui/material/Grid";
import SchoolIcon from "@mui/icons-material/School";
import AssignmentIcon from "@mui/icons-material/Assignment";
import PersonPinIcon from "@mui/icons-material/PersonPin";
import NotesIcon from "@mui/icons-material/Notes";
import CheckIcon from '@mui/icons-material/Check';
import CloseIcon from '@mui/icons-material/Close';
import CourseSidebar from "../components/CourseSidebar";
import PendingCourseContent from "../components/PendingCourseContent";
import LoadingButton from "@mui/lab/LoadingButton";

const PendingCourse = () => {
  const {courseId} = useParams();

  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();

  const [isFinishFetching, setIsFinishFetching] = useState(false);
  const [course, setCourse] = useState(null);
  const [elements, setElements] = useState([]);
  const [mainElement, setMainElement] = useState(null);
  const [isApproving, setIsApproving] = useState(false);
  const [isRejecting, setIsRejecting] = useState(false);

  useEffect(() => {
    const url = URL_GET_PENDING_COURSE.replace('{courseId}', courseId)
    axiosPrivate.get(url)
      .then(response => {
        const newCourse = response.data;
        const newElements = [
          {
            label: {icon: <SchoolIcon/>, text: 'Content'},
            element: <PendingCourseContent chapters={newCourse.chapters} courseId={courseId}/>
          },
          {
            label: {icon: <AssignmentIcon/>, text: 'Assignment'},
            element: null
          },
          {
            label: {icon: <PersonPinIcon/>, text: 'Instructor'},
            element: null
          },
          {
            label: {icon: <NotesIcon/>, text: 'About course'},
            element: null
          }
        ];
        setCourse(newCourse);
        setElements(newElements);
        setMainElement(newElements[0].element);
        console.log(newCourse);
      })
      .then(() => setIsFinishFetching(true))
      .catch(err => {
        console.error(err);
        navigate('/admin');
      })
  }, [axiosPrivate, courseId, navigate]);

  const handleChangeMainElement = (index) => {
    setMainElement(elements[index].element);
  }

  const handleApproval = async (isApproved) => {
    try {
      isApproved ? setIsApproving(true) : setIsRejecting(true)
      const url = URL_PUT_PENDING_COURSE.replace('{courseId}', courseId)
      await axiosPrivate.put(url,
        null,
        {
          params: {
            isApproved: isApproved
          }
        }
      );
      navigate('/admin');
    } catch (err) {
      isApproved ? setIsApproving(false) : setIsRejecting(false)
      console.error(err);
    }
  }

  return (
    <Container maxWidth="lg">
      <AppBarHeader/>
      <Box sx={{pt: 2, pb: 2}}>
        {(!isFinishFetching) &&
          <Stack alignItems='center' sx={{mt: 5}}>
            <CircularProgress/>
          </Stack>
        }
        {(!!course) &&
          <>
            <Grid container spacing={3}>
              <Grid item xs={3}>
                <CourseSidebar
                  labels={elements.map(element => element.label)}
                  onClickSidebar={handleChangeMainElement}
                />
              </Grid>
              <Grid item xs={9}>
                {mainElement}
              </Grid>
            </Grid>
            <Stack
              direction='row'
              alignItems='center'
              justifyContent='flex-end'
              spacing={1}
              sx={{marginTop: 6, marginBottom: 3}}
            >
              <Typography variant='h6' sx={{mr: 1}}>Approve this course ?</Typography>
              <LoadingButton
                disabled={isRejecting}
                variant='contained'
                loading={isApproving}
                loadingPosition="start"
                startIcon={<CheckIcon/>}
                onClick={() => handleApproval(true)}
              >
                Yes
              </LoadingButton>
              <LoadingButton
                disabled={isApproving}
                variant='outlined'
                loading={isRejecting}
                loadingPosition="end"
                endIcon={<CloseIcon/>}
                onClick={() => handleApproval(false)}
              >
                No
              </LoadingButton>
            </Stack>
          </>
        }

      </Box>
    </Container>
  );
}

export default PendingCourse;