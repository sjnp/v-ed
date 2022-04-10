import { Accordion, AccordionSummary, AccordionDetails, Alert, AlertTitle, TextField, Typography, Grid, Paper, IconButton, Button } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import DeleteIcon from '@mui/icons-material/Delete';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { addSection, removeSection } from '../features/createdCourseSlice';

const SectionDetailsForm = (props) => {

  const { handleNext, handleBack } = props;

  const [expanded, setExpanded] = useState(false);
  const [newSectionName, setNewSectionName] = useState('');
  const [newSectionNameError, setNewSectionNameError] = useState(false);
  const [errorAlert, setErrorAlert] = useState(false);

  const dispatch = useDispatch();

  const createdCourseChapters = useSelector((state) => state.createdCourse.value.chapters)

  useEffect(() => {
    if (createdCourseChapters.find(chapter => chapter.sections.length === 0)) {
      setErrorAlert(true);
    } else {
      setErrorAlert(false);
    }
  }, [createdCourseChapters])

  const handleExpandChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
    setNewSectionName('');
    setNewSectionNameError(false);
  }

  const handleNewSectionNameChange = (event) => {
    const newSectionNameInput = event.target.value;
    setNewSectionName(newSectionNameInput);
    if (!newSectionNameInput) {
      setNewSectionNameError(true);
    } else {
      setNewSectionNameError(false);
    }
  }

  const handleAdd = (chapterIndex) => {
    if (newSectionName) {
      dispatch(addSection(
        {
          chapterIndex: `${chapterIndex}`,
          section: { name: newSectionName }
        }
      ))
      setNewSectionName('');
    } else {
      setNewSectionNameError(true);
    }
  }

  const handleRemove = (chapterIndex, sectionIndex) => {
    dispatch(removeSection(
      {
        chapterIndex: `${chapterIndex}`,
        sectionIndex: `${sectionIndex}`
      }
    ));
  }

  const handleSubmit = () => {
    if (!errorAlert) {
      console.log(createdCourseChapters);

      handleNext();
    }
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
            <Typography
              component='h3'
              variant='h6'
            >
              Chapter {chapterIndex + 1} : {chapter.name}
            </Typography>
          </AccordionSummary>
          <AccordionDetails>
            {chapter.sections.map((section, sectionIndex) => (
              <Paper
                key={sectionIndex}
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
                      Section {sectionIndex + 1} :
                    </Typography>
                  </Grid>
                  <Grid item xs={9}>
                    <TextField
                      fullWidth
                      value={section.name}
                      InputProps={{ readOnly: true }}
                    />
                  </Grid>
                  <Grid item xs='auto'>
                    <IconButton onClick={() => handleRemove(chapterIndex, sectionIndex)}>
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
                    Section {createdCourseChapters[chapterIndex].sections.length + 1} :
                  </Typography>
                </Grid>
                <Grid item xs={9}>
                  <TextField
                    fullWidth
                    id='newSectionName'
                    type='text'
                    value={newSectionName}
                    onChange={handleNewSectionNameChange}
                    error={newSectionNameError}
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
      {errorAlert
        ? <Alert severity="error" sx={{ mt: 3 }}>
          <AlertTitle>Error</AlertTitle>
          Chapter with <strong>no section</strong> need to be filled.
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

export default SectionDetailsForm;