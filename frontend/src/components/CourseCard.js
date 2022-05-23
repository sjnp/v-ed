import React, { useState } from "react"
import { useNavigate } from "react-router-dom";

// Material UI component
import Card from "@mui/material/Card";
import Box from "@mui/material/Box";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import Rating from "@mui/material/Rating";
import StarIcon from '@mui/icons-material/Star';
import CardContent from "@mui/material/CardContent";

const CourseCard = ({ image, courseName, instructorName, rating, reviewCount, pathOnClick, price }) => {

    const [shadow, setShadow] = useState(1)
    const handleMouseOver = () => setShadow(8)
    const handleMouseOut = () => setShadow(1)

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
            sx={{ boxShadow: shadow, cursor: 'pointer' }}
            onMouseOver={handleMouseOver}
            onMouseOut={handleMouseOut}
            onClick={handleClickCourseCard}
        >
            <CardMedia component="img" height="140" image={image} />
            <CardContent >
                <Typography noWrap variant="body1" color="text.primary" title={courseName}>
                    {courseName}
                </Typography>
                <Typography noWrap variant="body2" color="text.secondary" title={instructorName}>
                    {instructorName}
                </Typography>
            </CardContent>
            <Box display='flex' justifyContent='space-between' p={1.5} alignItems='center'>
                <Box display='flex' alignItems='center'>
                {
                    rating === 0 && reviewCount === 0 ?
                    <Typography variant="caption" color='gray'>{'No review now'}</Typography>
                    :
                    <Box>
                        <Rating
                            value={rating}
                            size="small"
                            readOnly
                            emptyIcon={<StarIcon fontSize="inherit" />}
                        />
                        <Typography variant="caption" textAlign='center'>{rating} ({reviewCount})</Typography>
                    </Box>
                }    
                </Box>
                <Box>
                    <Typography variant="caption" color="primary" fontWeight='bold' fontSize={14} textAlign='center'>
                        {showPrice}
                    </Typography>
                </Box>
            </Box>
        </Card>
    )
}

export default CourseCard