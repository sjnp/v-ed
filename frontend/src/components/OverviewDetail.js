import React, { useState } from 'react'

// component
import OverviewTabOverview from './OverviewTabOverview'
import OverviewTabContent from './OverviewTabContent'
import OverviewTabRequirement from './OverviewTabRequirement'
import OverviewTabReview from './OverviewTabReview'
import OverviewTabInstructor from './OverviewTabInstructor'

// Materail UI
import Tabs from '@mui/material/Tabs'
import Tab from '@mui/material/Tab'
import Box from '@mui/material/Box'

const OverviewDetail = ({ data }) => {

    const [ selectTab, setSelectTab ] = useState(0)

    const handleSelectTab = (event, newTab) => {
        setSelectTab(newTab)
    }

    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Tabs value={selectTab} onChange={handleSelectTab}>
                    <Tab label='overview' sx={{ width: '20%' }} />
                    <Tab label='content' sx={{ width: '20%' }} />
                    <Tab label='requirement' sx={{ width: '20%' }} />
                    <Tab label='review' sx={{ width: '20%' }} />
                    <Tab label='instructor' sx={{ width: '20%' }} />
                </Tabs>
            </Box>
            <Box hidden={selectTab !== 0}>
                <OverviewTabOverview data={data.overview} />
            </Box>
            <Box hidden={selectTab !== 1}>
                <OverviewTabContent data={data.chapterList} />
            </Box>
            <Box hidden={selectTab !== 2}>
                <OverviewTabRequirement data={data.requirement} />
            </Box>
            <Box hidden={selectTab !== 3}>
                <OverviewTabReview data={data.reviewList} />
            </Box>
            <Box hidden={selectTab !== 4}>
                <OverviewTabInstructor data={data} />
            </Box>
        </Box>
    )
}

export default OverviewDetail