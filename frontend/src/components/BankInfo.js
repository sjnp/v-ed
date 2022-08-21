import React from 'react'
import { useNavigate } from 'react-router-dom'
import moment from 'moment'

// Material UI component
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Divider from '@mui/material/Divider'
import Button from '@mui/material/Button'

const BankInfo = ({ bankBand, accountName, accountNumber, activateDatetime }) => {

    const navigate = useNavigate()

    const handleClickChangeReceiptAccount = () => {
        // navigate('')
    }

    return (
        <Grid container border='solid 1px #d3d3d3' borderRadius={3} mt={3}>
            <Grid item xs={12} container p={3}>
                <Grid item xs={5}>
                    <Typography>Bank band</Typography>
                </Grid>
                <Grid item xs={7}>
                    <Typography color='gray'>{bankBand}</Typography>
                </Grid>
            </Grid>
            <Grid item xs={12}><Divider /></Grid>
            <Grid item xs={12} container p={3}>
                <Grid item xs={5}>
                    <Typography>Account name</Typography>
                </Grid>
                <Grid item xs={7}>
                    <Typography color='gray'>{accountName}</Typography>
                </Grid>
            </Grid>
            <Grid item xs={12}><Divider /></Grid>
            <Grid item xs={12} container p={3}>
                <Grid item xs={5}>
                    <Typography>Account number</Typography>
                </Grid>
                <Grid item xs={7}>
                    <Typography color='gray'>{accountNumber}</Typography>
                </Grid>
            </Grid>
            <Grid item xs={12}><Divider /></Grid>
            <Grid item xs={12} container p={3}>
                <Grid item xs={5}>
                    <Typography>Activate datetime</Typography>
                </Grid>
                <Grid item xs={7}>
                    <Typography color='gray'>{moment(activateDatetime).format("DD/MM/YYYY | kk:mm:ss")}</Typography>
                </Grid>
            </Grid>
            <Grid item xs={12}><Divider /></Grid>
            <Grid item xs={12} container direction='row' justifyContent='center' p={3}>
                <Button variant='contained' onClick={handleClickChangeReceiptAccount}>
                    Change Receipt Account
                </Button>
            </Grid>
        </Grid>
    )
}

export default BankInfo