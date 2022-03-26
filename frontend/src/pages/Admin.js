import { Container, Typography } from "@mui/material";
import AppBarHeader from "../components/AppBarHeader";

const Admin = () => {
  return (
    <Container maxWidth="lg">
      <AppBarHeader />
      <div>

        <h1>Welcome :D</h1>
        <h3>Admin</h3>

        <Typography>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. In congue placerat risus. Proin at volutpat ligula. Curabitur diam urna, dapibus ut auctor ut, ullamcorper in urna. Aenean in nulla dui. Sed feugiat tortor sed lorem gravida elementum. Nunc rutrum ornare porta. Vestibulum imperdiet lorem eu lacus fermentum, id hendrerit ante auctor. Praesent in velit semper, tempor mi ac, dictum neque. Aenean interdum fringilla magna. Ut feugiat ultrices mi at gravida. Cras elit ligula, tempus in malesuada et, dictum a eros. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Sed quis condimentum nisi, ac vehicula lacus. Curabitur magna diam, malesuada et elit quis, aliquam ultricies dolor. Morbi fringilla vitae arcu nec posuere.
        </Typography>

      </div>
    </Container>
  )
}

export default Admin;