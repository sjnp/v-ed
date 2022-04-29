import {Box, Container, Typography} from "@mui/material";
import AppBarHeader from "../components/AppBarHeader";
import Tabs from "@mui/material/Tabs";
import {useState} from "react";
import Tab from "@mui/material/Tab";
import PendingCourses from "../components/PendingCourses";

const Admin = () => {
  const [tabIndex, setTabIndex] = useState(0);

  const tabs = [
    {label: 'Course', children: <PendingCourses/>},
    {
      label: 'Report', children: <div><Typography>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. In congue placerat risus. Proin at volutpat ligula.
        Curabitur diam urna, dapibus ut auctor ut, ullamcorper in urna. Aenean in nulla dui. Sed feugiat tortor sed
        lorem gravida elementum. Nunc rutrum ornare porta. Vestibulum imperdiet lorem eu lacus fermentum, id hendrerit
        ante auctor. Praesent in velit semper, tempor mi ac, dictum neque. Aenean interdum fringilla magna. Ut feugiat
        ultrices mi at gravida. Cras elit ligula, tempus in malesuada et, dictum a eros. Vestibulum ante ipsum primis
        in faucibus orci luctus et ultrices posuere cubilia curae; Sed quis condimentum nisi, ac vehicula lacus.
        Curabitur magna diam, malesuada et elit quis, aliquam ultricies dolor. Morbi fringilla vitae arcu nec posuere.
      </Typography></div>
    }
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