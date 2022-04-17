import axios from '../api/axios'

// url
import { URL_GET_HOME_PREVIEW_CATEGORY } from '../utils/url'
import { URL_GET_PREVIEW_COURSE_ID } from '../utils/url'

const getPreviewCategory = async (category) => {
    const url = URL_GET_HOME_PREVIEW_CATEGORY + category
    return axios.get(url).then(res => res.data).catch(err => err.response)
}

const getPreviewCourse = async (courseId) => {
    const url = URL_GET_PREVIEW_COURSE_ID + courseId
    return axios.get(url).then(res => res.data).catch(err => err.response)
}

const previewService = {
    getPreviewCategory,
    getPreviewCourse
}

export default previewService