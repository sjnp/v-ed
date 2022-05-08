import React, {useState} from "react"
import {useNavigate} from "react-router-dom";

// Material UI
import Card from "@mui/material/Card";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import CardActions from '@mui/material/CardActions';
import CardContent from "@mui/material/CardContent";
import CloudUploadIcon from "@mui/icons-material/CloudUpload";
import PublishIcon from '@mui/icons-material/Publish';
import LoadingButton from "@mui/lab/LoadingButton";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import {URL_PUBLISH_INSTRUCTOR_COURSE} from "../utils/url";

const InstructorApprovedCourseCard = ({id, pictureUrl, courseName, price}) => {

  const [shadow, setShadow] = useState(1);
  const [isLoading, setIsLoading] = useState(false);
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();

  const handleMouseOver = () => setShadow(8);
  const handleMouseOut = () => setShadow(1);
  const handlePublishCourse = () => {
    setIsLoading(true);
    axiosPrivate.put(URL_PUBLISH_INSTRUCTOR_COURSE,
      null,
      {
        params: {
          id: id
        }
      })
      .then(() => navigate('/instructor'))
      .catch(err => console.error(err));
  }

  return (
    <Card
      sx={{boxShadow: shadow, cursor: 'pointer'}}
      onMouseOver={handleMouseOver}
      onMouseOut={handleMouseOut}
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
      <CardActions>
        <LoadingButton
          sx={{ml: 'auto'}}
          size='medium'
          onClick={handlePublishCourse}
          loading={isLoading}
          loadingPosition="start"
          startIcon={<PublishIcon/>}
        >
          Publish this course
        </LoadingButton>
      </CardActions>
    </Card>)
}

export default InstructorApprovedCourseCard;