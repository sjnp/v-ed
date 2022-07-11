// custom hook
import useAxiosPrivate from '../hooks/useAxiosPrivate'

const useApiPrivate = () => {
    
    const axiosPrivate = useAxiosPrivate()

    const getMethod = async (url) => {
        return await axiosPrivate.get(url)
            .then(res => res)
            .catch(err => err.response)
    }

    const postMethod = async (url, payload) => {
        return await axiosPrivate.post(url, payload)
            .then(res => res)
            .catch(err => err.response)
    }

    const putMethod = async (url, payload) => {
        return await axiosPrivate.put(url, payload)
            .then(res => res)
            .catch(err => err.response)
    }

    const deleteMethod = async (url) => {
        return await axiosPrivate.delete(url)
            .then(res => res)
            .catch(err => err.response)
    }

    return {
        get: getMethod,
        post: postMethod,
        put: putMethod,
        delete: deleteMethod
    }
}

export default useApiPrivate