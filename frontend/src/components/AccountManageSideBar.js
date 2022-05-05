import { Avatar,Box,CssBaseline,Divider,List,ListItemButton,ListItemIcon,ListItemText,Paper,Toolbar,Typography } from '@mui/material';
import PersonIcon from '@mui/icons-material/Person';
import ImageIcon from '@mui/icons-material/Image';
import KeyIcon from '@mui/icons-material/Key';
import SchoolIcon from '@mui/icons-material/School';
import { useSelector,useDispatch } from "react-redux";
import { setAccountManagePage } from "../features/pagesControlSlice";
import stringToColor from './stringToColor';

const drawerWidth = 240;

const AccountManageSideBar = () => {
  const username = useSelector((state) => state.auth.value.username);
  const page = useSelector((state) => state.page.value.accountManagePage);
  const dispatch = useDispatch();
  
  const handleClickSidebar = (index) => {
    dispatch(setAccountManagePage({ page : index}))
  }

  const sideBarList = [
    {
        text: 'Profile',
        icon: <PersonIcon />
    },
    {
        text: 'Picture',
        icon: <ImageIcon />
    },
    {
        text: 'Change Password',
        icon: <KeyIcon />
    },
    {
        text: 'Active Instructor',
        icon: <SchoolIcon />
    }
  ]

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
        {sideBarList.map((item, index) => (
          <ListItemButton 
            key={index} 
            selected={ page === index }
            onClick={() => handleClickSidebar(index)}
          >
            <ListItemIcon>{item.icon}</ListItemIcon>
            <ListItemText primary={item.text} />
          </ListItemButton>
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