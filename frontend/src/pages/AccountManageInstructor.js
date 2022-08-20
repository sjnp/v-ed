import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import AccountManageMenu from '../components/AccountManageMenu'
import LoadingCircle from '../components/LoadingCircle'
import ActiveInstructor from '../components/ActiveInstructor'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'

const AccountManageInstructor = () => {

    const roles = useSelector(state => state.auth.value.roles)

    const [ myRole, setMyRole ] = useState(roles.length === 1 ? roles[0] : roles[1])
    const [ loading, setLoading ] = useState(false)

    useEffect(async () => {
        setTimeout(() => setLoading(false), 2000)
    }, [])

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
                        loading ?
                        <Grid item xs={12}>
                            <LoadingCircle loading={loading} centerY={true} />
                        </Grid>
                        :
                        myRole === 'STUDENT' ?
                        <ActiveInstructor />
                        :
                        <Typography>Instructor already</Typography>
                    }
                    </Grid>
                    <Grid item xs={1}></Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default AccountManageInstructor