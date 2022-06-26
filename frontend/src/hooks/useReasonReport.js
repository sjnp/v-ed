import { useDispatch } from 'react-redux' 

// feature slice
import { setReasonReport } from '../features/reasonReportSlice'

// custom hook
import useAxiosPrivate from './useAxiosPrivate'

// custom api
import apiPrivate from '../api/apiPrivate'

// url
import { URL_GET_REASON_REPORTS } from '../utils/url'

const useReasonReport = () => {

    const axiosPrivate = useAxiosPrivate()
    const dispatch = useDispatch()

    const createReasonReportRedux = async () => {
        const response = await apiPrivate.get(axiosPrivate, URL_GET_REASON_REPORTS)
        if (response.status === 200) {
            dispatch(
                setReasonReport({ reasonReports: response.data })
            )
        } else {
            alert('Error get raason report')
        }
    }

    return createReasonReportRedux
}

export default useReasonReport