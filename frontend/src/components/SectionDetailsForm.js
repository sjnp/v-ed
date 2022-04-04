import { IconButton, Grid, Button, MenuItem, TextField, Typography, Paper } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { addSection, removeSection } from '../features/createdCourseSlice';

const SectionDetailsForm = (props) => {

  const { handleNext, handleBack } = props;

  const [newSectionName, setNewSectionName] = useState('');
  const [newSectionNameError, setNewSectionNameError] = useState(false);

  const dispatch = useDispatch();

  const createdCourseSections = useSelector((state) => state.createdCourse.value.contents)
    .map(item => item.name);

  console.log(createdCourseSections);
  const handleNewSectionNameChange = (event) => {
    const newSectionNameInput = event.target.value;
    setNewSectionName(newSectionNameInput);
    if (!newSectionNameInput) {
      setNewSectionNameError(true);
    } else {
      setNewSectionNameError(false);
    }
  }

  const handleAdd = () => {
    if (newSectionName) {
      dispatch(addSection({ section: { name: newSectionName, chapters: [], assignments: [] } }));
      setNewSectionName('');
    } else {
      setNewSectionNameError(true);
    }
  }

  const handleRemove = (index) => {
    dispatch(removeSection({ index: '${index}' }));
  }

  const handleSubmit = () => {
    if (createdCourseSections.length === 0) {
      setNewSectionNameError(true);
    } else {
      handleNext()
    }
  }

  return (
    <>
      {createdCourseSections.map((section, index) => (
        <Paper
          key={index}
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
                Section {index + 1} :
              </Typography>
            </Grid>
            <Grid item xs={9}>
              <TextField
                fullWidth
                value={section}
                InputProps={{ readOnly: true }}
              />
            </Grid>
            <Grid item xs='auto'>
              <IconButton onClick={handleRemove}>
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
              Section {createdCourseSections.length + 1} :
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
            <IconButton onClick={handleAdd}>
              <AddCircleIcon />
            </IconButton>
          </Grid>
        </Grid>
      </Paper>
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
  );
}

export default SectionDetailsForm;