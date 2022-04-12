import { IconButton, Grid, Button, MenuItem, TextField, Typography, Paper } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { addChapter, removeChapter } from '../features/createdCourseSlice';

const ChapterDetailsForm = (props) => {

  const { handleNext, handleBack } = props;

  const [newChapterName, setNewChapterName] = useState('');
  const [newChapterNameError, setNewChapterNameError] = useState(false);

  const dispatch = useDispatch();
  // const createdCourseSections = useSelector((state) => state.createdCourse.value.contents)

  const createdCourseChapters = useSelector((state) => state.createdCourse.value.chapters)
    .map(item => item.name);

  console.log(createdCourseChapters);
  const handleNewChapterChange = (event) => {
    const newChapterNameInput = event.target.value;
    setNewChapterName(newChapterNameInput);
    if (!newChapterNameInput) {
      setNewChapterNameError(true);
    } else {
      setNewChapterNameError(false);
    }
  }

  const handleAdd = () => {
    if (newChapterName) {
      dispatch(addChapter({ chapter: { name: newChapterName, sections: [], assignments: [] } }));
      setNewChapterName('');
    } else {
      setNewChapterNameError(true);
    }
  }

  const handleRemove = (index) => {
    dispatch(removeChapter({ index: '${index}' }));
  }

  const handleSubmit = () => {
    if (createdCourseChapters.length === 0) {
      setNewChapterNameError(true);
    } else {
      handleNext()
    }
  }

  return (
    <>
      {createdCourseChapters.map((chapter, index) => (
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
                Chapter {index + 1} :
              </Typography>
            </Grid>
            <Grid item xs={9}>
              <TextField
                fullWidth
                value={chapter}
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
              Chapter {createdCourseChapters.length + 1} :
            </Typography>
          </Grid>
          <Grid item xs={9}>
            <TextField
              fullWidth
              id='newChapterName'
              type='text'
              value={newChapterName}
              onChange={handleNewChapterChange}
              error={newChapterNameError}
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

export default ChapterDetailsForm;