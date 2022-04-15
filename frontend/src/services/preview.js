import axios from '../api/axios'

const getPreviewCategory = async (category) => {
    const url = '/api/preview/' + category
    return axios.get(url).then(res => res.data).catch(err => err.response)
}

const getPreviewMyCourse = async () => {
    const url = '/api/preview/my-course' 
    return axios.get(url).then(res => res.data).catch(err => err.response)
}

const previewService = {
    getPreviewCategory,
    getPreviewMyCourse
}

export default previewService