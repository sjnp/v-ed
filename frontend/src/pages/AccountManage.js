import {Box, Container, Typography } from "@mui/material";
import AppBarHeader from "../components/AppBarHeader";
import AccountManageDrawer from "../components/AccountManageSideBar";
import AccountManageProfile from "../components/AccountManageProfile";
import AccountManagePicture from "../components/AccountManagePicture";
import AccountManagePassword from "../components/AccountManagePassword";
import AccountManageInstructor from "../components/AccountManageInstructor";


const AccountManage = () => {
  return (
    <Container maxWidth="lg">
      <AppBarHeader />
      <Box sx={{
          display: 'flex',
          flexDirection: 'row',
          alignItems: 'flex-start',
        }}>
        <Box sx={{flex : 2, mt: 3}}>
          <AccountManageDrawer/>
        </Box>
        <Box sx={{minWidth: '30px'}}/>
        <Box sx={{flex : 10, mt: 3,display: 'flex', flexDirection: 'column',alignItems: 'center'}}>
          <AccountManageProfile/>
          <AccountManagePicture/>
          <AccountManagePassword/>
          <AccountManageInstructor/>
        </Box>
      </Box>
    </Container>
  )
}

export default AccountManage;