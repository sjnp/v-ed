import { useState } from 'react'

const useAlertMessage = () => {

    const [ open, setOpen ] = useState(false)
    const [ type, setType ] = useState('info')
    const [ message, setMessage ] = useState('')

    const show = (newType, newMessage) => {
        try {

            if (typeof newMessage !== 'string') {
                throw 'AlertMessage: properties message require string type only'
            }
            
            if (typeof newType !== 'string') {
                throw 'AlertMessage: properties type require string type only'
            }

            if (newType === 'success' || newType === 'info' || newType === 'warning' || newType === 'error') {
                setOpen(true)
                setType(newType)
                setMessage(newMessage)
            } else {
                throw 'AlertMessage: properties type value is ( success | info | warning | error ) only'
            }

        } catch (err) {
            console.error(err)
        }
    }

    const close = (event, reason) => {
        if (reason === 'clickaway') return
        setOpen(false)
    }

    const getOpen = () => open
    const getType = () => type
    const getMessage = () => message

    return {
        show,
        close,
        getOpen,
        getType,
        getMessage
    }

}

export default useAlertMessage