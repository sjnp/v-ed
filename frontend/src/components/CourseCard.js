import React, { useState } from "react"

// Material UI
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import Rating from "@mui/material/Rating";
import StarIcon from '@mui/icons-material/Star';

const CourseCard = (props) => {

    const starIcon = <StarIcon style={{ opacity: 0.55 }} fontSize="inherit" />

    const [ shadow, setShadow ] = useState(1)

    const onMouseOver = () => setShadow(10)
    const onMouseOut = () => setShadow(1)

    const { courseName, instructorName, value, image } = props

    return (
        <Card sx={{ maxWidth: 250, boxShadow: shadow, margin: 3 }} onMouseOver={onMouseOver} onMouseOut={onMouseOut}>
            <CardMedia component="img" height="170" image={image} alt="green iguana" />
            <CardContent sx={{ marginBottom: -2 }}>
                <Typography gutterBottom variant="h5" component="div">
                    {courseName}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    {instructorName}
                </Typography>
                <Typography sx={{ marginTop: 2 }}>
                    <Rating name="text-feedback" value={value} size="small" readOnly precision={0.1} emptyIcon={starIcon}/>
                </Typography>
            </CardContent>
        </Card>
    )
}

export default CourseCard