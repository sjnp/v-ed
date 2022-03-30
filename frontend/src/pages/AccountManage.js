import {Box, Container, Typography } from "@mui/material";
import AppBarHeader from "../components/AppBarHeader";
import AccountManageDrawer from "../components/AccountManageSideBar";
import AccountManageProfile from "../components/AccountManageProfile";

const AccountManage = () => {
  return (
    <Container maxWidth="lg">
      <AppBarHeader />
      <Box sx={{
          display: 'flex',
          flexDirection: 'row',
          alignItems: 'flex-start',
        }}>
        <Box sx={{flex : 3, mt: 3}}>
          <AccountManageDrawer/>
        </Box>
        <Box sx={{minWidth: '30px'}}/>
        <Box sx={{flex : 7, mt: 3}}>
          <AccountManageProfile/>
        </Box>
      </Box>
    </Container>
  )
}

export default AccountManage;