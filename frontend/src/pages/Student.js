import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import AppBarSearchHeader from "../components/AppBarSearchHeader";

// component
import MyCourseCardTable from "../components/MyCourseCardTable"
import MyCourseCardList from "../components/MyCourseCardList"
import LoadingCircle from '../components/LoadingCircle'

// Material UI component
import Typography from "@mui/material/Typography"
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import IconButton from "@mui/material/IconButton";

// Material UI icon
import GridViewSharpIcon from '@mui/icons-material/GridViewSharp';
import TableRowsSharpIcon from '@mui/icons-material/TableRowsSharp';

// custom hook
import useAxiosPrivate from "../hooks/useAxiosPrivate";

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_STUDENT_COURSES } from "../utils/url";

const Student = () => {
  
  const axiosPrivate = useAxiosPrivate()

  // const navigate = useNavigate();
  // const location = useLocation();
  // const upgradeToInstructor = async () => {
  //   try {
  //     const response = await axiosPrivate.put('/api/students/instructor-feature', { recipientId: 'placeholder-omise-recipient-id'});
  //   } catch (err) {
  //     console.error(err);
  //     navigate('/', { state: { from: location }, replace: true });
  //   }
  // }
  
  const [ myCourse, setMyCourse ] = useState([])

  const [ loading, setLoading ] = useState(true)

  const [ viewTable, setViewTable ] = useState(true)
  const [ viewList, setViewList ] = useState(false)

  const handleChangeView = () => {
    setViewTable(!viewTable)
    setViewList(!viewList)
  }

  useEffect(async () => {
    const response = await apiPrivate.get(axiosPrivate, URL_GET_STUDENT_COURSES)
    if (response.status === 200) {
      setMyCourse(response.data)
      setLoading(false)
    }
  }, [])

  return (
    <Container maxWidth="lg">
      <AppBarSearchHeader />
      <Grid container pt={5} mb={10}>
        <Grid item xs={1}></Grid>
        <Grid item xs={2}>
          <Typography variant="h5" fontWeight='bold'>My Course</Typography>
        </Grid>
        <Grid item xs={7}>
        </Grid>
        <Grid item xs={2}>
          <IconButton onClick={handleChangeView}>
            <GridViewSharpIcon color={ viewTable ? 'primary' : 'default' } />
          </IconButton>
          <IconButton onClick={handleChangeView}>
            <TableRowsSharpIcon color={ viewList ? 'primary' : 'default' } />
          </IconButton>
        </Grid>
        <Grid container pt={5} spacing={2}>
        {
          loading ? <LoadingCircle loading={loading} centerY={true} />
          :
          viewTable ? 
          <MyCourseCardTable data={myCourse} /> 
          : 
          <MyCourseCardList data={myCourse} />
        }
        </Grid>
      </Grid>
    </Container>
  )
}

export default Student;