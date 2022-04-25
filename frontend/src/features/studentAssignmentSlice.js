import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    assignment: []
}

export const studentAssignmentSlice = createSlice({
    name: 'student_assignment',
    initialState: {
        value: initialState
    },
    reducers: {
        setAssignment: (state, action) => {
            state.value.assignment = action.payload.assignment
        },
        resetAssignment: (state) => {
            state.value = initialState;
        },
    }
});

export const { setAssignment, resetAssignment } = studentAssignmentSlice.actions;

export default studentAssignmentSlice.reducer;