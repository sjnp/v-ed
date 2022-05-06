import React, { useState } from "react"
import { useNavigate } from "react-router-dom";

// Material UI
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
            <Box sx={{ display: 'flex', justifyContent: 'space-between', p: 1.5, alignItems: 'center' }}>
                <Box sx={{ display: "flex", alignItems: 'center' }}>
                    <Rating
                        value={rating}
                        size="small"
                        readOnly
                        precision={0.1}
                        emptyIcon={<StarIcon fontSize="inherit" />}
                    />
                    <Box>
                        <Typography variant="caption" sx={{ textAlign: 'center' }}>
                            {rating} ({reviewCount})
                        </Typography>
                    </Box>
                </Box>
                <Box>
                    <Typography variant="caption" color="primary" sx={{ fontWeight: 'bold', fontSize: 14, textAlign: 'center' }} >
                        {showPrice}
                    </Typography>
                </Box>
            </Box>
        </Card>
    )
}

export default CourseCard