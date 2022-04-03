import { Avatar,Box,CssBaseline,Divider,List,ListItem,ListItemIcon,ListItemText,Paper,Toolbar,Typography } from '@mui/material';
import PersonIcon from '@mui/icons-material/Person';
import ImageIcon from '@mui/icons-material/Image';
import KeyIcon from '@mui/icons-material/Key';
import SchoolIcon from '@mui/icons-material/School';
import { useSelector } from "react-redux";
import stringToColor from './stringToColor';

const drawerWidth = 240;
const sideBarList = ['Profile', 'Picture', 'Change Password', 'Instructor'];
const sideBarIconList = [<PersonIcon />, <ImageIcon />, <KeyIcon />, <SchoolIcon /> ]

const AccountManageSideBar = () => {
  const username = useSelector((state) => state.auth.value.username);

  const avatar = (
      <Box
        sx={{
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Avatar 
          alt={username} 
          src="/static/images/avatar/1.jpg" 
          sx={{ 
            mt : 5,
            width: 130, 
            height: 130, 
            bgcolor: stringToColor(username)}}
          />
        <Typography 
          // variant="h6"
          sx={{
            my : 3,
          }}
        >
          {username}
        </Typography>
      </Box>
  )

  const drawer = (
      <List>
        {sideBarList.map((text, index) => (
          <ListItem button key={text}>
            <ListItemIcon>
              {sideBarIconList[index]}
            </ListItemIcon>
            <ListItemText primary={text} />
          </ListItem>
        ))}
      </List>
  );

  return (
    <Paper 
      elevation={2} 
      sx={{ 
        width: drawerWidth, 
        flexShrink: 0 
      }}
    >
      {avatar}
      <Divider />
      {drawer}
    </Paper>
  );
}

export default AccountManageSideBar;