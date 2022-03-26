import { Container, Typography } from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";
import AppBarHeader from "../components/AppBarHeader";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

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

  return (
    <Container maxWidth="lg">
      <AppBarHeader />
      <div>
        <h1>Welcome :D</h1>
        <h3>Student</h3>
        <Typography>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. In congue placerat risus. Proin at volutpat ligula. Curabitur diam urna, dapibus ut auctor ut, ullamcorper in urna. Aenean in nulla dui. Sed feugiat tortor sed lorem gravida elementum. Nunc rutrum ornare porta. Vestibulum imperdiet lorem eu lacus fermentum, id hendrerit ante auctor. Praesent in velit semper, tempor mi ac, dictum neque. Aenean interdum fringilla magna. Ut feugiat ultrices mi at gravida. Cras elit ligula, tempus in malesuada et, dictum a eros. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Sed quis condimentum nisi, ac vehicula lacus. Curabitur magna diam, malesuada et elit quis, aliquam ultricies dolor. Morbi fringilla vitae arcu nec posuere.
        </Typography>
        <button onClick={() => upgradeToInstructor()}>
          to be instructor
        </button>

      </div>
    </Container>

  )
}

export default Student;