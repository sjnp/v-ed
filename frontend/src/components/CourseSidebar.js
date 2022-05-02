import {List, ListItemButton, ListItemIcon, ListItemText, Paper} from "@mui/material";
import {useState} from "react";

const CourseSidebar = (props) => {
  const {labels, onClickSidebar} = props;
  const [selectSidebarIndex, setSelectSidebarIndex] = useState(0);

  const handleClickSidebar = (index) => {
    setSelectSidebarIndex(index);
    onClickSidebar(index);
  }

  return (
    <Paper variant='outlined'>
      <List>
        {labels.map((label, index) => (
          <ListItemButton
            key={index}
            selected={selectSidebarIndex === index}
            onClick={() => handleClickSidebar(index)}
          >
            <ListItemIcon sx={{pl: 2}}>{label.icon}</ListItemIcon>
            <ListItemText primary={label.text}/>
          </ListItemButton>
        ))}
      </List>
    </Paper>
  )
}

export default CourseSidebar;