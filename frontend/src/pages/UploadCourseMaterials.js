import {Box, Container} from "@mui/material";
import AppBarHeader from "../components/AppBarHeader";
import UploadCourseMaterialForm from "../components/UploadCourseMaterialForm";
import {useParams} from "react-router-dom";

const UploadCourseMaterials = () => {

  const {id} = useParams();
  return (
    <Container maxWidth="lg">
      <AppBarHeader/>
      <Box component='main'>
        <UploadCourseMaterialForm courseId={id}/>
      </Box>
    </Container>
  )
}

export default UploadCourseMaterials;