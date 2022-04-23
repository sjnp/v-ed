import { configureStore } from "@reduxjs/toolkit";

import authReducer from '../features/authSlice'
import pageReducer from '../features/pagesControlSlice'
import createdCourseReducer from '../features/createdCourseSlice'
import studentCourseReducer from '../features/studentCourseSlice'
import videoCourseReducer from '../features/videoCourseSlice'

export const store = configureStore({
  reducer: {
    auth: authReducer,
    page: pageReducer,
    createdCourse: createdCourseReducer,
    studentCourse: studentCourseReducer,
    videoCourse: videoCourseReducer
  }
})