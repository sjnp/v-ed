

const deleteCourseHandout = async (axiosPrivate, url, courseId, handoutObjectName) => {
  const response = await axiosPrivate.delete(`${url}?id=${courseId}&objectName=${handoutObjectName}`);
  return response.data;
}


export const deleteUtility = {
  deleteCourseHandout
}