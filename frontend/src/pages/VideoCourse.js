import React from 'react'
import { useParams } from 'react-router-dom'
import ReactPlayer from 'react-player'

// component
import AppBarHeader from '../components/AppBarHeader'
import MaterialFileDownload from '../components/MaterialFileDownload'
import Grid from '@mui/material/Grid';

// Material UI
import Container from '@mui/material/Container'
import Typography from '@mui/material/Typography'

const VideoCourse = () => {
    
    const { id } = useParams()
    
    return (
        <Container maxWidth="lg">
            <AppBarHeader />
            <Typography variant="h5" sx={{ fontWeight: 'bold' }}>
                Student Video {id}
            </Typography>

            <Grid container rowSpacing={1} sx={{ marginTop: 1 }}>

        <Grid item xs={8}>
          <ReactPlayer
            url='https://www.youtube.com/watch?v=u798bXYXz2w'
            controls={true}
            style={{ margin: 'auto' }}
          />
        </Grid>
        
        <Grid item xs={4}>
            <MaterialFileDownload />
        </Grid>
        
      </Grid>
      
            {/* <ReactPlayer
                url='https://www.youtube.com/watch?v=u798bXYXz2w'
                controls={true}
                style={{ margin: 'auto' }}
            /> */}

            {/* <MaterialFileDownload /> */}

        </Container>
    )
}

export default VideoCourse