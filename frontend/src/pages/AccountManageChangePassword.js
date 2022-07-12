import React from 'react'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import AccountManageMenu from '../components/AccountManageMenu'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'

const AccountManageChangePassword = () => {
    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <AccountManageMenu active='change password' />
                </Grid>
                <Grid item xs={9}>
                    content account manage change password page
                </Grid>
            </Grid>
        </Container>
    )
}

export default AccountManageChangePassword