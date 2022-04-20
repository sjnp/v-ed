// import { Container, Typography } from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";
import AppBarSearchHeader from "../components/AppBarSearchHeader";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

// component
import CourseCard from "../components/CourseCard";

// Material UI
import Typography from "@mui/material/Typography"
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Toolbar from "@mui/material/Toolbar";

const Student = () => {
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();
  const location = useLocation();

  const upgradeToInstructor = async () => {
    try {
      const response = await axiosPrivate.put('/api/students/instructor-feature', { recipientId: 'placeholder-omise-recipient-id'});
    } catch (err) {
      console.error(err);
      navigate('/', { state: { from: location }, replace: true });
    }
  }

  const data = {
    courseId: 0,
    courseName: `Java programming`,
    instructorName: `pradinan benjanavee`,
    rating: 4.7,
    reviewTotal: 125,
    pathOnClick: '/student/course/'
  }
  const datas = [ data, data, data, data, data, data, data, data, data, ]

  const handleData = (data) => {
    let key = 0
    let column = 0
    let result = []
    for (const element of data) {
      
      if (column === 0) result.push(<Grid item xs={1} key={++key}></Grid>)

      result.push(
        <Grid item xs={2} key={++key}>
          <CourseCard 
            key={key}
            image={`https://picsum.photos/200/300?random=${key}`}
            courseName={element.courseName}
            instructorName={element.instructorName}
            rating={element.rating}
            reviewTotal={element.reviewTotal}
            pathOnClick={element.pathOnClick + element.courseId}
          />
        </Grid>
      )
      ++column

      if (column === 5) {
        result.push(<Grid item xs={1} key={++key}></Grid>)
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
        handleData(datas).map(item => item)
      }
      </Grid>
      <Toolbar />
    </Container>
  )
}

export default Student;