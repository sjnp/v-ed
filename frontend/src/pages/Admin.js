import {Box, Container, Typography} from "@mui/material";
import AppBarHeader from "../components/AppBarHeader";
import Tabs from "@mui/material/Tabs";
import {useState} from "react";
import Tab from "@mui/material/Tab";
import PendingCourseList from "../components/PendingCourseList";
import PendingReportList from "../components/PendingReportList";

const Admin = () => {
  const [tabIndex, setTabIndex] = useState(0);

  const tabs = [
    {label: 'Course', children: <PendingCourseList/>},
    {label: 'Report', children: <PendingReportList/>}
  ]

  const handleTabChange = (event, newTabIndex) => {
    setTabIndex(newTabIndex);
  };

  return (
    <Container maxWidth="lg">
      <AppBarHeader/>
      <Box sx={{pt: 2}}>
        <Box sx={{borderBottom: 1, borderColor: 'divider'}}>
          <Tabs value={tabIndex} onChange={handleTabChange}>
            {tabs.map((tab, index) => <Tab key={index} label={tab.label}/>)}
          </Tabs>
        </Box>
        <Box sx={{pt: 3}}>
          {tabs.map((tab, index) =>
            <div
              key={index}
              hidden={tabIndex !== index}
            >
              {tabIndex === index && tab.children}
            </div>)}
        </Box>
      </Box>
    </Container>
  )
}

export default Admin;