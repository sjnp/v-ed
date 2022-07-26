import React from 'react'
import { useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'

// Material UI component
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import IconButton from '@mui/material/IconButton'

// Material UI icon
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos'

const ChangePassword = () => {

    const navigate = useNavigate()

    const username = useSelector(state => state.auth.value.username)

    const handleChangePassword = () => {
        navigate('/account-manage/profile/verify-password')
    }

    return (
        <Grid container direction='row' alignItems='center' justifyContent='center'>
            <Grid item xs={3}>
                <Typography>Password</Typography>
            </Grid>
            <Grid item xs={3}>
                <Typography noWrap color='gray'>{ '*'.repeat(username?.length) }</Typography>
            </Grid>
            <Grid item xs={6} container alignItems='center' justifyContent='right'>
                <Typography color='gray' mr={1}>Change password</Typography>
                <IconButton color='primary' onClick={handleChangePassword}>
                    <ArrowForwardIosIcon />
                </IconButton>
            </Grid>
        </Grid>
    )
}

export default ChangePassword