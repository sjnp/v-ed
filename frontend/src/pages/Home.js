import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux"; // ใช้ดึงข้อมูลออกจาก Store
import { useDispatch } from "react-redux"; // ใช้เรียก Method ที่เขียนไว้ใน Reducer

// component
import AppBarSearchHeader from "../components/AppBarSearchHeader";
import CaroueselCourse from "../components/CarouselCourse";

// Material UI component
import Container from '@mui/material/Container'
import Typography from '@mui/material/Typography';
import TestUpload from "../components/TestUpload";

// service
import previewService from '../services/preview'

const Home = () => {

  const usernameRedux = useSelector((state) => state.auth.value.username)

  const [ categories, setcategories ] = useState({
    myCourse: [],
    art: [],
    bussiness: [],
    academic: [],
    design: [],
    programming: []
  })

  const previewCategoryAPI = async () => {
    const category = await previewService.getPreviewCategory()
    setcategories({ ...category })
  }

  const previewMyCourseAPI = async () => {
    const myCourse = await previewService.getPrevieMyCourse()
    setcategories({ ...myCourse })
  }
  
  useEffect(() => {

    if (usernameRedux) {
      previewMyCourseAPI()
    } else {
      setcategories({ ...categories, myCourse: [] })
      previewCategoryAPI()
    }

  }, [usernameRedux])

  return (
    <Container maxWidth="lg">
      <AppBarSearchHeader />
      <br/>
      <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
        Home
      </Typography>
      <br/>

      {
        categories.myCourse?.length > 0 ?
          <CaroueselCourse data={categories.myCourse} labelCorousel="My Course" pathTo="/student" /> 
          : 
          null
      }
      {
        categories.art.length > 0 ?
          <CaroueselCourse data={categories.art} labelCorousel="Art" pathTo="/overview" /> 
          : 
          null
      }
      {
        categories.bussiness.length > 0 ?
          <CaroueselCourse data={categories.bussiness} labelCorousel="Bussiness" pathTo="/overview" /> 
          : 
          null
      }
      {
        categories.academic.length > 0 ?
          <CaroueselCourse data={categories.academic} labelCorousel="Academic" pathTo="/overview" /> 
          : 
          null
      }
      {
        categories.design.length > 0 ?
          <CaroueselCourse data={categories.design} labelCorousel="Design" pathTo="/overview" /> 
          : 
          null
      }
      {
        categories.programming.length > 0 ?
          <CaroueselCourse data={categories.programming} labelCorousel="Programming" /> 
          : 
          null
      }
    </Container>
  )
}

export default Home;