import React, { useState } from 'react'
import { useDispatch } from 'react-redux'

// Material UI component
import Accordion from '@mui/material/Accordion'
import AccordionDetails from '@mui/material/AccordionDetails'
import AccordionSummary from '@mui/material/AccordionSummary'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Rating from '@mui/material/Rating'
import List from '@mui/material/List'
import ListItemButton from '@mui/material/ListItemButton'
import ListItemText from '@mui/material/ListItemText'

// Material UI icon
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'
import StarIcon from '@mui/icons-material/Star'

// feature
import { setRating } from '../features/searchSlice'

const FilterRating = () => {

    const dispatch = useDispatch()

    const ratings = [5, 4, 3, 2, 1, 'No review', 'All']

    const [ active, setActive ] = useState('All')

    const handleClickRating = (value) => {
        setActive(value)
        dispatch( setRating({ rating: active }) )
    }

    const getMessageSelectRating = () => {
        if (active === 5) {
            return `${active} stars`
        } else if (active >= 1 && active <= 4) {
            return `${active} stars or more`
        } else {
            return active
        }
    }

    return (
        <Accordion>
            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                <Typography fontWeight='bold'>Rating</Typography>
                <Typography color='gray' pl={1}>( {getMessageSelectRating()} )</Typography>
            </AccordionSummary>
            <AccordionDetails>
                <List>
                {
                    ratings.map((rating, index) => (
                        <ListItemButton key={index} selected={active === rating} onClick={() => handleClickRating(rating)}>
                        {
                            rating === 'All' || rating === 'No review' ?
                            <ListItemText primary={rating} />
                            :
                            <Grid container>
                                <Grid item>
                                    <ListItemText 
                                        primary={
                                            <Rating 
                                                value={rating} 
                                                readOnly 
                                                emptyIcon={<StarIcon fontSize="inherit" />} 
                                            />
                                        } 
                                    />
                                </Grid>
                                <Grid item>
                                {
                                    rating === 5 ?
                                    null
                                    :
                                    <ListItemText primary='or more' sx={{ pl: 1 }} />
                                }
                                </Grid>
                            </Grid>
                        }
                        </ListItemButton>
                    ))
                }
                </List>
            </AccordionDetails>
        </Accordion>
    )
}

export default FilterRating