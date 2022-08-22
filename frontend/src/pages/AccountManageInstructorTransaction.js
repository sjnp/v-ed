import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import AccountManageMenu from '../components/AccountManageMenu'
import ActiveInstructor from '../components/ActiveInstructor'
import FinanceInstructor from '../components/FinanceInstructor'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Breadcrumbs from '@mui/material/Breadcrumbs'
import Typography from '@mui/material/Typography'
import Link from '@mui/material/Link'

// Material UI icon
import NavigateNextIcon from '@mui/icons-material/NavigateNext'

const AccountManageInstructorTransaction = () => {

    const navigate = useNavigate()

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
                        <Grid item xs={12}>
                            <Breadcrumbs separator={<NavigateNextIcon fontSize="small" />}>
                                <Link
                                    underline='hover' 
                                    color='default' 
                                    sx={{ cursor: 'pointer' }} 
                                    onClick={() => navigate(`/account-manage/instructor`)}
                                >
                                    Instructor
                                </Link>
                                <Typography color='text.primary'>Transaction</Typography>
                            </Breadcrumbs>
                        </Grid>
                        <Grid item xs={12} container pt={3}>
                            TODO component
                        </Grid>
                    </Grid>
                    <Grid item xs={1}></Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default AccountManageInstructorTransaction