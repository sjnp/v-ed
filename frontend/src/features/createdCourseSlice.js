import { createSlice } from "@reduxjs/toolkit"

const initialState = {
  name: '',
  price: '',
  category: null,
  overview: '',
  requirement: '',
  contents: [
    // EXAMPLE CONTENTS
    // {
    //   name: 'Intro to Color Theory',
    //   chapters: [
    //     {
    //       name: 'Basics of Color'
    //     },
    //     {
    //       name: 'Color Opposition'
    //     },
    //     {
    //       name: 'Color Composition'
    //     }
    //   ],
    //   assignments: [
    //     {
    //       detail: 'If I choose purple as the main color, which color would be the secondary and why?'
    //     },
    //     {
    //       detail: 'If I choose red as the main color, which color would be the secondary and why?'
    //     }
    //   ]
    // },
    // {
    //   name: 'Color and Its Feeling',
    //   chapters: [
    //     {
    //       name: 'I am feeling blue'
    //     }
    //   ],
    //   assignments: [
    //     {
    //       detail: 'Why do we feel blue when seeing blue-ish color?'
    //     }
    //   ]
    // }
  ]
}

export const createdCourseSlice = createSlice({
  name: 'createdCourse',
  initialState: { value: initialState },
  reducers: {
    setCourseDetails: (state, action) => {
      state.value.name = action.payload.name;
      state.value.price = action.payload.price;
      state.value.category = action.payload.category;
      state.value.overview = action.payload.overview;
      state.value.requirement = action.payload.requirement;
    },
    addSection: (state, action) => {
      state.value.contents.push(action.payload.section);
    },
    removeSection: (state, action) => {
      const index = Number(action.payload.index);
      state.value.contents.splice(index, 1);
    },
    addChapter: (state, action) => {
      const sectionIndex = Number(action.payload.sectionIndex);
      const chapter = action.payload.chapter;
      state.value.contents[sectionIndex].chapters.push(chapter);
    },
    removeChapter: (state, action) => {
      const sectionIndex = Number(action.payload.sectionIndex);
      const chapterIndex = Number(action.payload.chapterIndex);
      state.value.contents[sectionIndex].chapters.splice(chapterIndex, 1);
    },
    addAssignment: (state, action) => {
      const sectionIndex = Number(action.payload.sectionIndex);
      const assignment = action.payload.assignment;
      state.value.contents[sectionIndex].assignments.push(assignment);

    },
    removeAssignment: (state, action) => {
      const sectionIndex = Number(action.payload.sectionIndex);
      const chapterIndex = Number(action.payload.chapterIndex);
      state.value.contents[sectionIndex].assignments.splice(chapterIndex, 1);
    },
    setName: (state, action) => {
      state.value.name = action.payload.name;
    },
    setPrice: (state, action) => {
      state.value.price = action.payload.price;
    },
    setCategory: (state, action) => {
      state.value.category = action.payload.category;
    },
    setOverview: (state, action) => {
      state.value.overview = action.payload.overview;
    },
    setRequirement: (state, action) => {
      state.value.requirement = action.payload.requirement;
    },

  }
});

export const { setCourseDetails, addSection, removeSection, addChapter, removeChapter, addAssignment, removeAssignment, setName, setPrice, setCategory, setOverview, setRequirement } = createdCourseSlice.actions;

export default createdCourseSlice.reducer;