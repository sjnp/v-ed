import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// component
import AppBarSearchHeader from "../components/AppBarSearchHeader";
import CaroueselCourse from "../components/CarouselCourse";

// Material UI component
import Container from '@mui/material/Container'
import Typography from '@mui/material/Typography';

// service
import overviewSerview from '../services/overview'

// url
import { URL_GET_COURSE_SAMPLES } from "../utils/url";

const Home = () => {

  const axiosPrivate = useAxiosPrivate()

  const usernameRedux = useSelector((state) => state.auth.value.username)

  const [ myCourse, setMyCourse ] = useState([])
  const [ art, setArt ] = useState([])
  const [ business, setBusiness ] = useState([])
  const [ academic, setAcademic ] = useState([])
  const [ design, setDesign ] = useState([])
  const [ programming, setProgramming ] = useState([])

  useEffect(async () => {

    if (usernameRedux) {
      await axiosPrivate.get(URL_GET_COURSE_SAMPLES)
        .then(res => setMyCourse(res.data))
        .catch(err => err.response)
    } else {
      setMyCourse([])
    }
    
  }, [usernameRedux])

  useEffect(async () => {

      let response = await overviewSerview.getOverviewCategory('Art')
      setArt(response)
    
      response = await overviewSerview.getOverviewCategory('Business')
      setBusiness(response)
    
      response = await overviewSerview.getOverviewCategory('Academic')
      setAcademic(response)
    
      response = await overviewSerview.getOverviewCategory('Design')
      setDesign(response)
    
      response = await overviewSerview.getOverviewCategory('Programming')
      setProgramming(response)
    
  }, [])

  return (
    <Container maxWidth="lg">
      <AppBarSearchHeader />
      <Typography variant="h5" sx={{ fontWeight: 'bold', mt: 2, mb: 1 }}>
        Home
      </Typography>
      {
        myCourse?.length > 0 ?
          <CaroueselCourse data={myCourse} labelCorousel="My Course" pathTo="/student/course/" />
          : null
      }
      {
        art?.length > 0 ?
          <CaroueselCourse data={art} labelCorousel="Art" pathTo="/overview/course/" />
          : null
      }
      {
        business?.length > 0 ?
          <CaroueselCourse data={business} labelCorousel="Business" pathTo="/overview/course/" />
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