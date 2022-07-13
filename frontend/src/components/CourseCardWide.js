import React, { useState } from "react"
import { useNavigate } from "react-router-dom"

// Material UI component
import Box from "@mui/material/Box"
import Card from "@mui/material/Card"
import CardMedia from "@mui/material/CardMedia"
import Typography from "@mui/material/Typography"
import Rating from "@mui/material/Rating"
import StarIcon from '@mui/icons-material/Star'
import Grid from "@mui/material/Grid"

const CourseCardWide = ({ image, courseName, instructorName, rating, reviewCount, pathOnClick, price }) => {

  const [ shadow, setShadow ] = useState(1)
  const handleMouseOver = () => setShadow(8)
  const handleMouseOut = () => setShadow(1)

  const cardHeight = 160

  const navigate = useNavigate()
  const handleClickCourseCard = () => navigate(pathOnClick)

  let showPrice = ''
  if (price > 0) {
      showPrice = `${price} THB`
  } else if (price === 0) {
      showPrice = 'FREE'
  }
  
  return (
    <Card 
      sx={{
        mb: 2,
        boxShadow: shadow,
        cursor: 'pointer',
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
        overflow: 'hidden',
      }}
      onMouseOver={handleMouseOver}
      onMouseOut={handleMouseOut}
      onClick={handleClickCourseCard}
    >
      <Grid container >
        <Grid item xs={6}>
          <CardMedia component="img" image={image} height={cardHeight} />
        </Grid>
        <Grid item xs={6} container direction='column' justifyContent='space-between' pt={1}>
          <Grid item container>
            <Grid item xs={12}>
              <Typography noWrap pl={1} pr={1} variant="body1" color="text.primary" title={courseName}>
                {courseName}
              </Typography>
            </Grid>
            <Grid item xs={12}>
              <Typography noWrap pl={1} pr={1} variant="caption" color="text.secondary" title={instructorName}>
                {instructorName}
              </Typography>
            </Grid>
          </Grid>
          <Grid item container direction='row' justifyContent='space-between' alignItems='center' pb={1}>
          {
            rating === 0 && reviewCount === 0 ?
              <Typography variant="caption" pl={1}>No review now</Typography>
              :
              <Box pl={1}>
                <Rating
                  value={rating} 
                  size="small" 
                  readOnly
                  emptyIcon={<StarIcon fontSize="inherit" />}
                />
                <Typography pl={1} pt={-1} variant="caption">{rating} ({reviewCount})</Typography>
              </Box>
          }
          {
            price ? 
            <Box pr={1}>
              <Typography variant="body1" color="primary" fontWeight='bold' title={price}>
                {showPrice}
              </Typography>
            </Box>
            :
            null
          }
          </Grid>
        </Grid>
      </Grid>
    </Card>
  )
}

export default CourseCardWide