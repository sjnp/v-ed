import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  firstName : '',
  lastName : '',
  occupation : '',
  biography : '',
  uriPicture : '',
}

export const profileSlice = createSlice({
  name: 'profile',
  initialState : {value: initialState},
  reducers: {
    setProfile : ( state , action ) => {
      state.value.firstName = action.payload.firstName;
      state.value.lastName = action.payload.lastName;
      state.value.occupation = action.payload.occupation;
      state.value.biography = action.payload.biography;
      state.value.uriPicture = action.payload.uriPicture;
    },
    updateProfile : ( state , action ) => {
      state.value.firstName = action.payload.firstName;
      state.value.lastName = action.payload.lastName;
      state.value.occupation = action.payload.occupation;
      state.value.biography = action.payload.biography;
    },
    updatePicture : ( state , action ) => {
      state.value.uriPicture = action.payload.uriPicture;
    },
    
  }
});

export const { setProfile, updateProfile, updatePicture } = profileSlice.actions;

export default profileSlice.reducer;