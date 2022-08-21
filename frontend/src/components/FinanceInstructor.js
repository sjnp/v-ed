import React from 'react'
import { useNavigate } from 'react-router-dom'

// Material UI component
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import IconButton from '@mui/material/IconButton'
import Divider from '@mui/material/Divider'

// Material UI icon
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos'

const FinanceInstructor = () => {

    const navigate = useNavigate()

    const handleClickBankInfo = () => {
        navigate('/account-manage/instructor/bank')
    }

    const handleClickTransaction = () => {
        navigate('/account-manage/instructor/transaction')
    }

    return (
        <Grid container border='solid 1px #d3d3d3' borderRadius={3} mt={3}>
            <Grid item xs={12} container direction='row' alignItems='center'>
                <Grid item xs={3} p={3}>
                    <Typography>Bank info</Typography>
                </Grid>
                <Grid item xs={2} p={3}>
                    <Typography color='gray'>Logo</Typography>
                </Grid>
                <Grid item xs={4} p={3}>
                    <Typography color='gray'>Account name</Typography>
                </Grid>
                <Grid item xs={2} container justifyContent='right' p={3}>
                    <Typography color='gray'>Edit</Typography>
                </Grid>
                <Grid item xs={1} container justifyContent='right' p={3}>
                    <IconButton size='small' color='primary' onClick={handleClickBankInfo}>
                        <ArrowForwardIosIcon />
                    </IconButton>
                </Grid>
            </Grid>
            <Grid item xs={12}><Divider /></Grid>
            <Grid item xs={12} container direction='row' alignItems='center'>
                <Grid item xs={6} p={3}>
                    <Typography>Transaction</Typography>
                </Grid>
                <Grid item xs={5} container justifyContent='right' p={3}>
                    <Typography color='gray'>View transaction</Typography>
                </Grid>
                <Grid item xs={1} container justifyContent='right' p={3}>
                    <IconButton size='small' color='primary' onClick={handleClickTransaction}>
                        <ArrowForwardIosIcon />
                    </IconButton>
                </Grid>
            </Grid>
        </Grid>
    )
}

export default FinanceInstructor