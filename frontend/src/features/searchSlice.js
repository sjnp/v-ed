import { createSlice } from "@reduxjs/toolkit"

const initialState = {
    name: null,
    category: null,
    minPrice: null,
    maxPrice: null,
    rating: null,
    searching: 0
}

export const searchSlice = createSlice({
    name: 'search',
    initialState : {
        value: initialState
    },
    reducers: {
        setName: (state, action) => {
            state.value.name = action.payload.name
        },
        setCategory: (state, action) => {
            state.value.category = action.payload.category
        },
        setMinPrice: (state, action) => {
            state.value.minPrice = action.payload.minPrice
        },
        setMaxPrice: (state, action) => {
            state.value.maxPrice = action.payload.maxPrice
        },
        setRating: (state, action) => {
            state.value.rating = action.payload.rating
        },
        doSearching: (state, action) => {
            state.value.searching = state.value.searching + 1
        },
        reset: (state) => {
            state.value = initialState
        }
    }
})

export const { 
    setName,
    setCategory,
    setMinPrice,
    setMaxPrice,
    setRating,
    doSearching,
    reset,
} = searchSlice.actions

export default searchSlice.reducer