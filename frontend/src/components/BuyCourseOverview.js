import React from 'react'
import { useNavigate } from 'react-router-dom'

// Material UI
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import CardHeader from '@mui/material/CardHeader'
import Avatar from '@mui/material/Avatar'
import Typography from '@mui/material/Typography'
import Rating from "@mui/material/Rating"
import StarIcon from '@mui/icons-material/Star'
import Paper from '@mui/material/Paper'

const BuyCourseOverview = () => {

  const navigate = useNavigate()

  const handleClickBuyCourse = () => navigate('/payment')

  const imageURL = 'https://www.cats.org.uk/media/2297/tabby-cat-looking-up.jpg?width=1600'
  const title = "Material UI from basic to advance"
  const subheader = "Pradinan Benjanavee"
  const rating = 4.8
  const reviewTotal = 125
  const price = 500

  return (
    <Paper>
      <CardHeader
        avatar={ <Avatar src={imageURL} /> } 
        title={title}
        subheader={subheader}
      />
      <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>

        <Rating
          value={rating} 
          size="large" 
          readOnly 
          precision={0.1} 
          emptyIcon={<StarIcon fontSize="inherit" />}
          sx={{ marginTop: 2, marginBottom: 1 }}
        />

        <Typography variant="body1" sx={{ marginBottom: 3 }}>
            {rating} ({reviewTotal})
        </Typography>

        <Typography variant="h6" sx={{ marginBottom: 3 }}>
            {price} THB
        </Typography>

        <Button variant="contained" onClick={handleClickBuyCourse} sx={{ marginBottom: 5 }}>
            Buy Now
        </Button>

      </Box>
    </Paper>
  )
}

export default BuyCourseOverview