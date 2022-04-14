import React, { useState } from 'react'

// Material UI
import { Accordion, AccordionDetails, AccordionSummary, Box, Checkbox, FormGroup, FormControlLabel, Paper, List, ListItemButton, ListItemIcon, ListItemText, Slider, Typography } from '@mui/material'

import SectionVideoList from './SectionVideoList';
// Icon
import SchoolIcon from '@mui/icons-material/School';
import NotesIcon from '@mui/icons-material/Notes'
import AssignmentIcon from '@mui/icons-material/Assignment';
import QuestionAnswerIcon from '@mui/icons-material/QuestionAnswer';
import StarsIcon from '@mui/icons-material/Stars';
import PersonPinIcon from '@mui/icons-material/PersonPin';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

const FilterSidebar = () => {

  const [ratingValue, setRatingValue] = React.useState([0, 5]);
  const [pricingValue, setPricingValue] = React.useState([0, 2000]);

  const handleRatingChange = (event, ratingValue) => {
    setRatingValue(ratingValue);
  };

  const handlePricingChange = (event, ratingValue) => {
    setPricingValue(ratingValue);
  };

  const categories = [
      "Category 1",
      "Category 2",
      "Category 3",
      "Category 4",
  ]
  
  const FilterAccordion = (
    <Box>
      <Accordion key={0}>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography>Category</Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Box sx={{mx : 1}}>
            <FormGroup>
              {categories.map((category, index) => (
                <FormControlLabel key={index} control={<Checkbox />} label={category} />
              ))}            
            </FormGroup>
          </Box>
        </AccordionDetails>
      </Accordion>

      <Accordion key={1}>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography>Rating</Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Box sx={{mx : 1}}>
            <Slider 
              value={ratingValue}
              onChange={handleRatingChange}
              valueLabelDisplay="auto"
              valueLabelFormat={(ratingValue) => {return `${ratingValue} Star`;}}
              min={0}
              step={0.1}
              max={5}
              marks={[
                {
                  value: 0,
                  label: '0',
                },
                {
                  value: 1,
                  label: '1',
                },
                {
                  value: 2,
                  label: '2',
                },
                {
                  value: 3,
                  label: '3',
                },
                {
                  value: 4,
                  label: '4',
                },
                {
                  value: 5,
                  label: '5',
                },
              ]}
            />
          </Box>
        </AccordionDetails>
      </Accordion>

      <Accordion key={2}>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography>Pricing</Typography>
        </AccordionSummary>
        <AccordionDetails>
          <Box sx={{mx : 1}}>
            <Slider 
              value={pricingValue}
              onChange={handlePricingChange}
              valueLabelDisplay="auto"
              valueLabelFormat={(pricingValue) => {return `${pricingValue} ฿`;}}
              min={0}
              step={100}
              max={2000}
              marks={[
                {
                  value: 0,
                  label: 0,
                },
                {
                  value: 2000,
                  label: 2000,
                }
              ]}
            />
          </Box>
        </AccordionDetails>
      </Accordion>
    </Box>
  )


  return (
    <Paper elevation={4} sx={{ width: 240, flexShrink: 0, borderRadius: 1, position: 'fixed' }}>
      <Typography variant="h6" sx={{mx : 2 , mt : 3 , mb : 1 }}>Filter</Typography>
      {FilterAccordion}
    </Paper>
  )
}

export default FilterSidebar