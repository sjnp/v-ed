import React, { useState } from 'react'

// component
import LoadingCircle from './LoadingCircle'

// Materail UI component
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'

const OverviewTabOverview = ({ data }) => {

    const minHeight = data ? 500 : 0

    return (
        <Box minHeight={minHeight} paddingTop={5} paddingLeft={20} paddingRight={20}>
        {
            data ?
            <Typography variant='subtitle1'>{data}</Typography>
            :
            <LoadingCircle loading={data ? false : true} layoutLeft={50} />
        }    
            
        </Box>
    )
}

export default OverviewTabOverview