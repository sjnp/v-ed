import React from 'react'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import AccountManageMenu from '../components/AccountManageMenu'
import NewPassword from '../components/NewPassword'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Breadcrumbs from '@mui/material/Breadcrumbs'
import Typography from '@mui/material/Typography'

const AccountManageNewPassword = () => {

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <AccountManageMenu active='profile' />
                </Grid>
                <Grid item xs={9} container>
                    <Grid item xs={1}></Grid>
                    <Grid item xs={10} container>
                        <Grid item xs={12}>
                            <Breadcrumbs>
                                <Typography color='text.primary'>New password</Typography>
                            </Breadcrumbs>
                        </Grid>
                        <Grid item xs={12} container mt={7}>
                            <Grid item xs={2}></Grid>
                            <Grid item xs={8}>
                                <NewPassword />
                            </Grid>
                            <Grid item xs={2}></Grid>
                        </Grid>
                    </Grid>
                    <Grid item xs={1}></Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default AccountManageNewPassword