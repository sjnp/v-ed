import { configureStore } from "@reduxjs/toolkit";
import authReducer from '../features/authSlice'
import createdCourseReducer from '../features/createdCourseSlice'

export const store = configureStore({
  reducer: {
    auth: authReducer,
    createdCourse: createdCourseReducer
  }
})