import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import moment from 'moment'

// component
import LoadingCircle from './LoadingCircle'

// Material UI component
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import IconButton from '@mui/material/IconButton'

// Material UI icon
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos'
import EditIcon from '@mui/icons-material/Edit'

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'

// url
import { URL_GET_BANK_ACCOUNT_INSTRUCTOR } from '../utils/url'

const FinanceInstructor = () => {

    const navigate = useNavigate()
    const apiPrivate = useApiPrivate()

    const [ bankBand, setBankBand ] = useState('')
    const [ accountName, setAccountName ] = useState('')
    const [ accountNumber, setAccountNumber ] = useState('')
    const [ activateDatetime, setActivateDatetime ] = useState('')
    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {
        const response = await apiPrivate.get(URL_GET_BANK_ACCOUNT_INSTRUCTOR)
        if (response.status === 200) {
            setBankBand(response.data.brand)
            setAccountName(response.data.name)
            setAccountNumber(`${'*'.repeat(12)}${response.data.last_digits}`  )
            setActivateDatetime(response.data.created_at)
        } else {
            alert('Fail')
        }
        setLoading(false)
    }, [])

    const handleClickEditBank = () => {
        navigate('/account-manage/instructor/bank')
    }

    const handleClickTransaction = () => {
        navigate('/account-manage/instructor/transaction')
    }

    return (
        <React.Fragment>
        {
            loading ?
            <LoadingCircle loading={loading} centerY={true} />
            :
            <Grid container mt={5}>
                <Grid item xs={12} container border='solid 1px #d3d3d3' borderRadius={3} pl={3} pb={3}>
                    <Grid item xs={11} pt={3}>
                        <Typography variant='h6'>Bank information</Typography>
                    </Grid>
                    <Grid item xs={1} pt={3}>
                        <IconButton color='primary' onClick={handleClickEditBank} title='Change receipt account'>
                            <EditIcon />
                        </IconButton>
                    </Grid>
                    <Grid item xs={5} pt={2}>
                        <Typography>Bank band</Typography>
                    </Grid>
                    <Grid item xs={7} pt={2}>
                        <Typography color='gray'>{bankBand}</Typography>
                    </Grid>
                    <Grid item xs={5} pt={2}>
                        <Typography>Account name</Typography>
                    </Grid>
                    <Grid item xs={7} pt={2}>
                        <Typography color='gray'>{accountName}</Typography>
                    </Grid>
                    <Grid item xs={5} pt={2}>
                        <Typography>Account number</Typography>
                    </Grid>
                    <Grid item xs={7} pt={2}>
                        <Typography color='gray'>{accountNumber}</Typography>
                    </Grid>
                    <Grid item xs={5} pt={2}>
                        <Typography>Activate datetime</Typography>
                    </Grid>
                    <Grid item xs={7} pt={2}>
                        <Typography color='gray'>
                            {moment(activateDatetime).format("DD/MM/YYYY | kk:mm:ss")}
                        </Typography>
                    </Grid>
                </Grid>
                <Grid item xs={12} container border='solid 1px #d3d3d3' borderRadius={3} mt={3} pt={3} pb={3}>
                    <Grid item container direction='row' alignItems='center' justifyContent='center' pl={3} pr={1}>
                        <Grid item xs={4}>
                            Transaction
                        </Grid>
                        <Grid item xs={7} container direction='row' justifyContent='right'>
                            <Typography color='gray'>View transaction</Typography>
                        </Grid>
                        <Grid item xs={1} container direction='row' justifyContent='right'>
                            <IconButton color='primary' size='small' onClick={handleClickTransaction}>
                                <ArrowForwardIosIcon />
                            </IconButton>
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        }
        </React.Fragment>
    )
}

export default FinanceInstructor