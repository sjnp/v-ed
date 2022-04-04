
import { Drawer, List, ListItemButton, ListItemText } from '@mui/material';
const CreateCourseDrawer = () => {

  const navList = ['Course Details', 'Content', 'Assignment']

  return (
    // <Drawer
    //   variant='permanent'
    // >
    <List sx={{ width: '100%', maxWidth: 240 }}>
      {navList.map((item, index) => (
        <ListItemButton>
          <ListItemText primary={item} />
        </ListItemButton>
      ))}
    </List>
    // </Drawer>
  )
}

export default CreateCourseDrawer;