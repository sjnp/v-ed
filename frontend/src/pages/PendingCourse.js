import AppBarHeader from "../components/AppBarHeader";
import Container from "@mui/material/Container";
import {useNavigate, useParams} from "react-router-dom";
import {Box, CircularProgress, Stack} from "@mui/material";
import {useEffect, useState} from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import {URL_GET_PENDING_COURSES} from "../utils/url";

const PendingCourse = () => {
  const {courseId} = useParams();

  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();

  const [isFinishFetching, setIsFinishFetching] = useState(false);
  const [course, setCourse] = useState(null);

  useEffect(() => {
    axiosPrivate.get(`${URL_GET_PENDING_COURSES}?id=${courseId}`)
      .then(response => setCourse(response.data))
      .then(() => setIsFinishFetching(true))
      .catch(err => {
        console.error(err);
        navigate('/admin');
      })
  }, [axiosPrivate, courseId, navigate]);
  


  return (
    <Container maxWidth="lg">
      <AppBarHeader/>
      <Box sx={{pt: 2}}>
        {(!isFinishFetching) &&
          <Stack alignItems='center' sx={{mt: 5}}>
            <CircularProgress/>
          </Stack>
        }
      </Box>
    </Container>
  );
}

export default PendingCourse;