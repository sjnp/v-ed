import {
  Button,
  CircularProgress,
  Paper,
  Stack,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow
} from "@mui/material";
import {useEffect, useState} from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import {useNavigate} from "react-router-dom";
import {URL_GET_ALL_PENDING_COURSES} from "../utils/url";

const PendingReviewReportList = () => {

  // const pendingCourses = [
  //   { instructorName: 'FirstName LastName', id: 5, name: 'Intro to CourseName'},
  //   { instructorName: 'FirstName2 LastName2', id: 52, name: 'Intro to CourseName2'}
  // ]
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();

  const [pendingCourses, setPendingCourses] = useState([]);
  const [isFinishFetching, setIsFinishFetching] = useState(false);

  useEffect(() => {
    axiosPrivate.get(URL_GET_ALL_PENDING_COURSES)
      .then(response => setPendingCourses(response.data))
      .then(() => setIsFinishFetching(true))
      .catch(err => console.error(err));
  }, [axiosPrivate])

  if (!isFinishFetching) {
    return (
      <Stack alignItems='center' sx={{mt: 5}}>
        <CircularProgress/>
      </Stack>
    );
  }

  return (<>
    <TableContainer component={Paper} variant='outlined'>
      <Table
        style={{tableLayout: 'fixed'}}
      >
        <TableHead>
          <TableRow>
            <TableCell>Instructor</TableCell>
            <TableCell>Course</TableCell>
            <TableCell/>
          </TableRow>
        </TableHead>
        <TableBody>
          {pendingCourses.map((course => (
            <TableRow
              key={course.id}
              sx={{'&:last-child td, &:last-child th': {border: 0}}}
              hover
            >
              <TableCell component='th'>{course.instructorName}</TableCell>
              <TableCell>{course.name}</TableCell>
              <TableCell align='right'>
                <Button variant='contained' onClick={() => {navigate(`/admin/pending-course/${course.id}`)}}>
                  Review
                </Button>
              </TableCell>
            </TableRow>
          )))}
        </TableBody>
      </Table>
    </TableContainer>
  </>)
}

export default PendingReviewReportList;