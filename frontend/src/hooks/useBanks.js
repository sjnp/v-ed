import React, { useState, useEffect } from 'react'

const useBanks = () => {

    const [ banks, setBanks ] = useState([])

    useEffect(async () => {
        const URL_BANK_DATA = 'https://raw.githubusercontent.com/omise/banks-logo/master/banks.json'
        const response = await fetch(URL_BANK_DATA)

        if (response.status === 200) {
            const bankJson = await response.json()
            const bankNames = ['bbl', 'kbank', 'ktb', 'tmb', 'scb', 'citi', 'cimb', 'uob', 'bay', 'gsb', 'tbank', 'kk' ]

            const BANK_LOGO_URL = 'https://raw.githubusercontent.com/omise/banks-logo/master/th/'
            const result = bankNames.map(name => {
                bankJson.th[name]['name'] = name
                bankJson.th[name]['imageUrl'] = BANK_LOGO_URL + name + '.svg'
                return bankJson.th[name]
            })
            setBanks(result)
        }
    }, [])

    return banks
}

export default useBanks