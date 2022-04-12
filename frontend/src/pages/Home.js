import { useSelector } from "react-redux"; // ใช้ดึงข้อมูลออกจาก Store
import { useDispatch } from "react-redux"; // ใช้เรียก Method ที่เขียนไว้ใน Reducer
// import { AppBar, Box, Container, ThemeProvider, Toolbar, Typography } from "@mui/material";
// import { Button, Modal, ClickAwayListener } from '@mui/material'
import AppBarSearchHeader from "../components/AppBarSearchHeader";
import CourseCard from "../components/CourseCard"
import CaroueselCourse from "../components/CarouselCourse";

// import { Alert, AlertTitle } from "@mui/material";

import Container from '@mui/material/Container'
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';


const Home = (props) => {

  return (
    <Container maxWidth="lg">
      <AppBarSearchHeader />
      <br/>
      <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
        Home
      </Typography>
      <br/>
      <CaroueselCourse labelCorousel="My Course" startIndex={0} />
      <CaroueselCourse labelCorousel="Programming" startIndex={5} />
      
    </Container>
  )
}

export default Home;