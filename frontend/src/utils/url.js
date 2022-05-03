export const URL_LOGIN = '/api/login'
export const URL_REGISTER = '/api/users/register-new-student'
export const URL_CATEGORIES = '/api/categories'
export const URL_CREATE_NEW_COURSE = '/api/instructors/course'
export const URL_GET_CREATED_COURSE = '/api/instructors/incomplete-courses'
export const URL_CREATE_PAR_FOR_READ_WRITE_COURSE_PIC = '/api/instructors/incomplete-courses/picture/pre-authenticated-request'
export const URL_CREATE_PAR_FOR_READ_WRITE_COURSE_VID = '/api/instructors/incomplete-courses/video/pre-authenticated-request'
export const URL_CREATE_PAR_FOR_READ_WRITE_COURSE_HANDOUT = '/api/instructors/incomplete-courses/handout/pre-authenticated-request'
export const URL_INCOMPLETE_COURSE_HANDOUT = '/api/instructors/incomplete-courses/handout'
export const URL_SUBMIT_INCOMPLETE_COURSE = '/api/instructors/incomplete-courses/submission'
export const URL_GET_PENDING_COURSES = '/api/admins/pending-courses'
export const URL_GET_PENDING_COURSE_VIDEO = '/api/admins/pending-courses/video'
export const URL_GET_PENDING_COURSE_HANDOUT = '/api/admins/pending-courses/handout'

export const URL_SAVE_COURSE_PICTURE = '/api/instructors/incomplete-courses/picture'
export const URL_UPDATE_COURSE_MATERIAL = '/api/instructors/incomplete-courses/chapters'
export const URL_DELETE_COURSE_PICTURE = '/api/instructors/incomplete-courses/picture'

// overview
export const URL_OVERVIEW_CATEGORY = '/api/overview/category/'
export const URL_OVERVIEW_MY_COURSE = '/api/overview/my-course'
export const URL_OVERVIEW_COURSE_ID = '/api/overview/course/'
export const URL_OVERVIEW_COURSE_ID_CARD = '/api/overview/course/{courseId}/card'
export const URL_OVERVIEW_VIDEO_EXAMPLE = '/api/overview/video-example/'

// student
export const URL_STUDENT_MY_COURSE = '/api/students/my-course'

// course
export const URL_GET_COURSE_BY_ID = '/api/course/'
export const URL_COURSE_VIDEO = '/api/course/video/'

// assignment
export const URL_ASSIGNMENT_UPLOAD_ANSWER = '/api/assignment/answer/upload'
export const URL_ASSIGNMENT_ANSWER_SAVE = '/api/assignment/answer/save'

// question board
export const URL_QUESTION_BOARD_CREATE = '/api/question-board/create'
export const URL_GET_QUESTION_TOPIC_ALL = '/api/question-board/question/all'

// comment
export const URL_CREATE_COMMENT = '/api/comment/create'