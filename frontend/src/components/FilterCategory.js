import React, { useState } from 'react'
import { useDispatch } from 'react-redux'
import { useSearchParams } from 'react-router-dom'

// Material UI component
import Accordion from '@mui/material/Accordion'
import AccordionDetails from '@mui/material/AccordionDetails'
import AccordionSummary from '@mui/material/AccordionSummary'
import Typography from '@mui/material/Typography'
import List from '@mui/material/List'
import ListItemButton from '@mui/material/ListItemButton'
import ListItemText from '@mui/material/ListItemText'

// Material UI icon
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'

// feature
import { setCategory } from '../features/searchSlice'

const FilterCategory = () => {

    const dispatch = useDispatch()
    const [ searchParams ] = useSearchParams()

    const categories = ['All', 'Academic', 'Art', 'Business', 'Design', 'Programming']
    
    const [ active, setActive ] = useState(searchParams.get('category') || categories[0])

    const handleClickCategory = (value) => {
        setActive(value)
        dispatch( setCategory({ category: value }) )
    }

    return (
        <Accordion>
            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                <Typography fontWeight='bold'>Category</Typography>
                <Typography color='gray' pl={1}>{`( ${active} )`}</Typography>
            </AccordionSummary>
            <AccordionDetails>
                <List>
                {
                    categories.map((category, index) => (
                        <ListItemButton key={index} selected={active === category} onClick={() => handleClickCategory(category)}>
                            <ListItemText primary={category} />
                        </ListItemButton>
                    ))
                }
                </List>
            </AccordionDetails>
        </Accordion>
    )
}

export default FilterCategory