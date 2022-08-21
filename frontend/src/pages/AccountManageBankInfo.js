import React, { useEffect, useState } from 'react'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import AccountManageMenu from '../components/AccountManageMenu'
import BankInfo from '../components/BankInfo'
import LoadingCircle from '../components/LoadingCircle'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'

const AccountManageBankInfo = () => {

    const apiPrivate = useApiPrivate()

    const [ bankBand, setBankBand ] = useState('')
    const [ accountName, setAccountName ] = useState('')
    const [ accountNumber, setAccountNumber ] = useState('')
    const [ activateDatetime, setActivateDatetime ] = useState('')
    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {
        const url = '/api/instructors/finance/getAccount'
        const response = await apiPrivate.get(url)
        if (response.status === 200) {
            setBankBand(response.data.brand)
            setAccountName(response.data.name)
            setAccountNumber(`${'*'.repeat(12)}${response.data.last_digits}`  )
            setActivateDatetime(response.data.created_at)
        } else {
            alert('Fail')
        }
        setLoading(false)
    }, [])

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <AccountManageMenu active='instructor' />
                </Grid>
                <Grid item xs={9} container>
                    <Grid item xs={2}></Grid>
                    <Grid item xs={8} container>
                    {
                        loading ?
                        <LoadingCircle loading={loading} centerY={true} />
                        :
                        <BankInfo
                            bankBand={bankBand}
                            accountName={accountName}
                            accountNumber={accountNumber}
                            activateDatetime={activateDatetime}
                        />
                    }
                    </Grid>
                    <Grid item xs={2}></Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default AccountManageBankInfo