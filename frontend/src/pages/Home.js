import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";

// component
import AppBarSearchHeader from "../components/AppBarSearchHeader";
import CaroueselCourse from "../components/CarouselCourse";

// Material UI component
import Container from '@mui/material/Container'
import Typography from '@mui/material/Typography';

// service
import previewService from '../services/preview'

const Home = () => {

  const usernameRedux = useSelector((state) => state.auth.value.username)

  const [ myCourse, setMyCourse ] = useState([])
  const [ art, setArt ] = useState([])
  const [ bussiness, setBusiness ] = useState([])
  const [ academic, setAcademic ] = useState([])
  const [ design, setDesign ] = useState([])
  const [ programming, setProgramming ] = useState([])

  const previewMyCourseAPI = async () => {
    const result = await previewService.getPreviewMyCourse()
    setMyCourse(result)
  }

  const previewCategoryAPI = async (type, setState) => {
    const result = await previewService.getPreviewCategory(type)
    setState(result)
  }

  useEffect(() => {

    if (usernameRedux) {

      if (myCourse.length === 0) {
        previewMyCourseAPI()
      }
    
    } else {
      setMyCourse([])
    }

    if (art.length === 0) {
      previewCategoryAPI('Art', setArt)
    }
    
    if (bussiness.length === 0) {
      previewCategoryAPI('Business', setBusiness)
    }
    
    if (academic.length === 0) {
      previewCategoryAPI('Academic', setAcademic)
    }
    
    if (design.length === 0) {
      previewCategoryAPI('Design', setDesign)
    }
    
    if (programming.length === 0) {
      previewCategoryAPI('Programming', setProgramming)
    }
    
  }, [usernameRedux])

  return (
    <Container maxWidth="lg">
      <AppBarSearchHeader />
      <Typography variant="h5" sx={{ fontWeight: 'bold', mt: 2, mb: 1 }}>
        Home
      </Typography>
      {
        myCourse?.length > 0 ?
          <CaroueselCourse data={myCourse} labelCorousel="My Course" pathTo="/student/course/video/" />
          : null
      }
      {
        art?.length > 0 ?
          <CaroueselCourse data={art} labelCorousel="Art" pathTo="/overview/course/" />
          : null
      }
      {
        bussiness?.length > 0 ?
          <CaroueselCourse data={bussiness} labelCorousel="Bussiness" pathTo="/overview/course/" />
          : null
      }
      {
        academic?.length > 0 ?
          <CaroueselCourse data={academic} labelCorousel="Academic" pathTo="/overview/course/" />
          : null
      }
      {
        design?.length > 0 ?
        <CaroueselCourse data={design} labelCorousel="Design" pathTo="/overview/course/" />
        : null
      }
      {
        programming?.length > 0 ?
          <CaroueselCourse data={programming} labelCorousel="Programming" pathTo="/overview/course/" />
          : null
      }
    </Container>
  )
}

export default Home;