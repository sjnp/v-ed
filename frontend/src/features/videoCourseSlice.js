import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    courseId: null,
    videoUri: null,
    materials: []
}

export const videoCourseSlice = createSlice({
    name: 'student_course',
    initialState: {
        value: initialState
    },
    reducers: {
        setVideo: (state, action) => {
            state.value.courseId = action.payload.courseId
            state.value.videoUri = action.payload.videoUri
            state.value.materials = action.payload.materials
        },
        reset: (state) => {
            state.value = initialState;
        },
    }
});

export const { setVideo, reset } = videoCourseSlice.actions;

export default videoCourseSlice.reducer;