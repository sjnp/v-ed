import React from 'react'

// component
import ContentPaper from './ContentPaper'
import stringToColor from './stringToColor';

// Materail UI component
import Grid from '@mui/material/Grid'
import Avatar from '@mui/material/Avatar'
import Typography from '@mui/material/Typography'

const OverviewTabInstructor = ({ data }) => {

    const { instructorFirstname, instructorLastname, instructorPictureURI, biography, occupation } = data
    
    const instructorName = instructorFirstname + ' ' + instructorLastname

    const NAElement = <Typography variant='caption' color='gray'>N/A</Typography>

    const imageSize = 150

    return (
        <Grid container minHeight={500} paddingTop={5} paddingLeft={20} paddingRight={20}>
            <Grid item xs={3}>
                <Avatar
                    alt={instructorFirstname}
                    src={instructorPictureURI || "/static/images/avatar/2.jpg"} 
                    sx={{ bgcolor: stringToColor(instructorFirstname), width: imageSize, height: imageSize }}
                />
            </Grid>
            <Grid item xs={9}>
                <ContentPaper label='Instructor name' content={instructorName} />
                <ContentPaper label='Biography' content={biography || NAElement} />
                <ContentPaper label='Occupation' content={occupation || NAElement} />
            </Grid>
        </Grid>
    )
}

export default OverviewTabInstructor