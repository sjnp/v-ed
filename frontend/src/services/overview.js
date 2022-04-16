import axios from '../api/axios'

const getOverviewCaourse = async (courseId) => {
    const url = '/api/overview/' + courseId
    return axios.get(url).then(res => res.data).catch(err => err.response)
}

const overviewService = {
    getOverviewCaourse
}

export default overviewService