import { configureStore } from "@reduxjs/toolkit";
import authReducer from '../features/authSlice'
import pageReducer from '../features/pagesControlSlice'

export const store = configureStore({
  reducer: {
    auth: authReducer,
    page: pageReducer,
  }
})