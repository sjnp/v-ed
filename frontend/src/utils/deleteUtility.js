

const deleteCourseHandout = async (axiosPrivate, url, courseId, chapterIndex, sectionIndex, handoutObjectName) => {
  const deleteUrl = url
    .replace('{courseId}', courseId)
    .replace('{chapterIndex}', chapterIndex)
    .replace('{sectionIndex}', sectionIndex)
    .replace('{handoutUri}', handoutObjectName)

  const response = await axiosPrivate.delete(deleteUrl);
  return response.data;
}


export const deleteUtility = {
  deleteCourseHandout
}