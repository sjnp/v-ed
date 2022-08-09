import {Avatar, Grid, IconButton, Menu, MenuItem, Typography} from "@mui/material";
import {useEffect, useState} from "react";
import { useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import useLogout from "../hooks/useLogout";
import stringToColor from './stringToColor';

import Skeleton from '@mui/material/Skeleton'

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'

// url
import { URL_GET_PROFILE } from "../utils/url";

// feature slice
import { updatePicture } from '../features/profileSlice'

const UserMenu = () => {
  const logout = useLogout();
  const navigate = useNavigate();
  const apiPrivage = useApiPrivate();
  const dispatch = useDispatch();

  const username = useSelector((state) => state.auth.value.username);
  const uriPicture = useSelector((state) => state.profile.value.uriPicture);
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
        navigate('/account-manage/profile');
        break;
      case 'Logout':
        signOut();
        break;
      default:
        break;
    }
  };

  const [ displayUrl, setDisplayUrl ] = useState('')
  useEffect(async () => {
    if (roles.includes('ADMIN')) {
      setDisplayUrl(null)
      dispatch( updatePicture({ uriPicture: null }) )
      return
    }
    if (uriPicture) {
      setDisplayUrl(uriPicture)
    } else {
      const response = await apiPrivage.get(URL_GET_PROFILE)
      if (response.status === 200) {
        setDisplayUrl(response.data.displayUrl)
        dispatch( updatePicture({ uriPicture: response.data.displayUrl }) )
      }
    }
  }, [uriPicture])

  return (
    <Grid
      container
      alignItems='center'
      justifyContent='flex-end'
      spacing={2}
    >
      <Grid item>
        {
          displayUrl === '' ?
          <Skeleton variant='circular' width={40} height={40} />
          :
          <IconButton onClick={handleOpenUserMenu} sx={{p: 0}}>
            <Avatar
              alt={username} 
              src={displayUrl || "/static/images/avatar/2.jpg"}
              sx={{bgcolor: stringToColor(username)}}
            />
          </IconButton>
        }
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