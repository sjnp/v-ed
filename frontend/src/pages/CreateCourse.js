import { Box, Button, Container, Typography } from "@mui/material";
import AppBarHeader from "../components/AppBarHeader";
import { useNavigate } from "react-router-dom";
import CreateCourseForm from "../components/CreateCourseForm";

const CreateCourse = () => {

  return (
    <Container maxWidth="lg">
      <AppBarHeader />
      <Box
        component='main'
        sx={{
          margin: 3,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center'
        }}
      >
        <CreateCourseForm />

      </Box>
    </Container>
  )
}

export default CreateCourse;