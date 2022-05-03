import {Box, Button, Container, Stack, Typography} from "@mui/material";
import AppBarHeader from "../components/AppBarHeader";
import {useNavigate} from "react-router-dom";
import InstructorCourseList from "../components/InstructorCourseList";
import {useEffect, useState} from "react";

const Instructor = () => {
  const navigate = useNavigate();

  return (
    <Container maxWidth="lg">
      <AppBarHeader/>
      <Stack
        alignItems='stretch'
        component='main'
        sx={{
          mt: 3,
          mb: 3
        }}
      >
        <Stack direction='row' justifyContent='center'>
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

        <InstructorCourseList/>
      </Stack>
    </Container>
  )
}

export default Instructor;