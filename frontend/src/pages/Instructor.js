import {Box, Button, Container, Typography} from "@mui/material";
import AppBarHeader from "../components/AppBarHeader";
import {useNavigate} from "react-router-dom";

const Instructor = () => {
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
        <Typography>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. In congue placerat risus. Proin at volutpat ligula.
          Curabitur diam urna, dapibus ut auctor ut, ullamcorper in urna. Aenean in nulla dui. Sed feugiat tortor sed
          lorem gravida elementum. Nunc rutrum ornare porta. Vestibulum imperdiet lorem eu lacus fermentum, id hendrerit
          ante auctor. Praesent in velit semper, tempor mi ac, dictum neque. Aenean interdum fringilla magna. Ut feugiat
          ultrices mi at gravida. Cras elit ligula, tempus in malesuada et, dictum a eros. Vestibulum ante ipsum primis
          in faucibus orci luctus et ultrices posuere cubilia curae; Sed quis condimentum nisi, ac vehicula lacus.
          Curabitur magna diam, malesuada et elit quis, aliquam ultricies dolor. Morbi fringilla vitae arcu nec posuere.
        </Typography>

      </Box>
    </Container>
  )
}

export default Instructor;