import { URL_CREATE_NEW_COURSE } from '../utils/url'
import useAxiosPrivate from "../hooks/useAxiosPrivate";



export const createNewCourse = async (payload) => {
    const axiosPrivate = useAxiosPrivate();
    return await axiosPrivate.post(URL_CREATE_NEW_COURSE, payload);
}