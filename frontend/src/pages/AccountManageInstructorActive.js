import React, { useEffect } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import AccountManageMenu from '../components/AccountManageMenu'
import BankForm from '../components/BankForm'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Breadcrumbs from '@mui/material/Breadcrumbs'
import Link from '@mui/material/Link'

// Material UI icon
import NavigateNextIcon from '@mui/icons-material/NavigateNext'

const AccountManageInstructorActive = () => {

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

                    <Grid item xs={12} container>
                        <Grid item xs={1}></Grid>
                        <Grid item xs={10}>
                            <Breadcrumbs separator={<NavigateNextIcon fontSize="small" />} >
                                <Link
                                    underline='hover' 
                                    color='default' 
                                    sx={{ cursor: 'pointer' }} 
                                    onClick={() => navigate(`/account-manage/instructor`)}
                                >
                                    Instructor
                                </Link>
                                <Typography color='text.primary'>Active bank</Typography>
                            </Breadcrumbs>
                        </Grid>
                        <Grid item xs={1}></Grid>
                    </Grid>

                    <Grid item xs={12} container>
                        <Grid item xs={2}></Grid>
                        <Grid item xs={8}>
                            <BankForm />
                        </Grid>
                        <Grid item xs={2}></Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default AccountManageInstructorActive