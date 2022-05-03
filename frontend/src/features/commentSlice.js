import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    comments: []
}

export const commentSlice = createSlice({
    name: 'comment',
    initialState: {
        value: initialState
    },
    reducers: {
        setComment: (state, action) => {
            state.value.comments = action.payload.comments
        },
        resetComment: (state) => {
            state.value = initialState;
        },
    }
});

export const { setComment, resetComment } = commentSlice.actions;

export default commentSlice.reducer;