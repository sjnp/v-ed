import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  bankBrand: '',
  bankAccountName: '',
  bankAccountNumber: '',
  recipientName: '',
  type: 'individual'
}

export const financeControlSlice = createSlice({
  name: 'finance',
  initialState : {value: initialState},
  reducers: {
    setFinanceAccount: (state, action) => {
      
      action.payload.name = action.payload.value;
      console.log([action.payload.name] , action.payload.value );
      console.log(action.payload );
    },
  }
});

export const { setFinanceAccount } = financeControlSlice.actions;

export default financeControlSlice.reducer;