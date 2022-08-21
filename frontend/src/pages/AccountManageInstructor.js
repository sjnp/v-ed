import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import AccountManageMenu from '../components/AccountManageMenu'
import ActiveInstructor from '../components/ActiveInstructor'
import FinanceInstructor from '../components/FinanceInstructor'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'

const AccountManageInstructor = () => {

    const roles = useSelector(state => state.auth.value.roles)

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <AccountManageMenu active='instructor' />
                </Grid>
                <Grid item xs={9} container>
                    <Grid item xs={1}></Grid>
                    <Grid item xs={10} container>
                    {
                        roles?.includes('INSTRUCTOR') ?
                        <FinanceInstructor />
                        :
                        <ActiveInstructor />
                    }
                    </Grid>
                    <Grid item xs={1}></Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default AccountManageInstructor