import axios from '../api/axios'
import { URL_REGISTER } from '../utils/url'

export const register = async (payLoad) => {
    return axios.post(URL_REGISTER, payLoad).then(res => res).catch(error => error.response.data)
}