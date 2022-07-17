import React from 'react'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import AccountManageMenu from '../components/AccountManageMenu'
import DisplayEditor from '../components/DisplayEditor'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Breadcrumbs from '@mui/material/Breadcrumbs'
import Typography from '@mui/material/Typography'
import Divider from '@mui/material/Divider'

const AccountManageProfile = () => {

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
                                <Typography color='text.primary'>Profile</Typography>
                            </Breadcrumbs>
                        </Grid>
                        <Grid item xs={12} container border='solid 1px #d3d3d3' borderRadius={3} mt={3}>
                            <Grid item xs={12} pl={3} pr={3} pt={2} pb={2}>
                                <DisplayEditor />
                            </Grid>
                            <Grid item xs={12}><Divider /></Grid>
                            <Grid item xs={12}>
                                Name editor
                            </Grid>
                            <Grid item xs={12}><Divider /></Grid>
                            <Grid item xs={12}>
                                Biography editor
                            </Grid>
                            <Grid item xs={12}><Divider /></Grid>
                            <Grid item xs={12}>
                                Occupation editor
                            </Grid>
                        </Grid>
                    </Grid>
                    <Grid item xs={1}></Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default AccountManageProfile