import { useState } from 'react'

const useAlertMessage = () => {

    const [ valueOpen, setValueOpen ] = useState(false)
    const [ valueType, setValueType ] = useState('info')
    const [ valueMessage, setBalueMessage ] = useState('')

    const setOpen = (value) => {
        try {

            if (typeof value !== 'boolean') {
                throw 'AlertMessage: properties open require boolean type only'
            }
            setValueOpen(value)

        } catch (ex) {
            console.error(ex)
        }
    }

    const setType = (value) => {
        try {

            if (typeof value !== 'string') {
                throw 'AlertMessage: properties type require string type only'
            }

            if (value === 'success' || value === 'info' || value === 'warning' || value === 'error') {
                setValueType(value)
            } else {
                throw 'AlertMessage: properties type value is ( success | info | warning | error ) only'
            }

        } catch (ex) {
            console.error(ex)
        }
    }

    const setMessage = (value) => {
        try {

            if (typeof value !== 'string') {
                throw 'AlertMessage: properties message require string type only'
            }
            setBalueMessage(value)

        } catch (ex) {
            console.error(ex)
        }
    }

    const getOpen = () => valueOpen
    const getType = () => valueType
    const getMessage = () => valueMessage

    const close = (event, reason) => {
        if (reason === 'clickaway') return
        setValueOpen(false)
    }

    return {
        setOpen,
        setType,
        setMessage,
        getOpen,
        getType,
        getMessage,
        close
    }
}

export default useAlertMessage