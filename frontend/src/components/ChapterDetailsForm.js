import { Accordion, AccordionSummary, AccordionDetails, Alert, AlertTitle, TextField, Typography, Grid, Paper, IconButton, Button } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import DeleteIcon from '@mui/icons-material/Delete';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { addChapter, removeChapter } from '../features/createdCourseSlice';

const ChapterDetailsForm = (props) => {

  const { handleNext, handleBack } = props;

  const [expanded, setExpanded] = useState(false);
  const [newChapterName, setNewChapterName] = useState('');
  const [newChapterNameError, setNewChapterNameError] = useState(false);
  const [errorAlert, setErrorAlert] = useState(false);

  const dispatch = useDispatch();

  const createdCourseSections = useSelector((state) => state.createdCourse.value.contents)

  useEffect(() => {
    if (createdCourseSections.find(section => section.chapters.length === 0)) {
      setErrorAlert(true);
    } else {
      setErrorAlert(false);
    }
  }, [createdCourseSections])

  const handleExpandChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
    setNewChapterName('');
    setNewChapterNameError(false);
  }

  const handleNewChapterNameChange = (event) => {
    const newChapterNameInput = event.target.value;
    setNewChapterName(newChapterNameInput);
    if (!newChapterNameInput) {
      setNewChapterNameError(true);
    } else {
      setNewChapterNameError(false);
    }
  }

  const handleAdd = (sectionIndex) => {
    if (newChapterName) {
      dispatch(addChapter(
        {
          sectionIndex: `${sectionIndex}`,
          chapter: { name: newChapterName }
        }
      ))
      setNewChapterName('');
    } else {
      setNewChapterNameError(true);
    }
  }

  const handleRemove = (sectionIndex, chapterIndex) => {
    dispatch(removeChapter(
      {
        sectionIndex: `${sectionIndex}`,
        chapterIndex: `${chapterIndex}`
      }
    ));
  }

  const handleSubmit = () => {
    if (!errorAlert) {
      console.log(createdCourseSections);

      handleNext();
    }
  }

  return (
    <>
      {createdCourseSections.map((section, sectionIndex) => (
        <Accordion
          key={sectionIndex}
          expanded={expanded === sectionIndex}
          onChange={handleExpandChange(sectionIndex)}
        >
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            <Typography
              component='h3'
              variant='h6'
            >
              Section {sectionIndex + 1} : {section.name}
            </Typography>
          </AccordionSummary>
          <AccordionDetails>
            {section.chapters.map((chapter, chapterIndex) => (
              <Paper
                key={chapterIndex}
                sx={{ marginTop: 1, marginBottom: 2, padding: 2, paddingLeft: 3, bgcolor: 'grey.200' }}
                elevation={3}
              >
                <Grid
                  container
                  alignItems='center'
                  spacing={2}
                >
                  <Grid item xs='auto'>
                    <Typography
                      component='h3'
                      variant='h6'
                    >
                      Chapter {chapterIndex + 1} :
                    </Typography>
                  </Grid>
                  <Grid item xs={9}>
                    <TextField
                      fullWidth
                      value={chapter.name}
                      InputProps={{ readOnly: true }}
                    />
                  </Grid>
                  <Grid item xs='auto'>
                    <IconButton onClick={() => handleRemove(sectionIndex, chapterIndex)}>
                      <DeleteIcon />
                    </IconButton>
                  </Grid>
                </Grid>
              </Paper>
            ))}
            <Paper sx={{ marginTop: 1, marginBottom: 2, padding: 2, paddingLeft: 3 }} elevation={3}>
              <Grid
                container
                alignItems='center'
                spacing={2}
              >
                <Grid item xs='auto'>
                  <Typography
                    component='h3'
                    variant='h6'
                  >
                    Chapter {createdCourseSections[sectionIndex].chapters.length + 1} :
                  </Typography>
                </Grid>
                <Grid item xs={9}>
                  <TextField
                    fullWidth
                    id='newChapterName'
                    type='text'
                    value={newChapterName}
                    onChange={handleNewChapterNameChange}
                    error={newChapterNameError}
                  />
                </Grid>
                <Grid item xs='auto'>
                  <IconButton onClick={() => handleAdd(sectionIndex)}>
                    <AddCircleIcon />
                  </IconButton>
                </Grid>
              </Grid>
            </Paper>

          </AccordionDetails>
        </Accordion>
      ))}
      {errorAlert
        ? <Alert severity="error" sx={{ mt: 3 }}>
          <AlertTitle>Error</AlertTitle>
          Section with <strong>no chapter</strong> need to be filled.
        </Alert>
        : null
      }
      <Button
        variant='contained'
        size='large'
        onClick={handleSubmit}
        sx={{ mt: 2 }}
      >
        Next
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

export default ChapterDetailsForm;