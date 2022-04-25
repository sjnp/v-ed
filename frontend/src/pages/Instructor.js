import {Box, Button, Container, Typography} from "@mui/material";
import AppBarHeader from "../components/AppBarHeader";
import {useNavigate} from "react-router-dom";
import IncompleteCourseList from "../components/IncompleteCourseList";
import {useEffect, useState} from "react";

const Instructor = () => {
  const navigate = useNavigate();

  const [incompleteCourses , setIncompleteCourses] = useState();
  useEffect(() => {

  }, []);

  return (
    <Container maxWidth="lg">
      <AppBarHeader/>
      <Box
        component='main'
        sx={{
          margin: 3,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center'
        }}
      >
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

        <IncompleteCourseList/>

      </Box>
    </Container>
  )
}

export default Instructor;