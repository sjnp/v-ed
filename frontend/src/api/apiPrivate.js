const apiPrivate = {

    get: async (axiosPrivate, url) => {
        return axiosPrivate.get(url).then(res => res).catch(err => err.response)
    },
    
    post: async (axiosPrivate, url, payLoad = null) => {
        return axiosPrivate.post(url, payLoad).then(res => res).catch(err => err.response)
    },
    
    put: async (axiosPrivate, url, payLoad = null) => {
        return axiosPrivate.put(url, payLoad).then(res => res).catch(err => err.response)
    },
    
    delete: async (axiosPrivate, url) => {
        return axiosPrivate.delete(url).then(res => res).catch(err => err.response)
    },

}

export default apiPrivate