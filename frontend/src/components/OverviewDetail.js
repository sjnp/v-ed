import React, { useState } from 'react'

// component
import TabOverview from './TabOverview'
import TabContent from './TabContent'
import TabRequirement from './TabRequirement'
import TabReview from './TabReview'
import TabInstructor from './TabInstructor'

// Materail UI
import Tabs from '@mui/material/Tabs'
import Tab from '@mui/material/Tab'
import Box from '@mui/material/Box'

const OverviewDetail = () => {

    const [ tab, setTab ] = useState(0)
    const handleClickTab = (event, newTab) => setTab(newTab)

    const tabIndex = {
        overview: 0,
        content: 1,
        requirement: 2,
        review: 3,
        instructor: 4
    }

    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Tabs value={tab} onChange={handleClickTab}>
                    <Tab label="Overview" sx={{ width: '20%' }} />
                    <Tab label="Content" sx={{ width: '20%' }} />
                    <Tab label="Requirement" sx={{ width: '20%' }} />
                    <Tab label="Review" sx={{ width: '20%' }} />
                    <Tab label="Instructor" sx={{ width: '20%' }} />
                </Tabs>
            </Box>
            <Box hidden={tab !== tabIndex.overview}>
                <TabOverview />
            </Box>
            <Box hidden={tab !== tabIndex.content}>
                <TabContent />
            </Box>
            <Box hidden={tab !== tabIndex.requirement}>
                <TabRequirement />
            </Box>
            <Box hidden={tab !== tabIndex.review}>
                <TabReview />
            </Box>
            <Box hidden={tab !== tabIndex.instructor}>
                <TabInstructor />
            </Box>
        </Box>
    )
}

export default OverviewDetail