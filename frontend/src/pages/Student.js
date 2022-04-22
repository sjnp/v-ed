import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import AppBarSearchHeader from "../components/AppBarSearchHeader";

// component
import CourseCard from "../components/CourseCard";

// Material UI
import Typography from "@mui/material/Typography"
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Toolbar from "@mui/material/Toolbar";

// custom hook
import useAxiosPrivate from "../hooks/useAxiosPrivate";

// url
import { URL_STUDENT_MY_COURSE } from "../utils/url";

const Student = () => {
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();
  const location = useLocation();

  const [ myCourses, setMyCourse ] = useState([])

  const upgradeToInstructor = async () => {
    try {
      const response = await axiosPrivate.put('/api/students/instructor-feature', { recipientId: 'placeholder-omise-recipient-id'});
    } catch (err) {
      console.error(err);
      navigate('/', { state: { from: location }, replace: true });
    }
  }

  useEffect(async () => {
    const response = await axiosPrivate.get(URL_STUDENT_MY_COURSE).then(res => res).catch(err => err.response)
    if (response.status === 200) {
      setMyCourse(response.data)
    }
  }, [])

  const handleData = () => {
    let key = 0
    let column = 0
    let result = []
    for (const myCourse of myCourses) {
      
      if (column === 0) result.push(<Grid item xs={1.5} key={++key}></Grid>)

      result.push(
        <Grid item xs={3} key={++key}>
          <CourseCard
            key={key}
            image={myCourse.pictureURL}
            courseName={myCourse.courseName}
            instructorName={myCourse.instructorName}
            rating={myCourse.rating}
            reviewCount={myCourse.reviewCount}
            pathOnClick={'/student/course/' + myCourse.courseId}
          />
        </Grid>
      )
      ++column

      if (column === 3) {
        result.push(<Grid item xs={1.5} key={++key}></Grid>)
        column = 0
      }
    }
    return result
  }

  return (
    <Container maxWidth="lg">
      <AppBarSearchHeader />
      <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
        Student home
      </Typography>
      <button onClick={() => upgradeToInstructor()}>
        to be instructor
      </button>
      <hr/>
      <Typography variant="h5" sx={{ fontWeight: 'bold', mb: 2 }}>
        My Course
      </Typography>
      <Grid container spacing={1} >
      {
        handleData().map(item => item)
      }
      </Grid>
      <Toolbar />
    </Container>
  )
}

export default Student;