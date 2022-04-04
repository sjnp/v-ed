import { Stack, Box, Container, Typography } from "@mui/material";
import AppBarHeader from "../components/AppBarHeader";
import CreateCourseDrawer from "../components/CreateCourseDrawer";
import CreateCourseForm from "../components/CreateCourseForm";

const CreateCourse = () => {

  return (
    <Container maxWidth="lg">
      <AppBarHeader />
      <Typography
        marginTop={3}
        marginBottom={3}
        component='h1'
        variant='h4'>
        Create Course
      </Typography>
      <Stack spacing={2} component='main' direction='row'>

        <CreateCourseDrawer />

        <CreateCourseForm />
      </Stack>

    </Container>
  )
}

export default CreateCourse;