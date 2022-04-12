import {
  Grid,
  Button,
  MenuItem,
  TextField,
  Typography,
  Select,
  InputLabel,
  FormControl,
  FormHelperText
} from '@mui/material';
import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {setCategory, setCourseDetails} from '../features/createdCourseSlice';
import service from "../services/service";

const CourseDetailsForm = (props) => {

  const {handleNext, categories} = props;

  const PRICE_REGEX = /^[0-9]{1,5}?$/;

  const dispatch = useDispatch();

  const createdCourseName = useSelector((state) => state.createdCourse.value.name);
  const createdCoursePrice = useSelector((state) => state.createdCourse.value.price);
  const createdCourseCategory = useSelector((state) => state.createdCourse.value.category);
  const createdCourseOverview = useSelector((state) => state.createdCourse.value.overview);
  const createdCourseRequirement = useSelector((state) => state.createdCourse.value.requirement);

  const [name, setName] = useState(createdCourseName);
  const [price, setPrice] = useState(createdCoursePrice);
  const [categorySelected, setCategorySelected] = useState(createdCourseCategory ? createdCourseCategory : '');
  const [categorySelectedId, setCategorySelectedId] = useState( createdCourseCategory ? `${createdCourseCategory.id}` : '');
  const [overview, setOverview] = useState(createdCourseOverview);
  const [requirement, setRequirement] = useState(createdCourseRequirement);

  const [nameHelper, setNameHelper] = useState(' ');
  const [priceHelper, setPriceHelper] = useState(' ');
  const [overviewHelper, setOverviewHelper] = useState(' ');
  const [requirementHelper, setRequirementHelper] = useState(' ');
  const [categoryHelper, setCategoryHelper] = useState(' ');

  const [nameError, setNameError] = useState(false);
  const [priceError, setPriceError] = useState(false);
  const [overviewError, setOverviewError] = useState(false);
  const [requirementError, setRequirementError] = useState(false);
  const [categoryError, setCategoryError] = useState(false);

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
    const newCategorySelectedId = event.target.value;
    const newCategorySelected = categories.find(category => `${category.id}` === newCategorySelectedId);
    setCategorySelected(newCategorySelected);
    setCategorySelectedId(newCategorySelectedId);
    setCategoryError(false);
    setCategoryHelper(' ');
    dispatch(setCategory({ category: newCategorySelectedId}));
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
    if (!categorySelected) {
      setCategoryError(true);
      setCategoryHelper(requiredText);
    }
    if (!name || !price || !overview || !requirement || !categorySelected) {
      return;
    }
    if (!nameError && !priceError && !overviewError && !requirementError && !categoryError) {
      dispatch(setCourseDetails({
        name: name,
        price: price,
        category: categorySelected,
        overview: overview,
        requirement: requirement
      }));
      handleNext();
    }
  }

  return (
    <>
      <Grid container spacing={2} sx={{paddingTop: 1}}>
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
          <FormControl fullWidth>
            <InputLabel id="category-label">Category</InputLabel>
            <Select
              required
              fullWidth
              labelId="Category"
              id="category"
              value={categorySelectedId}
              label="Category"
              onChange={handleCategoryChange}
              error={categoryError}
            >
              {categories ?
                categories.map((option, index) => (
                  <MenuItem key={option.id} value={`${option.id}`}>
                    {option.name}
                  </MenuItem>))
                : null
              }
            </Select>
            <FormHelperText>{categoryHelper}</FormHelperText>
          </FormControl>
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
        sx={{mt: 2}}
      >
        Next
      </Button>
    </>
  )
}

export default CourseDetailsForm;