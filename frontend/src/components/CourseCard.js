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

const CourseCard = ({ image, courseName, instructorName, rating, reviewTotal, pathOnClick, price }) => {

    const [shadow, setShadow] = useState(1)
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
            <CardMedia component="img" height="140" image={image} />
            <CardContent >
                <Typography variant="body1" color="text.primary" title={courseName}>
                    {courseName}
                </Typography>
                <Typography variant="caption" color="text.secondary" title={instructorName}>
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
                            {rating} ({reviewTotal})
                        </Typography>
                    </Box>
                </Box>
                <Box>
                    <Typography variant="caption" color="primary" sx={{ fontWeight: 'bold', fontSize: 14, textAlign: 'center' }} >
                        { price ? `${price} THB` : 'FREE' }
                    </Typography>
                </Box>
            </Box>
        </Card>
    )
}

export default CourseCard