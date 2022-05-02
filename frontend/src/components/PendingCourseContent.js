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
import OndemandVideoIcon from '@mui/icons-material/OndemandVideo'

import {useState} from "react";
import PendingSectionContent from "./PendingSectionContent";

const PendingCourseContent = (props) => {
  const {chapters, courseId} = props;

  const [expand, setExpanded] = useState(false);
  const [selectedSection, setSelectedSection] = useState(false);

  const handleExpandChange = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  }

  const handleClickSection = (section, sectionIndex, chapterName, chapterIndex) => {
    setSelectedSection({
      section: section,
      sectionIndex: sectionIndex,
      chapterIndex: chapterIndex,
      chapterTitle: `Chapter ${chapterIndex + 1}: ${chapterName}`
    });
  }

  const handleBackToCourseContents = () => {
    setSelectedSection(false);
  }

  return (
    <>
      {selectedSection
        ? <PendingSectionContent
          courseId={courseId}
          selectedSection={selectedSection}
          handleBackToCourseContents={handleBackToCourseContents}
        />
        : <Box>
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
                  {chapter.sections.map((section, sectionIndex) => (
                    <div key={sectionIndex}>
                      <ListItemButton onClick={() => handleClickSection(section, sectionIndex, chapter.name, chapterIndex)}>
                        <ListItemIcon><OndemandVideoIcon/></ListItemIcon>
                        <ListItemText primary={`Section ${sectionIndex + 1} : ${section.name}`}/>
                      </ListItemButton>
                      <Divider/>
                    </div>
                  ))}
                </List>
              </AccordionDetails>
            </Accordion>
          ))
          }
        </Box>
      }
    </>
  );
}

export default PendingCourseContent;