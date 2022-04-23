import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    courseId: null,
    content: null
}

export const studentCourseSlice = createSlice({
    name: 'student_course',
    initialState: {
        value: initialState
    },
    reducers: {
        setCourse: (state, action) => {
            state.value.courseId = action.payload.courseId
            state.value.content = action.payload.content
        },
        reset: (state) => {
            state.value = initialState;
        },
    }
});

export const { setCourse, reset } = studentCourseSlice.actions;

export default studentCourseSlice.reducer;