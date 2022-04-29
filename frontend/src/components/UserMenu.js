import {Avatar, Grid, IconButton, Menu, MenuItem, Typography} from "@mui/material";
import {useState} from "react";
import {useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import useLogout from "../hooks/useLogout";
import stringToColor from './stringToColor';

const UserMenu = () => {
  const logout = useLogout();
  const navigate = useNavigate();

  const username = useSelector((state) => state.auth.value.username);
  const roles = useSelector((state) => state.auth.value.roles);
  const settings = roles.includes('ADMIN')
    ? ['Admin', 'Logout']
    : roles.includes('INSTRUCTOR')
      ? ['Student', 'Instructor', 'Account Settings', 'Logout']
      : ['Student', 'Account Settings', 'Logout'];

  const [anchorElUser, setAnchorElUser] = useState(null);
  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const signOut = async () => {
    await logout();
    navigate('/');
  }

  const handleCloseUserMenu = (event) => {
    setAnchorElUser(null);
    switch (event.currentTarget.dataset.myValue) {
      case 'Student':
        navigate('/student/course');
        break;
      case 'Instructor':
        navigate('/instructor');
        break;
      case 'Admin':
        navigate('/admin');
        break;
      case 'Account Settings':
        navigate('/account-manage');
        break;
      case 'Logout':
        signOut();
        break;
      default:
        break;
    }
  };

  return (

    <Grid
      container
      alignItems='center'
      justifyContent='flex-end'
      spacing={2}
    >
      <Grid item>
        <IconButton onClick={handleOpenUserMenu} sx={{p: 0}}>
          <Avatar alt={username} src="/static/images/avatar/2.jpg" sx={{bgcolor: stringToColor(username)}}/>
        </IconButton>
        <Menu
          sx={{mt: '45px'}}
          id="menu-appbar"
          anchorEl={anchorElUser}
          anchorOrigin={{
            vertical: 'top',
            horizontal: 'right',
          }}
          transformOrigin={{
            vertical: 'top',
            horizontal: 'right',
          }}
          open={Boolean(anchorElUser)}
          onClose={handleCloseUserMenu}
        >
          {settings.map((setting) => (
            <MenuItem key={setting} data-my-value={setting} onClick={handleCloseUserMenu}>
              <Typography textAlign="center">{setting}</Typography>
            </MenuItem>
          ))}
        </Menu>
      </Grid>
    </Grid>
  )
}

export default UserMenu;