import axios from '../api/axios'

// url
import { URL_GET_OVERVIEW_COURSE_ID } from '../utils/url'

const getOverviewCaourse = async (courseId) => {
    const url = URL_GET_OVERVIEW_COURSE_ID + courseId
    return axios.get(url).then(res => res.data).catch(err => err.response)
}

const overviewService = {
    getOverviewCaourse
}

export default overviewService