import React, { useState } from 'react'
import { useDispatch } from 'react-redux'
import { useSearchParams } from 'react-router-dom'

// Material UI component
import Accordion from '@mui/material/Accordion'
import AccordionDetails from '@mui/material/AccordionDetails'
import AccordionSummary from '@mui/material/AccordionSummary'
import Grid from '@mui/material/Grid'
import TextField from '@mui/material/TextField'
import Typography from '@mui/material/Typography'

// Material UI icon
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'

// feature
import { setMinPrice, setMaxPrice } from '../features/searchSlice'

const FilterPrice = () => {

    const dispatch = useDispatch()
    const [ searchParams ] = useSearchParams()

    const [ min, setMin ] = useState(searchParams.get('min_price') || '')
    const [ max, setMax ] = useState(searchParams.get('max_price') || '')
    const [ error, setError ] = useState('')

    const getPrice = (oldValue, newValue) => {
        if (newValue === '') return ''

        const number = Number(newValue)
        if (Number.isNaN(number) || number < 0) {
            setError('Please, enter number only')
            return oldValue
        }

        setError('')
        return number
    }

    const handleChangeMin = (event) => {
        const minPrice = getPrice(min, event.target.value)
        checkErrorMinLessThanMax(minPrice)
        setMin(minPrice)
        dispatch( setMinPrice({ minPrice: minPrice || null }) )
    }

    const handleChangeMax = (event) => {
        const maxPrice = getPrice(max, event.target.value)
        checkErrorMaxMoreThanMin(maxPrice)
        setMax(maxPrice)
        dispatch( setMaxPrice({ maxPrice: maxPrice || null }) )
    }

    const handleBlurError = () => {
        if (error === 'Please, enter number only') {
            setError('')
        }
    }

    const checkErrorMinLessThanMax = (newMin) => {
        if (newMin > max && newMin && max) {
            setError('Min price must < max price')
        }
    }

    const checkErrorMaxMoreThanMin = (newMax) => {
        if (newMax < min && newMax && min) {
            setError('Max price must > min price')
        }
    }

    const getMessagePrice = () => {
        if (min && max) {
            return `${min} - ${max}`
        } else if (min && !max) {
            return `${min} or more`
        } else if (!min && max) {
            return `limit ${max}`
        } else if (!min && !max) {
            return 'all'
        }
    }

    return (
        <Accordion>
            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                <Typography fontWeight='bold'>Price</Typography>
                <Typography color='gray' pl={1}>( {getMessagePrice()} )</Typography>
            </AccordionSummary>
            <AccordionDetails>
                <Grid container spacing={1}>
                    <Grid item xs={6}>
                        <TextField
                            name='min'
                            type='text'
                            label='Min price'
                            size='small'
                            variant='outlined'
                            value={min}
                            onChange={handleChangeMin}
                            onBlur={handleBlurError}
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField
                            name='max'
                            type='text'
                            label='Max price'
                            size='small'
                            variant='outlined'
                            value={max}
                            onChange={handleChangeMax}
                            onBlur={handleBlurError}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <Typography variant='caption' color='red'>{error}</Typography>
                    </Grid>
                </Grid>
            </AccordionDetails>
        </Accordion>
    )
}

export default FilterPrice