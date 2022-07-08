import React from 'react'
import { useNavigate, useSearchParams } from "react-router-dom"
import { useDispatch, useSelector } from 'react-redux'

// component
import FilterCategory from './FilterCategory'
import FilterPrice from './FilterPrice'
import FilterRating from './FilterRating'

// Material UI component
import Box from '@mui/material/Box'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import LoadingButton from '@mui/lab/LoadingButton'

// feature
import { doSearching } from '../features/searchSlice'

const FilterSearch = () => {
    
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const [ searchParams ] = useSearchParams()

    const nameRedex = useSelector(state => state.search.value.name)
    const categoryRedex = useSelector(state => state.search.value.category)
    const minPriceRedex = useSelector(state => state.search.value.minPrice)
    const maxPriceRedex = useSelector(state => state.search.value.maxPrice)
    const ratingRedex = useSelector(state => state.search.value.rating)
    const searching = useSelector(state => state.search.value.searching)

    const handleClickSearch = () => {
        const name = nameRedex || searchParams.get('name')
        const category = categoryRedex || searchParams.get('category')
        const minPrice = minPriceRedex || searchParams.get('min_price')
        const maxPrice = maxPriceRedex || searchParams.get('max_price')
        const rating = ratingRedex || searchParams.get('rating')

        const params = [
            { name: 'name', value: name },
            { name: 'category', value: category },
            { name: 'min_price', value: minPrice },
            { name: 'max_price', value: maxPrice },
            { name: 'rating', value: rating }
        ]

        const queryString = params
            .filter(param => param.value !== null)
            .map(param => param.name + '=' + param.value)
            .join('&')

        navigate(`/search?${queryString}`)
        dispatch( doSearching() )
    }

    return (
        <Box pl={1} pr={1}>
            <Grid container>
                <Grid item xs={6}>
                    <Typography variant='h6' pl={1} pb={1}>Filter</Typography>
                </Grid>
                <Grid item xs={6}>
                    <Grid container justifyContent="flex-end" pr={1}>
                        <LoadingButton variant='outlined' size='small' onClick={handleClickSearch}>
                            Search
                        </LoadingButton>
                    </Grid>
                </Grid>
            </Grid>
            <FilterCategory />
            <FilterPrice />
            <FilterRating />
        </Box>
    )
}

export default FilterSearch