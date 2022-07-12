import React from 'react'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import AccountManageMenu from '../components/AccountManageMenu'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'

const AccountManageProfile = () => {

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <AccountManageMenu active='profile' />
                </Grid>
                <Grid item xs={9}>
                    content account manage profile page
                </Grid>
            </Grid>
        </Container>
    )
}

export default AccountManageProfile