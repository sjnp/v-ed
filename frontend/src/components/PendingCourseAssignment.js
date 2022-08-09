import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Box,
  Divider,
  List,
  ListItemButton, ListItemIcon, ListItemText,
  Typography
} from "@mui/material";

import ExpandMoreIcon from '@mui/icons-material/ExpandMore'

import {useState} from "react";

const PendingCourseAssignment = (props) => {
  const {chapters} = props;

  const [expand, setExpanded] = useState(false);

  const handleExpandChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  }

  return (
    <>
      <Box>
        {chapters.map((chapter, chapterIndex) => (
          <Accordion
            key={chapterIndex}
            expanded={expand === chapterIndex}
            onChange={handleExpandChange(chapterIndex)}
            variant='outlined'
          >
            <AccordionSummary expandIcon={<ExpandMoreIcon/>}>
              <Typography>{`Chapter ${chapterIndex + 1}: ${chapter.name}`}</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <List>
                <Divider/>
                {chapter.assignments.length !== 0 ?
                  chapter.assignments.map((assignment, assignmentIndex) => (
                    <div key={assignmentIndex}>
                      <ListItemButton>
                        <ListItemText primary={`Assignment ${assignmentIndex + 1} : ${assignment.detail}`}/>
                      </ListItemButton>
                      <Divider/>
                    </div>
                  ))
                  : <div>
                    <ListItemButton>
                    <ListItemText primary={"No assignment in this chapter"}/>
                    </ListItemButton>
                    <Divider/>
                  </div>
                }
              </List>
            </AccordionDetails>
          </Accordion>
        ))
        }
      </Box>
    </>
  );
}

export default PendingCourseAssignment;