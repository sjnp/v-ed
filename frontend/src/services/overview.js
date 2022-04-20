import axios from '../api/axios'

// url
import { URL_OVERVIEW_CATEGORY } from "../utils/url";
import { URL_OVERVIEW_COURSE_ID } from '../utils/url';

const getOverviewCategory = async (category) => {
    const url = URL_OVERVIEW_CATEGORY + category
    return axios.get(url).then(res => res.data).catch(err => err.response)
}

const getOverviewCaourse = async (courseId) => {
    const url = URL_OVERVIEW_COURSE_ID + courseId
    return axios.get(url).then(res => res.data).catch(err => err.response)
}

const overviewService = {
    getOverviewCategory,
    getOverviewCaourse
}

export default overviewService