import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  username: '',
  roles: [],
  access_token: '',
  persist: JSON.parse(localStorage.getItem("v_ed_persist")) || false
}

export const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    setAuth: (state, action) => {
      state.username = action.payload.username;
      state.roles = action.payload.roles;
      state.access_token = action.payload.access_token;
    },
    setTokenAndRoles: (state, action) => {
      state.roles = action.payload.roles;
      state.access_token = action.payload.access_token;
    },
    setPersist: (state, action) => {
      state.persist = action.payload;
      localStorage.setItem("v_ed_persist", action.payload);
    },
    reset: (state) => {
      state = initialState;
      console.log(`This redux ${state}`)
    },
  }
});

export const { setAuth, setTokenAndRoles, setPersist, reset } = authSlice.actions;

export default authSlice.reducer;