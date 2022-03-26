import { createSlice } from "@reduxjs/toolkit"

const initialStateValue = {
  firstName: "",
  lastName: "",
  email: "",
  profilePicture: ""
}

const userSlice = createSlice({
  name: "user",
  initialState: {value: initialStateValue},
  reducers: {
    setUser: (state, action) => {
      state.value = action.payload
    },
    resetUser: (state, action) => {
      state.value = initialStateValue
    }
  }
})

export const { setUser, resetUser } = userSlice.actions 

export default userSlice.reducer