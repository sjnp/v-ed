import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  username: '',
  roles: [],
  access_token: '',
  persist: JSON.parse(localStorage.getItem("v_ed_persist")) || false
}

export const authSlice = createSlice({
  name: 'auth',
  initialState : {value: initialState},
  reducers: {
    setAuth: (state, action) => {
      state.value.username = action.payload.username;
      state.value.roles = action.payload.roles;
      state.value.access_token = action.payload.access_token;
    },
    setTokenAndRoles: (state, action) => {
      state.value.roles = action.payload.roles;
      state.value.access_token = action.payload.access_token;
    },
    setPersist: (state, action) => {
      state.value.persist = action.payload;
      localStorage.setItem("v_ed_persist", action.payload);
    },
    reset: (state) => {
      state.value = initialState;
    },
  }
});

export const { setAuth, setTokenAndRoles, setPersist, reset } = authSlice.actions;

export default authSlice.reducer;