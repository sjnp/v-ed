import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import AppBarSearchHeader from "../components/AppBarSearchHeader";

// component
import CourseCard from "../components/CourseCard";
import CourseCardWide from '../components/CourseCardWide'

// Material UI component
import Typography from "@mui/material/Typography"
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import IconButton from "@mui/material/IconButton";

// Material UI icon
import GridViewSharpIcon from '@mui/icons-material/GridViewSharp';
import TableRowsSharpIcon from '@mui/icons-material/TableRowsSharp';

// custom hook
import useAxiosPrivate from "../hooks/useAxiosPrivate";

// url
import { URL_GET_STUDENT_COURSES } from "../utils/url";

const Student = () => {
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();
  const location = useLocation();

  const [ myCourse, setMyCourse ] = useState([])

  // const upgradeToInstructor = async () => {
  //   try {
  //     const response = await axiosPrivate.put('/api/students/instructor-feature', { recipientId: 'placeholder-omise-recipient-id'});
  //   } catch (err) {
  //     console.error(err);
  //     navigate('/', { state: { from: location }, replace: true });
  //   }
  // }

  useEffect(async () => {
    const response = await axiosPrivate.get(URL_GET_STUDENT_COURSES).then(res => res).catch(err => err.response)
    if (response.status === 200) {
      // setMyCourse(response.data)
      setMyCourse(Array(5).fill(...response.data))
    }
  }, [])

  const handleDataShowTable = () => {
    let key = 0
    let column = 0
    let result = []
    const sideSize = 1.5
    const contentSize = 3
    for (const course of myCourse) {
      
      if (column === 0) result.push(<Grid item xs={sideSize} key={++key}></Grid>)

      result.push(
        <Grid item xs={contentSize} key={++key}>
          <CourseCard
            key={key}
            image={course.pictureURL}
            courseName={course.courseName}
            instructorName={course.instructorName}
            rating={course.rating}
            reviewCount={course.reviewCount}
            pathOnClick={'/student/course/' + course.courseId + '/content'}
          />
        </Grid>
      )
      ++column

      if (column === 3) {
        result.push(<Grid item xs={sideSize} key={++key}></Grid>)
        column = 0
      }
    }
    return result
  }

  const handleDataShowList = () => {
    let result = []
    let key = 0
    const sideSize = 3
    const contentSize = 6
    for (const course of myCourse) {
      result.push(<Grid item xs={sideSize} key={++key}></Grid>)

      // const element = (
      //   <Grid item xs={8} key={++key}>
      //     <CourseCardWide
      //       image={course.pictureURL}
      //       courseName={course.courseName}
      //       instructorName={course.instructorName}
      //       rating={course.rating}
      //       reviewCount={course.reviewCount}
      //       pathOnClick={'/student/course/' + course.courseId + '/content'}
      //     />
      //   </Grid>
        
      // )
        result.push(
          <Grid item xs={contentSize} key={++key}>
            <CourseCardWide
              image={course.pictureURL}
              courseName={course.courseName}
              instructorName={course.instructorName}
              rating={course.rating}
              reviewCount={course.reviewCount}
              pathOnClick={'/student/course/' + course.courseId + '/content'}
            />
          </Grid>
        )
        result.push(<Grid item xs={sideSize} key={++key}></Grid>)
    }
    return result
  }

  const [ showTable, setshowTable ] = useState(true)
  const [ showList, setshowList ] = useState(false)

  const handleChageShowListOrShowTable = () => {
    setshowTable(!showTable)
    setshowList(!showList)
  }

  return (
    <Container maxWidth="lg">
      <AppBarSearchHeader />
      <Grid container pt={5}>
        <Grid item xs={1}></Grid>
        <Grid item xs={2}>
          <Typography variant="h5" fontWeight='bold'>My Course</Typography>
        </Grid>
        <Grid item xs={7}>
        </Grid>
        <Grid item xs={2}>
          <IconButton onClick={handleChageShowListOrShowTable}>
            <GridViewSharpIcon color={ showTable ? 'info' : 'default' } />
          </IconButton>
          <IconButton onClick={handleChageShowListOrShowTable}>
            <TableRowsSharpIcon color={ showList ? 'info' : 'default' } />
          </IconButton>
        </Grid>
        <Grid container pt={5} spacing={1}>
        { showTable ? handleDataShowTable().map(item => item) : null }
        { showList ? handleDataShowList().map(item => item) : null }
        </Grid>
      </Grid>
    </Container>
  )
}

export default Student;