import { Box, Container } from "@mui/material";
import AppBarHeader from "../components/AppBarHeader";
import CreateCourseForm from "../components/CreateCourseForm";

const CreateCourse = () => {

  return (
    <Container maxWidth="lg">
      <AppBarHeader />
      <Box component='main'>

        <CreateCourseForm />
      </Box>

    </Container>
  )
}

export default CreateCourse;