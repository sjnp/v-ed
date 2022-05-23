import React from 'react'

// Materail UI component
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'

const OverviewTabReview = ({ data }) => {

    return (
        <Box minHeight={500} paddingTop={5} paddingLeft={20} paddingRight={20}>
        {
            data?.length > 0 ?
            <Typography variant='subtitle1'>Add review list later</Typography>
            :
            <Box display='flex' flexDirection='column' textAlign='center' paddingTop={10}>
                <Typography variant='button' color='gray'>No review now</Typography>
            </Box>
        }   
        </Box>
    )
}

export default OverviewTabReview