import { Box, Container } from "@mui/material";
import AppBarSearchHeader from "../components/AppBarSearchHeader";
import AccountManageDrawer from "../components/AccountManageSideBar";
import AccountManageProfile from "../components/AccountManageProfile";
import AccountManagePicture from "../components/AccountManagePicture";
import AccountManagePassword from "../components/AccountManagePassword";
import AccountManageInstructor from "../components/AccountManageInstructor";
import { useSelector } from "react-redux";

const AccountManage = () => {
  const page = useSelector((state) => state.page.value.accountManagePage);

  const componentRender = () => {
    switch (page) {
      case 0 : return <AccountManageProfile/>
      case 1 : return <AccountManagePicture/>
      case 2 : return <AccountManagePassword/>
      case 3 : return <AccountManageInstructor/>
    }
  }

  return (
    <Container maxWidth="lg">
      <AppBarSearchHeader />
      <Box sx={{
          display: 'flex',
          flexDirection: 'row',
          alignItems: 'flex-start',
        }}>
        <Box sx={{flex : 2, mt: 3}}>
          <AccountManageDrawer/>
        </Box>
        <Box sx={{minWidth: '30px'}}/>
        <Box sx={{flex : 10, mt: 3,display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
          {componentRender()}
        </Box>
      </Box>
    </Container>
  )
}

export default AccountManage;