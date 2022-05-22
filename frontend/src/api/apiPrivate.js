const apiPrivate = {

    get: async (axiosPrivate, url) => {
        return axiosPrivate.get(url).then(res => res).catch(err => err.response.data)
    },
    
    post: async (axiosPrivate, url, payLoad = null) => {
        return axiosPrivate.post(url, payLoad).then(res => res).catch(err => err.response.data)
    },
    
    put: async (axiosPrivate, url, payLoad = null) => {
        return axiosPrivate.put(url, payLoad).then(res => res).catch(err => err.response.data)
    },
    
    delete: async (axiosPrivate, url) => {
        return axiosPrivate.delete(url).then(res => res).catch(err => err.response.data)
    },

}

export default apiPrivate