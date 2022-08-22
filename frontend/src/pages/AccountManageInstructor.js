import React from 'react'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import AccountManageMenu from '../components/AccountManageMenu'
import useAxiosPrivate from "../hooks/useAxiosPrivate";

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import { Button } from '@mui/material'

const AccountManageInstructor = () => {

    const axiosPrivate = useAxiosPrivate();

    const HandleTestFunction = async () => {
        try {
            const response = await axiosPrivate.get('/api/instructors/finance/getTransaction' );
            console.log(response);
        }
        catch(error) {
            console.log(error);
        }
    }


    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <AccountManageMenu active='instructor' />
                </Grid>
                <Grid item xs={9}>
                    content account manage instructor page
                    
                    <Button onClick={HandleTestFunction}>Test Get Transaction</Button>
                </Grid>
                
            </Grid>
        </Container>
    )
}

export default AccountManageInstructor