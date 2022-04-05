// import { Container, Typography } from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";
import AppBarHeader from "../components/AppBarHeader";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

// component
import CourseCard from "../components/CourseCard";

// Material UI
import Typography from "@mui/material/Typography"
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";

const Student = () => {
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();
  const location = useLocation();

  const upgradeToInstructor = async () => {
    try {
      const response = await axiosPrivate.put('/api/users/u/change-to-instructor');
    } catch (err) {
      console.error(err);
      navigate('/', { state: { from: location }, replace: true });
    }
  }

  let index = 0
  const data = [
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      pathOnClick: '/student/course'
    },
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      pathOnClick: '/student/course'
    }, 
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      pathOnClick: '/student/course'
    }, 
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      pathOnClick: '/student/course'
    }, 
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      pathOnClick: '/student/course'
    }, 
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      pathOnClick: '/student/course'
    }, 
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      pathOnClick: '/student/course'
    }, 
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      pathOnClick: '/student/course'
    }, 
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      pathOnClick: '/student/course'
    }, 
    // {
    //   image: `https://picsum.photos/200/300?random=${index++}`,
    //   courseName: `Java programming ${index}`,
    //   instructorName: `pradinan benjanavee ${index}`,
    //   rating: 4.7,
    //   reviewTotal: 125,
    //   pathOnClick: '/student/course'
    // },
  ]

  const handleData = (data) => {
    let key = 0
    let column = 0
    let result = []
    for (const element of data) {
      
      if (column === 0) result.push(<Grid item xs={1} key={++key}></Grid>)

      result.push(
        <Grid item xs={2} key={++key}>
          <CourseCard 
            key={index}
            image={element.image}
            courseName={element.courseName}
            instructorName={element.instructorName}
            rating={element.rating}
            reviewTotal={element.reviewTotal}
            pathOnClick={element.pathOnClick}
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
      <AppBarHeader />
      <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
        Student home
      </Typography>
      <button onClick={() => upgradeToInstructor()}>
        to be instructor
      </button>
      <hr/>
      <br/>
      <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
        My Course
      </Typography>
      <br/>

      <Grid container spacing={1} >
      {
        handleData(data).map(item => item)
      }
      </Grid>

      <br/><br/><br/><br/>
    </Container>
  )
}

export default Student;