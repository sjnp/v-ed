

import React, { useState, useEffect } from 'react'
import { useSelector } from 'react-redux'
import { useSearchParams } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import FilterSearch from '../components/FilterSearch'
import LoadingCircle from '../components/LoadingCircle'
import CourseCardWide from '../components/CourseCardWide'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'

// url
import { URL_SEARCH } from '../utils/url'

const Search = () => {

  const apiPrivate = useApiPrivate()
  const [ searchParams ] = useSearchParams()

  const nameRedex = useSelector(state => state.search.value.name)
  const categoryRedex = useSelector(state => state.search.value.category)
  const minPriceRedex = useSelector(state => state.search.value.minPrice)
  const maxPriceRedex = useSelector(state => state.search.value.maxPrice)
  const ratingRedex = useSelector(state => state.search.value.rating)
  const searching = useSelector(state => state.search.value.searching)

  const [ searches, setSearches ] = useState([])
  const [ loading, setLoading ] = useState(false)

  useEffect(async () => {
    setLoading(true)

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

    const url = URL_SEARCH + '?' + queryString
    const response = await apiPrivate.get(url)

    if (response.status === 200) {
      setSearches(response.data)
    } else {
      alert('Error search')
    }
    setLoading(false)
  }, [searching])

  return (
    <Container maxWidth='lg'>
      <AppBarSearchHeader />
      <Grid container mt={3} mb={5}>
        <Grid item xs={3}>
          <FilterSearch />
        </Grid>
        <Grid item xs={1}></Grid>
        <Grid item xs={7}>
        {
          loading ?
            <LoadingCircle loading={loading} centerY={true} />
            :
            searches?.length === 0 ?
            <div>Search not match</div>
            :
            searches?.map((search, index) => (
              <CourseCardWide
                key={index}
                image={search.pictureURL}
                courseName={search.courseName}
                instructorName={search.instructorName}
                rating={search.rating}
                reviewCount={search.reviewCount}
                pathOnClick={'/overview/course/' + search.courseId}
                price={search.price}

              />
            ))
        }
        <Grid item xs={1}></Grid>
        </Grid>
      </Grid>
    </Container>
  )
}

export default Search