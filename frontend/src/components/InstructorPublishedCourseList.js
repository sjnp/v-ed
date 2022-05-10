import {useEffect, useState} from "react";
import {CircularProgress, Stack, Grid, Typography} from "@mui/material";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import {URL_GET_ALL_INSTRUCTOR_PUBLISHED_COURSES} from "../utils/url";
import InstructorCourseCard from "./InstructorCourseCard";

const InstructorPublishedCourseList = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [publishedCourses, setPublishedCourses] = useState([]);
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    axiosPrivate.get(URL_GET_ALL_INSTRUCTOR_PUBLISHED_COURSES)
      .then(res => {
        const newPublishedCourses = res.data.map(course => {
          const courseCard = {};
          courseCard['id'] = course.id;
          courseCard['courseName'] = course.name;
          courseCard['pictureUrl'] = course.pictureUrl;
          courseCard['price'] = course.price;
          courseCard['rating'] = course.rating;
          courseCard['reviewTotal'] = course.reviewTotal;
          courseCard['isIncomplete'] = false;
          // courseCard['pathOnClick'] = null;
          return courseCard;
        });
        console.log(newPublishedCourses);
        setPublishedCourses(newPublishedCourses);
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
      {!!publishedCourses.length
        ? <Grid container spacing={1}>
          {publishedCourses.map(course => (
            <Grid item xs={3} key={course.id}>
              <InstructorCourseCard {...course} />
            </Grid>
          ))}
        </Grid>
        : <Grid container>
          <Grid item xs={12} sx={{m: 12}}>
            <Stack alignItems='center'>
              <Typography>
                There is no published course.
              </Typography>
            </Stack>
          </Grid>
        </Grid>
      }
    </>
  );
}

export default InstructorPublishedCourseList;