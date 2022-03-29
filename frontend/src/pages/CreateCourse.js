import {Box, Button, Container, Typography} from "@mui/material";
import AppBarHeader from "../components/AppBarHeader";
import {useNavigate} from "react-router-dom";

const CreateCourse = () => {
  const navigate = useNavigate();

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
        

      </Box>
    </Container>
  )
}

export default CreateCourse;