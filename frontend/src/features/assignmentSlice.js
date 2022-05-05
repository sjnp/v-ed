import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    chapterNo: null,
    assignments: [],
}

export const assignmentStudent = createSlice({
    name: 'assignment',
    initialState: {
        value: initialState
    },
    reducers: {
        setAssignment: (state, action) => {
            state.value.chapterNo = action.payload.chapterNo
            state.value.assignments = action.payload.assignments
        },
        resetAssignment: (state) => {
            state.value = initialState;
        },
    }
})

export const { setAssignment, resetAssignment } = assignmentStudent.actions;

export default assignmentStudent.reducer;