import React, { useState } from "react"
import { useNavigate } from "react-router-dom";

// Material UI
import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import Rating from "@mui/material/Rating";
import StarIcon from '@mui/icons-material/Star';
import CardActions from '@mui/material/CardActions';
import CardContent from "@mui/material/CardContent";

const CourseCard = ({ image, courseName, instructorName, rating, reviewTotal, pathOnClick }) => {

    const [ shadow, setShadow ] = useState(1)
    const handleMouseOver = () => setShadow(8)
    const handleMouseOut = () => setShadow(1)

    const navigate = useNavigate()
    const handleClickCourseCard = () => navigate(pathOnClick)
    
    return (
        <Card
            sx={{ boxShadow: shadow, cursor: 'pointer' }} 
            onMouseOver={handleMouseOver} 
            onMouseOut={handleMouseOut}
            onClick={handleClickCourseCard}
        >
            <CardMedia component="img" height="130" image={image} />
            <CardContent >
                <Typography variant="body1" color="text.primary" title={courseName}>
                    {courseName}
                </Typography>
                <Typography variant="caption" color="text.secondary" title={instructorName}>
                    {instructorName}
                </Typography>
            </CardContent>
            <CardActions sx={{ marginTop: -2 }}>
                <Rating
                    value={rating} 
                    size="small" 
                    readOnly 
                    precision={0.1} 
                    emptyIcon={<StarIcon fontSize="inherit" />}
                />
                <Typography variant="caption">
                    {rating} ({reviewTotal})
                </Typography>
            </CardActions>
        </Card>
    )
}

export default CourseCard