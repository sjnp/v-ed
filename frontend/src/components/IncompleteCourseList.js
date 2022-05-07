import {useEffect, useState} from "react";
import {CircularProgress, Stack, Grid, Button} from "@mui/material";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import {URL_GET_ALL_INCOMPLETE_COURSES} from "../utils/url";
import InstructorCourseCard from "./InstructorCourseCard";
import {useNavigate} from "react-router-dom";

const IncompleteCourseList = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [incompleteCourses, setIncompleteCourses] = useState([]);
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();

  useEffect(() => {
    axiosPrivate.get(URL_GET_ALL_INCOMPLETE_COURSES)
      .then(res => {
        const newIncompleteCourses = res.data.courses.map(course => {
          const courseCard = {};
          courseCard['id'] = course.id;
          courseCard['courseName'] = course.name;
          courseCard['pictureUrl'] = course.pictureUrl;
          courseCard['price'] = course.price;
          courseCard['pathOnClick'] = `/instructor/create-course/${course.id}`
          courseCard['isIncomplete'] = true;
          return courseCard;
        });
        console.log(newIncompleteCourses);
        setIncompleteCourses(newIncompleteCourses);
      })
      .then(() => setIsLoading(false))
      .catch(err => console.error(err));
  }, [axiosPrivate]);

  if (isLoading) {
    return (
      <Grid container>
        <Grid item xs={12} sx={{m: 15}}>
          <Stack alignItems='center'>
            <CircularProgress/>
          </Stack>
        </Grid>
      </Grid>
    );
  }

  return (
    <>
      {!!incompleteCourses.length
        ? <Grid container spacing={1}>
          {incompleteCourses.map(course => (
            <Grid item xs={3} key={course.id}>
              <InstructorCourseCard {...course} />
            </Grid>
          ))}
        </Grid>
        : <Grid container>
          <Grid item xs={12} sx={{m: 15}}>
            <Stack alignItems='center'>
              <Button
                variant='contained'
                size='large'
                sx={{
                  marginBottom: 3
                }}
                onClick={() => {navigate('/instructor/create-course')}}
              >
                Create Course
              </Button>
            </Stack>
          </Grid>
        </Grid>
      }
    </>
  );
}

export default IncompleteCourseList;