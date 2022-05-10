import React, { useState } from "react"
import { useNavigate } from "react-router-dom";

// Material UI
import { Box } from "@mui/material";
import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import Rating from "@mui/material/Rating";
import StarIcon from '@mui/icons-material/Star';
import CardActions from '@mui/material/CardActions';
import CardContent from "@mui/material/CardContent";

const cardHeight = 160

const CourseCardWide = ({ image, courseName, instructorName, rating, reviewTotal, pathOnClick, price }) => {

    const [ shadow, setShadow ] = useState(1)
    const handleMouseOver = () => setShadow(8)
    const handleMouseOut = () => setShadow(1)

    const navigate = useNavigate()
    const handleClickCourseCard = () => navigate(pathOnClick)
    
    return (
        <Card
            sx={{ 
              height: cardHeight,
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
            <CardMedia component="img" image={image}
              sx={{
                // mx : 2,
                height : cardHeight,
                minWidth : 284,
                maxWidth : 284
              }}
            />
            <Box sx={{ minWidth: 580 }}>
              <CardContent sx={{ marginTop: -1.5 }}>
                <Box sx={{ 
                  display: 'flex',
                  flexDirection: 'row',
                  alignItems: 'flex-end',
                  overflow: 'hidden',
                }} >
                  <Typography sx={{flex : 1}} variant="body1" color="text.primary" title={courseName}>
                    {courseName}
                  </Typography>
                  {price ? 
                  <Box >
                    <Typography sx={{flex : 1}} variant="body1" color="text.secondary" title={price}>
                      {price} THB
                    </Typography>
                  </Box>
                  :
                  null
                  }
                </Box>
                <Typography variant="caption" color="text.secondary" title={instructorName}>
                  {instructorName}
                </Typography>
              </CardContent>
              <Box sx={{height : cardHeight - 115}}/>
              <CardActions sx={{ marginLeft: 0.6 }}>
              {
                rating === 0 && reviewTotal === 0 ?
                  <Typography variant="caption">No review now</Typography>
                  :
                  <Box>
                    <Rating
                      value={rating} 
                      size="small" 
                      readOnly 
                      precision={0.1} 
                      emptyIcon={<StarIcon fontSize="inherit" />}
                    />
                    <Typography variant="caption">{rating} ({reviewTotal})</Typography>
                  </Box>
              }
              </CardActions>
            </Box>
            
        </Card>
    )
}

export default CourseCardWide