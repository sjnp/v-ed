import React from 'react'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import AccountManageMenu from '../components/AccountManageMenu'
import BankInfo from '../components/BankInfo'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'

const AccountManageBankInfo = () => {

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
                        <BankInfo
                            bankBand={'SCB'}
                            accountName={'firstname lastname'}
                            accountNumber={'123***'}
                            activateDatetime={'date time'}
                        />
                    </Grid>
                    <Grid item xs={2}></Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default AccountManageBankInfo