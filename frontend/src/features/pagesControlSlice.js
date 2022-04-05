import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  accountManagePage : 0,

}

export const pagesControlSlice = createSlice({
  name: 'page',
  initialState : {value: initialState},
  reducers: {
    setAccountManagePage: (state, action) => {
      state.value.accountManagePage = action.payload.page;
    },
  }
});

export const { setAccountManagePage } = pagesControlSlice.actions;

export default pagesControlSlice.reducer;