import React, { useState } from 'react'
import { useNavigate, useSearchParams } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'

// Material UI component
import Box from '@mui/material/Box'
import InputBase from '@mui/material/InputBase'
import IconButton from '@mui/material/IconButton'

// Material UI icon
import SearchIcon from '@mui/icons-material/Search'

// feature
import { setName } from '../features/searchSlice'

const SearchBox = () => {

  const navigate = useNavigate()
  const dispatch = useDispatch()
  const [ searchParams ] = useSearchParams()

  const nameRedex = useSelector(state => state.search.value.name)
  const categoryRedex = useSelector(state => state.search.value.category)
  const minPriceRedex = useSelector(state => state.search.value.minPrice)
  const maxPriceRedex = useSelector(state => state.search.value.maxPrice)
  const ratingRedex = useSelector(state => state.search.value.rating)

  const [ search, setSearch ] = useState(searchParams.get('name') || '')

  const handleChangeSearch = (event) => {
    setSearch(event.target.value)
    dispatch( setName({ name: event.target.value }) )
  }

  const handleKeyDownSearch = (event) => {
    if (event.keyCode === 13) {
      handleSearch()
    }
  }

  const handleSearch = () => {
    if (search.trim() === '') return

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

    // navigate(`/search?name=${search}`)
  }

  return (
    <Box bgcolor='#f5f5f5' borderRadius={5} p='2px 4px' display='flex' alignItems='center'>
      <InputBase 
        placeholder='Search...'
        sx={{ ml: 1, width: '100%' }}
        value={search}
        onChange={handleChangeSearch}
        onKeyDown={handleKeyDownSearch}
      />
      <IconButton size='small' onClick={handleSearch}>
        <SearchIcon  />
      </IconButton>
    </Box>
  )
}

export default SearchBox