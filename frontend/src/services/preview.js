import axios from '../api/axios'

const getPreviewCategory = async () => {

    const url = '/api/preview/category'
    return axios.get(url)
        .then(res => res.data)
        .catch(err => err.reponse)
}

const getPrevieMyCourse = async () => {

    const url = '/api/preview/my-course'
    return axios.get(url)
        .then(res => res.data)
        .catch(err => err.reponse)
}

const previewService = {
    getPreviewCategory,
    getPrevieMyCourse
}

export default previewService