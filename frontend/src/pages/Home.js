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
import { URL_OVERVIEW_MY_COURSE } from "../utils/url";

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

    if (usernameRedux && myCourse.length === 0) {
      const response = await axiosPrivate.get(URL_OVERVIEW_MY_COURSE)
        .then(res => res.data)
        .catch(err => err.response)
      setMyCourse(response)
    } else {
      setMyCourse([])
    }

    if (art.length === 0) {
      const response = await overviewSerview.getOverviewCategory('Art')
      setArt(response)
    }
    
    if (business.length === 0) {
      const response = await overviewSerview.getOverviewCategory('Business')
      setBusiness(response)
    }
    
    if (academic.length === 0) {
      const response = await overviewSerview.getOverviewCategory('Academic')
      setAcademic(response)
    }
    
    if (design.length === 0) {
      const response = await overviewSerview.getOverviewCategory('Design')
      setDesign(response)
    }
    
    if (programming.length === 0) {
      const response = await overviewSerview.getOverviewCategory('Programming')
      setProgramming(response)
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