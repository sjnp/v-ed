import React, { useState, useEffect } from "react"
import { useSelector } from "react-redux"

// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

// component
import AppBarSearchHeader from "../components/AppBarSearchHeader"
import CaroueselCourse from "../components/CarouselCourse"
import LoadingCarouselCourse from "../components/LoadingCarouselCourse"

// Material UI component
import Container from '@mui/material/Container'
import Typography from '@mui/material/Typography'

// service
import overviewSerview from '../services/overview'

// url
import { URL_GET_COURSE_SAMPLES } from "../utils/url"

const Home = () => {

  const axiosPrivate = useAxiosPrivate()

  const usernameRedux = useSelector((state) => state.auth.value.username)

  const [ myCourse, setMyCourse ] = useState([])
  const [ academic, setAcademic ] = useState([])
  const [ art, setArt ] = useState()
  const [ business, setBusiness ] = useState([])
  const [ design, setDesign ] = useState([])
  const [ programming, setProgramming ] = useState([])

  const [ loadingMyCourse, setLoadingMyCourse ] = useState(false)
  const [ loadingCategories, setLoadingCategories ] = useState(true) 

  useEffect(async () => {

    setLoadingMyCourse(true)

    if (usernameRedux) {
      await axiosPrivate.get(URL_GET_COURSE_SAMPLES)
        .then(res => setMyCourse(res.data))
        .catch(err => err.response)
    } else {
      setMyCourse([])
    }
    setLoadingMyCourse(false)
    
  }, [usernameRedux])

  useEffect(async () => {

    let response = await overviewSerview.getOverviewCategory('ACADEMIC')
    handleSetStateCategory(setAcademic, response)

    response = await overviewSerview.getOverviewCategory('ART')
    handleSetStateCategory(setArt, response)

    response = await overviewSerview.getOverviewCategory('BUSINESS')
    handleSetStateCategory(setBusiness, response)
    
    response = await overviewSerview.getOverviewCategory('DESIGN')
    handleSetStateCategory(setDesign, response)

    response = await overviewSerview.getOverviewCategory('PROGRAMMING')
    handleSetStateCategory(setProgramming, response)

    if (loadingCategories) {
      setLoadingCategories(false)
    }
    
  }, [])

  const handleSetStateCategory = (setState, data) => {
    setState(data)
    if (data?.length > 0 && loadingCategories) {
      setLoadingCategories(false)
    }
  }

  return (
    <Container maxWidth="lg">
      <AppBarSearchHeader />
      <Typography variant="h5" sx={{ fontWeight: 'bold', mt: 2 }}>
        Home
      </Typography>
      { loadingMyCourse ? <LoadingCarouselCourse /> : null }
      { loadingCategories ? <LoadingCarouselCourse /> : null }
      { myCourse?.length > 0 ? <CaroueselCourse data={myCourse} label="My Course" /> : null }
      { academic?.length > 0 ? <CaroueselCourse data={academic} label="Academic" /> : null }
      { art?.length > 0 ? <CaroueselCourse data={art} label="Art" /> : null }
      { business?.length > 0 ? <CaroueselCourse data={business} label="Business" /> : null }
      { design?.length > 0 ? <CaroueselCourse data={design} label="Design" /> : null }
      { programming?.length > 0 ? <CaroueselCourse data={programming} label="Programming" /> : null }
    </Container>
  )
}

export default Home;