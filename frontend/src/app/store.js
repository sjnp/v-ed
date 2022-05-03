import { configureStore } from "@reduxjs/toolkit";

import authReducer from '../features/authSlice'
import pageReducer from '../features/pagesControlSlice'
import createdCourseReducer from '../features/createdCourseSlice'
import studentCourseReducer from '../features/studentCourseSlice'
import videoCourseReducer from '../features/videoCourseSlice'
import studentAssignmentReducer from '../features/studentAssignmentSlice'
import questionBoardReducer from '../features/questionBoardSlice'
import commentReducer from '../features/commentSlice'

export const store = configureStore({
  reducer: {
    auth: authReducer,
    page: pageReducer,
    createdCourse: createdCourseReducer,
    studentCourse: studentCourseReducer,
    videoCourse: videoCourseReducer,
    studentAssignment: studentAssignmentReducer,
    questionBoard: questionBoardReducer,
    comment: commentReducer
  }
})