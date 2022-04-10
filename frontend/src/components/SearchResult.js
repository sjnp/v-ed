// import { Container, Typography } from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";
import AppBarHeader from "../components/AppBarHeader";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

// component
import CourseCardWide from "../components/CourseCardWide";

// Material UI
import { Stack } from "@mui/material"
import Typography from "@mui/material/Typography"
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";

const SearchResult = () => {
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();
  const location = useLocation();

  let index = 0
  const data = [
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      price: 500,
      pathOnClick: '/overview'
    },
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      price: 500,
      pathOnClick: '/overview'
    }, 
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      price: 500,
      pathOnClick: '/overview'
    }, 
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      price: 500,
      pathOnClick: '/overview'
    }, 
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      price: 500,
      pathOnClick: '/overview'
    }, 
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      price: 500,
      pathOnClick: '/overview'
    }, 
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      price: 500,
      pathOnClick: '/overview'
    }, 
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      price: 500,
      pathOnClick: '/overview'
    }, 
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      price: 500,
      pathOnClick: '/overview'
    }, 
    {
      image: `https://picsum.photos/200/300?random=${index++}`,
      courseName: `Java programming ${index}`,
      instructorName: `pradinan benjanavee ${index}`,
      rating: 4.7,
      reviewTotal: 125,
      pathOnClick: '/overview'
    },
  ]

  const handleData = (
    data.map( (element, index) =>  
      <CourseCardWide 
        key={index}
        image={element.image}
        courseName={element.courseName}
        instructorName={element.instructorName}
        rating={element.rating}
        reviewTotal={element.reviewTotal}
        pathOnClick={element.pathOnClick}
        price={element.price}
      />    
    )
  )

  return (
    <Stack spacing={2}>
      {handleData}
    </Stack>
  )
}

export default SearchResult;