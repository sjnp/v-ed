import React, { useEffect } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import AccountManageMenu from '../components/AccountManageMenu'
import BankAddForm from '../components/BankAddForm'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'

const AccountManageInstructorActiveBank = () => {

    const navigate = useNavigate()

    const roles = useSelector(state => state.auth.value.roles)

    useEffect(() => {
        if (roles?.includes('INSTRUCTOR')) {
            navigate('/account-manage/instructor')
        }
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
                        <BankAddForm />
                    </Grid>
                    <Grid item xs={2}></Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default AccountManageInstructorActiveBank