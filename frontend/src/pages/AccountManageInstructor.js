import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import AccountManageMenu from '../components/AccountManageMenu'
// <<<<<<< HEAD
import ActiveInstructor from '../components/ActiveInstructor'
import FinanceInstructor from '../components/FinanceInstructor'
// =======
// import useAxiosPrivate from "../hooks/useAxiosPrivate";
// >>>>>>> 7019f403b0a1614782242991ce3df38224898c9f

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
// <<<<<<< HEAD
import Breadcrumbs from '@mui/material/Breadcrumbs'
import Typography from '@mui/material/Typography'

const AccountManageInstructor = () => {

    const roles = useSelector(state => state.auth.value.roles)
// =======
// import { Button } from '@mui/material'

// const AccountManageInstructor = () => {

    // const axiosPrivate = useAxiosPrivate();

    // const HandleTestFunction = async () => {
    //     try {
    //         const response = await axiosPrivate.get('/api/instructors/finance/getTransaction' );
    //         console.log(response);
    //     }
    //     catch(error) {
    //         console.log(error);
    //     }
    // }

// >>>>>>> 7019f403b0a1614782242991ce3df38224898c9f

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <AccountManageMenu active='instructor' />
                </Grid>
{/* <<<<<<< HEAD */}
                <Grid item xs={9} container>
                    <Grid item xs={1}></Grid>
                    <Grid item xs={10} container>
                        <Grid item xs={12}>
                            <Breadcrumbs>
                                <Typography color='text.primary'>Instructor</Typography>
                            </Breadcrumbs>
                        </Grid>
                        <Grid item xs={12} container>
                            <Grid item xs={1}></Grid>
                            <Grid item xs={10}>
                            {
                                roles?.includes('INSTRUCTOR') ?
                                <Grid item xs={12}>
                                    <FinanceInstructor />
                                </Grid>
                                :
                                <Grid item xs={12}>
                                    <ActiveInstructor />
                                </Grid>
                            }
                            </Grid>
                            <Grid item xs={1}></Grid>
                        </Grid>
                    </Grid>
                    <Grid item xs={1}></Grid>
{/* ======= */}
                {/* <Grid item xs={9}> */}
                    {/* content account manage instructor page */}
                    
                    {/* <Button onClick={HandleTestFunction}>Test Get Transaction</Button> */}
{/* >>>>>>> 7019f403b0a1614782242991ce3df38224898c9f */}
                </Grid>
                
            </Grid>
        </Container>
    )
}

export default AccountManageInstructor