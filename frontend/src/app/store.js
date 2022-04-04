import { configureStore } from "@reduxjs/toolkit";

import authReducer from '../features/authSlice'
import pageReducer from '../features/pagesControlSlice'
import createdCourseReducer from '../features/createdCourseSlice'

export const store = configureStore({
  reducer: {
    auth: authReducer,
    page: pageReducer,
    createdCourse: createdCourseReducer
  }
})