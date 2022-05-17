import axios from '../api/axios'

// url
import { URL_GET_OVERVIEWS_FROM_CATEGORY } from "../utils/url";
import { URL_GET_OVERVIEW_COURSE } from '../utils/url';
import { URL_GET_VIDEO_EXAMPLE } from '../utils/url';

const getOverviewCategory = async (category) => {
    const url = URL_GET_OVERVIEWS_FROM_CATEGORY.replace('{name}', category)
    return axios.get(url).then(res => res.data).catch(err => err.response)
}

const getOverviewCourse = async (courseId) => {
    const url = URL_GET_OVERVIEW_COURSE.replace('{courseId}', courseId)
    return axios.get(url).then(res => res).catch(err => err.response)
}

const getExampleVideoCourse = async (courseId) => {
    const url = URL_GET_VIDEO_EXAMPLE.replace('{courseId}', courseId)
    return axios.get(url).then(res => res).catch(err => err.response)
}

const overviewService = {
    getOverviewCategory,
    getOverviewCourse,
    getExampleVideoCourse
}

export default overviewService