import { Accordion, AccordionSummary, AccordionDetails, Alert, AlertTitle, TextField, Typography, Grid, Paper, IconButton, Button } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import DeleteIcon from '@mui/icons-material/Delete';
import AddCircleIcon from '@mui/icons-material/AddCircle';

import { useDispatch, useSelector } from 'react-redux';
import { useState, useEffect } from 'react';
import { addAssignment, removeAssignment } from '../features/createdCourseSlice';

const CourseAssignmentForm = (props) => {

  const { handleNext, handleBack } = props;

  const [expanded, setExpanded] = useState(false);
  const [newAssignementDetail, setNewAssignmentDetail] = useState('');
  const [newAssignementDetailError, setNewAssignmentDetailError] = useState(false);
  const [skippable, setSkippable] = useState(false);

  const dispatch = useDispatch();

  const createdCourseChapters = useSelector((state) => state.createdCourse.value.chapters);

  useEffect(() => {
    if (createdCourseChapters.find(chapter => chapter.assignments.length !== 0)) {
      setSkippable(false);
    } else {
      setSkippable(true);
    }
  }, [createdCourseChapters])

  const handleExpandChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
    setNewAssignmentDetail('');
    setNewAssignmentDetailError(false);
  }

  const handleNewAssignmentDetailChange = (event) => {
    const assignmentDetailInput = event.target.value;
    setNewAssignmentDetail(assignmentDetailInput);
  }

  const handleAdd = (chapterIndex) => {
    if (newAssignementDetail) {
      dispatch(addAssignment(
        {
          chapterIndex: `${chapterIndex}`,
          assignment: { detail: newAssignementDetail }
        }
      ))
      setNewAssignmentDetail('');
    } else {
      setNewAssignmentDetailError(true);
    }
  }

  const handleRemove = (chapterIndex, assignmentIndex) => {
    dispatch(removeAssignment(
      {
        chapterIndex: `${chapterIndex}`,
        assignmentIndex: `${assignmentIndex}`
      }
    ))
  }

  const handleSubmit = () => {
    console.log(createdCourseChapters);

    //TODO: save course structure to the database
  }


  return (
    <>
      {createdCourseChapters.map((chapter, chapterIndex) => (
        <Accordion
          key={chapterIndex}
          expanded={expanded === chapterIndex}
          onChange={handleExpandChange(chapterIndex)}
        >
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Typography component='h3' variant='h6'>
              Chapter {chapterIndex + 1} : {chapter.name}
            </Typography>
          </AccordionSummary>
          <AccordionDetails>
            {chapter.assignments.map((assignment, assignmentIndex) => (
              <Paper
                key={assignmentIndex}
                sx={{ mt: 1, mb: 2, padding: 2, pl: 3, bgcolor: 'grey.200' }}
                elevation={3}
              >
                <Grid container alignItems='center' spacing={2}>
                  <Grid item xs='auto'>
                    <Typography component='h3' variant='h6'>
                      Assignment {assignmentIndex + 1} :
                    </Typography>
                  </Grid>
                  <Grid item xs={9}>
                    <TextField
                      fullWidth
                      value={assignment.detail}
                      InputProps={{ readOnly: true }}
                    />
                  </Grid>
                  <Grid item xs='auto'>
                    <IconButton onClick={() => handleRemove(chapterIndex, assignmentIndex)}>
                      <DeleteIcon />
                    </IconButton>
                  </Grid>
                </Grid>
              </Paper>
            ))}
            <Paper
              sx={{ mt: 1, mb: 2, padding: 2, pl: 3 }}
              elevation={3}
            >
              <Grid container alignItems='center' spacing={2}>
                <Grid item xs='auto'>
                  <Typography component='h3' variant='h6'>
                    Assignment {createdCourseChapters[chapterIndex].assignments.length + 1} :
                  </Typography>
                </Grid>
                <Grid item xs={9}>
                  <TextField
                    fullWidth
                    id='newAssignmentDetail'
                    value={newAssignementDetail}
                    onChange={handleNewAssignmentDetailChange}
                    error={newAssignementDetailError}
                  />
                </Grid>
                <Grid item xs='auto'>
                  <IconButton onClick={() => handleAdd(chapterIndex)}>
                    <AddCircleIcon />
                  </IconButton>
                </Grid>
              </Grid>
            </Paper>
          </AccordionDetails>
        </Accordion>
      ))}
      <Button
        variant='contained'
        size='large'
        onClick={handleSubmit}
        sx={{ mt: 2 }}
      >
        {skippable ? 'Skip' : 'Next'}
      </Button>
      <Button
        size='large'
        onClick={handleBack}
        sx={{ mt: 2, ml: 3 }}
      >
        Back
      </Button>
    </>
  )
}

export default CourseAssignmentForm;