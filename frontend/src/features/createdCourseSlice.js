import { createSlice } from "@reduxjs/toolkit"

const initialState = {
  name: '',
  price: '',
  category: null,
  overview: '',
  requirement: '',
  pictureUrl: '',
  chapters: [
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
    addChapter: (state, action) => {
      state.value.chapters.push(action.payload.chapter);
    },
    removeChapter: (state, action) => {
      const index = action.payload.index;
      state.value.chapters.splice(index, 1);
    },
    addSection: (state, action) => {
      const chapterIndex = Number(action.payload.chapterIndex);
      const section = action.payload.section;
      state.value.chapters[chapterIndex].sections.push(section);
    },
    removeSection: (state, action) => {
      const chapterIndex = Number(action.payload.chapterIndex);
      const sectionIndex = Number(action.payload.sectionIndex);
      state.value.chapters[chapterIndex].sections.splice(sectionIndex, 1);
    },
    addAssignment: (state, action) => {
      const chapterIndex = Number(action.payload.chapterIndex);
      const assignment = action.payload.assignment;
      state.value.chapters[chapterIndex].assignments.push(assignment);

    },
    removeAssignment: (state, action) => {
      const chapterIndex = Number(action.payload.chapterIndex);
      const assignmentIndex = Number(action.payload.assignmentIndex);
      state.value.chapters[chapterIndex].assignments.splice(assignmentIndex, 1);
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
    setPictureUrl: (state, action) => {
      state.value.pictureUrl = action.payload.pictureUrl;
    },
    setChapters: (state, action) => {
      state.value.chapters = action.payload.chapters;
    },
    setVideoUri: (state, action) => {
      const chapterIndex = Number(action.payload.chapterIndex);
      const sectionIndex = Number(action.payload.sectionIndex);
      state.value.chapters[chapterIndex].sections[sectionIndex].videoUri = action.payload.videoUri;
    },
    setHandoutUri: (state, action) => {
      const chapterIndex = Number(action.payload.chapterIndex);
      const sectionIndex = Number(action.payload.sectionIndex);
      if (!state.value.chapters[chapterIndex].sections[sectionIndex].handouts) {
        state.value.chapters[chapterIndex].sections[sectionIndex].handouts = [];
      }
      state.value.chapters[chapterIndex].sections[sectionIndex].handouts.push({handoutUri: action.payload.handoutUri});
    },
    removeHandoutUri: (state, action) => {
      const chapterIndex = Number(action.payload.chapterIndex);
      const sectionIndex = Number(action.payload.sectionIndex);
      const handoutUri = action.payload.handoutUri;
      const oldHandouts = state.value.chapters[chapterIndex].sections[sectionIndex].handouts;
      state.value.chapters[chapterIndex].sections[sectionIndex].handouts = oldHandouts
        .filter(item => item.handoutUri !== handoutUri);
    },
    resetCourse: (state) => {
      state.value = {...initialState};
    }
  }
});

export const { setCourseDetails, addSection, removeSection, addChapter, removeChapter, addAssignment, removeAssignment,
  setName, setPrice, setCategory, setOverview, setRequirement, setPictureUrl, setVideoUri, setChapters,
  setHandoutUri, removeHandoutUri, resetCourse } = createdCourseSlice.actions;

export default createdCourseSlice.reducer;