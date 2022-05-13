import React, {useState} from "react"
import {useNavigate} from "react-router-dom";

// Material UI
import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import Rating from "@mui/material/Rating";
import StarIcon from '@mui/icons-material/Star';
import CardActions from '@mui/material/CardActions';
import CardContent from "@mui/material/CardContent";

const InstructorCourseCard = ({pictureUrl, courseName, price, isIncomplete, rating, reviewTotal, pathOnClick}) => {

  const [shadow, setShadow] = useState(1)
  const handleMouseOver = () => setShadow(8)
  const handleMouseOut = () => setShadow(1)

  const navigate = useNavigate()
  const handleClickCourseCard = () => pathOnClick && navigate(pathOnClick)

  return (<Card
    sx={{boxShadow: shadow, cursor: 'pointer'}}
    onMouseOver={handleMouseOver}
    onMouseOut={handleMouseOut}
    onClick={handleClickCourseCard}
  >
    {pictureUrl
      ? <CardMedia component="img" height="140" image={pictureUrl}/>
      : <CardMedia component="img" height="140" sx={{backgroundColor: 'grey.200', border: 'none'}}/>
    }
    <CardContent>
      <Typography variant="body1" color="text.primary" title={courseName}>
        {courseName}
      </Typography>
      <Typography variant="caption" color="text.secondary" title={price}>
        {price !== 0 ? `${price} THB.` : "free"}
      </Typography>
    </CardContent>
    {isIncomplete
      ? null
      : <CardActions sx={{marginTop: -2}}>
        <Rating
          value={rating}
          size="small"
          readOnly
          precision={0.1}
          emptyIcon={<StarIcon fontSize="inherit"/>}
        />
        <Typography variant="caption">
          {rating} ({reviewTotal})
        </Typography>
      </CardActions>
    }
  </Card>)
}

export default InstructorCourseCard;