import { useDispatch, useSelector } from 'react-redux'
import axios from '../api/axios'

// feature slice
import { setReasonReport } from '../features/reasonReportSlice'

// url
import { URL_GET_REASON_REPORTS } from '../utils/url'

const useReasonReport = () => {

    const dispatch = useDispatch()
    const reasons = useSelector(state => state.reasonReport.value.reasonReports)

    const createReasonReportRedux = async () => {
        if (reasons.length === 0) {
            const response = await axios.get(URL_GET_REASON_REPORTS)
                .then(res => res)
                .catch(err => err.response)

            if (response.status === 200) {
                dispatch(
                    setReasonReport({ reasonReports: response.data })
                )
            } else {
                alert('Error get raason report')
            }
        }
    }

    return createReasonReportRedux
}

export default useReasonReport