import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  reasonReports: []
}

export const reasonReportSlice = createSlice({
  name: 'reason_report',
  initialState: {
    value: initialState
  },
  reducers: {
    setReasonReport: (state, action) => {
      state.value.reasonReports = action.payload.reasonReports
    },
    resetReasonReport: (state, action) => {
      state.value = initialState
    },
  }
});

export const { setReasonReport, resetReasonReport } = reasonReportSlice.actions;

export default reasonReportSlice.reducer;