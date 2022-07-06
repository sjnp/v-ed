import React, { useState } from 'react'
import { useNavigate, useSearchParams } from 'react-router-dom'
import { useDispatch } from 'react-redux'

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

  const [ search, setSearch ] = useState(searchParams.get('name') || '')

  if (searchParams.get('name')) {
    dispatch( setName({ name: searchParams.get('name') }) )
  }

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
    navigate(`/search?name=${search}`)
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