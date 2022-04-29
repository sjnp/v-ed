import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    questionId: null,
    topic: null,
    detail: null,
    datetime: null,
}

export const questionBoardSlice = createSlice({
    name: 'question_board',
    initialState: {
        value: initialState
    },
    reducers: {
        setQuestionBoard: (state, action) => {
            state.value.questionId = action.payload.questionId
            state.value.topic = action.payload.topic
            state.value.detail = action.payload.detail
            state.value.datetime = action.payload.datetime
        },
        resetQuestionBoard: (state) => {
            state.value = initialState;
        },
    }
});

export const { setQuestionBoard, resetQuestionBoard } = questionBoardSlice.actions;

export default questionBoardSlice.reducer;