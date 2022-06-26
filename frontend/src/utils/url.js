export const URL_LOGIN = '/api/login'
export const URL_REGISTER = '/api/users/new-student'
export const URL_CATEGORIES = '/api/categories'

// instructor
export const URL_GET_ALL_INSTRUCTOR_PENDING_COURSES = '/api/instructors/pending-courses'
export const URL_GET_ALL_INSTRUCTOR_APPROVED_COURSES = '/api/instructors/approved-courses'
export const URL_GET_ALL_INSTRUCTOR_REJECTED_COURSES = '/api/instructors/rejected-courses'
export const URL_GET_ALL_INSTRUCTOR_PUBLISHED_COURSES = '/api/instructors/published-courses'
export const URL_CREATE_NEW_COURSE = '/api/instructors/course'
export const URL_GET_INCOMPLETE_COURSE = '/api/instructors/incomplete-courses/{courseId}'
export const URL_GET_ALL_INCOMPLETE_COURSES = '/api/instructors/incomplete-courses'
export const URL_CREATE_PAR_FOR_READ_WRITE_COURSE_PIC = '/api/instructors/incomplete-courses/{courseId}/picture/pre-authenticated-request'
export const URL_CREATE_PAR_FOR_READ_WRITE_COURSE_VID = '/api/instructors/incomplete-courses/{courseId}/video/pre-authenticated-request'
export const URL_CREATE_PAR_FOR_READ_WRITE_COURSE_HANDOUT = '/api/instructors/incomplete-courses/{courseId}/handout/pre-authenticated-request'
export const URL_DELETE_INCOMPLETE_COURSE_HANDOUT = '/api/instructors/incomplete-courses/{courseId}/chapter/{chapterIndex}/section/{sectionIndex}/handout/{handoutUri}'
export const URL_SAVE_COURSE_PICTURE = '/api/instructors/incomplete-courses/{courseId}/picture/{pictureName}'
export const URL_UPDATE_COURSE_MATERIAL = '/api/instructors/incomplete-courses/{courseId}/chapters'
export const URL_SUBMIT_INCOMPLETE_COURSE = '/api/instructors/incomplete-courses/{courseId}/state'
export const URL_PUBLISH_INSTRUCTOR_COURSE = '/api/instructors/approved-courses/{courseId}'
export const URL_DELETE_COURSE_PICTURE = '/api/instructors/incomplete-courses/{courseId}/picture'

// admin
export const URL_GET_PENDING_COURSE = '/api/admins/pending-courses/{courseId}'
export const URL_PUT_PENDING_COURSE = '/api/admins/pending-courses/{courseId}'
export const URL_GET_ALL_PENDING_COURSES = '/api/admins/pending-courses'
export const URL_GET_PENDING_COURSE_VIDEO = '/api/admins/pending-courses/{courseId}/chapter/{chapterIndex}/section/{sectionIndex}/video'
export const URL_GET_PENDING_COURSE_HANDOUT = '/api/admins/pending-courses/{courseId}/chapter/{chapterIndex}/section/{sectionIndex}/handout/{handoutUri}'

// overview
export const URL_GET_OVERVIEWS_FROM_CATEGORY = '/api/overviews/category/{name}'
export const URL_GET_OVERVIEW_COURSE = '/api/overviews/courses/{courseId}'
export const URL_GET_OVERVIEW_COURSE_CARD = '/api/overviews/courses/{courseId}/card'
export const URL_GET_VIDEO_EXAMPLE = '/api/overviews/video-example/courses/{courseId}'

// student
export const URL_GET_COURSE_SAMPLES = '/api/students/course-samples'
export const URL_FREE_COURSE = '/api/students/free/course'
export const URL_BUY_COURSE = '/api/students/buy/course'
export const URL_GET_STUDENT_COURSES = '/api/students/courses'
export const URL_GET_COURSE = '/api/students/courses/{courseId}'
export const URL_GET_VIDEO = '/api/students/courses/{courseId}/chapter/{chapterIndex}/section/{sectionIndex}/video'
export const URL_GET_HANDOUT = '/api/students/courses/{courseId}/chapter/{chapterIndex}/section/{sectionIndex}/handout/{handoutIndex}'

export const URL_GET_UPLOAD_ANSWER_URL = '/api/students/courses/{courseId}/chapter/{chapterIndex}/no/{noIndex}/answer/{fileName}'
export const URL_CREATE_ANSWER = '/api/students/courses/{courseId}/answer'
export const URL_GET_ASSIGNMENT_ANSWER = '/api/students/courses/{courseId}/chapter/{chapterIndex}/answer'

export const URL_GET_ALL_POSTS_BY_COURSE = '/api/students/courses/{courseId}/posts'
export const URL_CREATE_POST = '/api/students/courses/post'
export const URL_GET_POST = '/api/students/courses/{courseId}/posts/{postId}'
export const URL_CREATE_COMMENT = '/api/students/courses/{courseId}/posts/{postId}/comment'

export const URL_CREATE_REVIEW = '/api/students/courses/review'
export const URL_GET_REVIEWS_BY_COURSE = '/api/students/courses/{courseId}/reviews'
export const URL_GET_REVIEW = '/api/students/courses/{courseId}/reviews/{reviewId}'
export const URL_EDIT_REVIEW = '/api/students/courses/reviews/{reviewId}'

export const URL_GET_ABOUT_COURSE = '/api/students/courses/{courseId}/about'

export const URL_GET_REASON_REPORTS = '/api/students/reason-reports'

// student - incomplete
export const URL_CREATE_PAR_FOR_READ_WRITE_COURSE_ANSWER = '/api/students/courses/answers/pre-authenticated-request'
// export const URL_SAVE_ANSWER = '/api/students/courses/{courseId}/answer'
export const URL_DELETE_ANSWER = '/api/students/courses/{courseId}/answer/{answerId}'

export const URL_GET_COURSE_BY_ID = ''

// assignment

// question board

export const URL_GET_QUESTION_TOPIC_ALL = '/api/question-board/question/all'