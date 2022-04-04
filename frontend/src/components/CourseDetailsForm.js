import { Grid, Button, MenuItem, TextField, Typography } from '@mui/material';
import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { setCourseDetails } from '../features/createdCourseSlice';

const CourseDetailsForm = (props) => {

  const { handleNext } = props;

  const PRICE_REGEX = /^[0-9]{1,5}?$/

  //TODO: Need to get this from backend api
  const categories = [
    "Programming",
    "Art",
    "Academic",
    "Business",
    "Design"
  ];

  const dispatch = useDispatch();

  const createdCourseName = useSelector((state) => state.createdCourse.value.name);
  const createdCoursePrice = useSelector((state) => state.createdCourse.value.price);
  const createdCourseCategory = useSelector((state) => state.createdCourse.value.category);
  const createdCourseOverview = useSelector((state) => state.createdCourse.value.overview);
  const createdCourseRequirement = useSelector((state) => state.createdCourse.value.requirement);

  const [name, setName] = useState(createdCourseName);
  const [price, setPrice] = useState(createdCoursePrice);
  const [category, setCategory] = useState(createdCourseCategory ? createdCourseCategory : 0);
  const [overview, setOverview] = useState(createdCourseOverview);
  const [requirement, setRequirement] = useState(createdCourseRequirement);

  const [nameHelper, setNameHelper] = useState(' ');
  const [priceHelper, setPriceHelper] = useState(' ');
  const [overviewHelper, setOverviewHelper] = useState(' ');
  const [requirementHelper, setRequirementHelper] = useState(' ');

  const [nameError, setNameError] = useState(false);
  const [priceError, setPriceError] = useState(false);
  const [overviewError, setOverviewError] = useState(false);
  const [requirementError, setRequirementError] = useState(false);

  const handleNameChange = (event) => {
    const nameInput = event.target.value;
    setName(nameInput);
    setNameHelper(`(${nameInput.length}/100)`);
    if (nameInput.length > 100) {
      setNameError(true);
    } else {
      setNameError(false);
    }
  };

  const handlePriceChange = (event) => {
    const priceInput = event.target.value;
    setPrice(priceInput);
    if (!PRICE_REGEX.test(priceInput)) {
      setPriceError(true);
      setPriceHelper('Must be a number between 0 - 99999');
    } else {
      setPriceError(false);
      setPriceHelper('(Price range: 0 - 99999)');
    }
  }

  const handleCategoryChange = (event) => {
    setCategory(event.target.value);
  }

  const handleOverviewChange = (event) => {
    const overviewInput = event.target.value;
    setOverview(overviewInput);
    setOverviewHelper(`(${overviewInput.length}/1000)`);
    if (overviewInput.length > 1000) {
      setOverviewError(true);
    } else {
      setOverviewError(false);
    }
  }

  const handleRequirementChange = (event) => {
    const requirementInput = event.target.value;
    setRequirement(requirementInput);
    setRequirementHelper(`(${requirementInput.length}/1000)`);
    if (requirementInput.length > 1000) {
      setRequirementError(true);
    } else {
      setRequirementError(false);
    }
  }

  const handleSubmit = () => {
    const requiredText = 'This is required';
    if (!name) {
      setNameError(true);
      setNameHelper(requiredText);
    }
    if (!price) {
      setPriceError(true);
      setPriceHelper(requiredText);
    }
    if (!overview) {
      setOverviewError(true);
      setOverviewHelper(requiredText);
    }
    if (!requirement) {
      setRequirementError(true);
      setRequirementHelper(requiredText);
    }
    if (!name || !price || !overview || !requirement) { return; }
    if (!nameError && !priceError && !overviewError && !requirementError) {
      dispatch(setCourseDetails({ name, price, category, overview, requirement }))
      handleNext();
    }
  }

  return (
    <>
      <Grid container spacing={2} sx={{ paddingTop: 1 }}>
        <Grid item xs={12}>
          <TextField
            autoComplete='off'
            required
            fullWidth
            id='name'
            label='Course name'
            name='name'
            type='text'
            value={name}
            onChange={handleNameChange}
            error={nameError}
            helperText={nameHelper}
          />
        </Grid>
        <Grid item xs={12} md={6}>
          <TextField
            autoComplete='off'
            required
            fullWidth
            id='price'
            label='Price (฿)'
            name='price'
            type='text'
            value={price}
            onChange={handlePriceChange}
            error={priceError}
            helperText={priceHelper}
          />
        </Grid>
        <Grid item xs={12} md={6}>
          <TextField
            required
            fullWidth
            id='category'
            select
            label='Category'
            value={category}
            onChange={handleCategoryChange}
          >
            {categories.map((option, index) => (
              <MenuItem key={index} value={index}>
                {option}
              </MenuItem>
            ))}
          </TextField>
        </Grid>
        <Grid item xs={12}>
          <TextField
            autoComplete='off'
            required
            fullWidth
            id='overview'
            label='Overview'
            name='overview'
            type='text'
            value={overview}
            onChange={handleOverviewChange}
            error={overviewError}
            helperText={overviewHelper}
            multiline
            minRows={3}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            autoComplete='off'
            required
            fullWidth
            id='requirement'
            label='Requirement'
            name='requirement'
            type='text'
            value={requirement}
            onChange={handleRequirementChange}
            error={requirementError}
            helperText={requirementHelper}
            multiline
            minRows={3}
          />
        </Grid>
      </Grid>
      <Button
        variant='contained'
        size='large'
        onClick={handleSubmit}
        sx={{ mt: 2 }}
      >
        Next
      </Button>
    </>
  )
}

export default CourseDetailsForm;