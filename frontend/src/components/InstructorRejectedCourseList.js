import {useEffect, useState} from "react";
import {CircularProgress, Stack, Grid, Typography} from "@mui/material";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import {URL_GET_ALL_INSTRUCTOR_REJECTED_COURSES} from "../utils/url";
import InstructorCourseCard from "./InstructorCourseCard";

const InstructorRejectedCourseList = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [rejectedCourses, setRejectedCourses] = useState([]);
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    axiosPrivate.get(URL_GET_ALL_INSTRUCTOR_REJECTED_COURSES)
      .then(res => {
        const newRejectedCourses = res.data.courses.map(course => {
          const courseCard = {};
          courseCard['id'] = course.id;
          courseCard['courseName'] = course.name;
          courseCard['pictureUrl'] = course.pictureUrl;
          courseCard['price'] = course.price;
          courseCard['isIncomplete'] = true;
          return courseCard;
        });
        console.log(newRejectedCourses);
        setRejectedCourses(newRejectedCourses);
      })
      .then(() => setIsLoading(false))
      .catch(err => console.error(err));
  }, [axiosPrivate]);

  if (isLoading) {
    return (
      <Grid container>
        <Grid item xs={12} sx={{m: 12}}>
          <Stack alignItems='center'>
            <CircularProgress/>
          </Stack>
        </Grid>
      </Grid>
    );
  }

  return (
    <>
      {!!rejectedCourses.length
        ? <Grid container spacing={1}>
          {rejectedCourses.map(course => (
            <Grid item xs={3} key={course.id}>
              <InstructorCourseCard {...course} />
            </Grid>
          ))}
        </Grid>
        : <Grid container>
          <Grid item xs={12} sx={{m: 12}}>
            <Stack alignItems='center'>
              <Typography>
                There is no rejected course.
              </Typography>
            </Stack>
          </Grid>
        </Grid>
      }
    </>
  );
}

export default InstructorRejectedCourseList;