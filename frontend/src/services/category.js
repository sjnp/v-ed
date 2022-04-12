import axios from '../api/axios'
import { URL_CATEGORIES } from '../utils/url'

export const getAllCategories = async () => {
    return await axios.get(URL_CATEGORIES);
}