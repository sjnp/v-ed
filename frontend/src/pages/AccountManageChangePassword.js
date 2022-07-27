import React from 'react'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import AccountManageMenu from '../components/AccountManageMenu'
import ChangePassword from '../components/ChangePassword'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Breadcrumbs from '@mui/material/Breadcrumbs'
import Typography from '@mui/material/Typography'

const AccountManageChangePassword = () => {
    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <AccountManageMenu active='change password' />
                </Grid>
                <Grid item xs={9} container>
                    <Grid item xs={1}></Grid>
                    <Grid item xs={10} container>
                        <Grid item xs={12}>
                            <Breadcrumbs>
                                <Typography color='text.primary'>Change password</Typography>
                            </Breadcrumbs>
                        </Grid>
                        <Grid item xs={12} container mt={3}>
                            <Grid item xs={1}></Grid>
                            <Grid item xs={10}>
                                <ChangePassword />
                            </Grid>
                            <Grid item xs={1}></Grid>
                        </Grid>
                    </Grid>
                    <Grid item xs={1}></Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default AccountManageChangePassword