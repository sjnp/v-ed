import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import AccountManageMenu from '../components/AccountManageMenu'
import LoadingCircle from '../components/LoadingCircle'
import BankForm from '../components/BankForm'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Breadcrumbs from '@mui/material/Breadcrumbs'
import Link from '@mui/material/Link'

// Material UI icon
import NavigateNextIcon from '@mui/icons-material/NavigateNext'

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'

// url
import { URL_GET_BANK_ACCOUNT_INSTRUCTOR } from '../utils/url'

const AccountManageInstructorBank = () => {

    const navigate = useNavigate()
    const apiPrivate = useApiPrivate()

    const [ bankBand, setBankBand ] = useState('')
    const [ accountName, setAccountName ] = useState('')
    const [ accountNumber, setAccountNumber ] = useState('')
    const [ loading, setLoading ] = useState(true)

    useEffect(async () => {
        const response = await apiPrivate.get(URL_GET_BANK_ACCOUNT_INSTRUCTOR)
        if (response.status === 200) {
            setBankBand(response.data.bank_code)
            setAccountName(response.data.name)
            setAccountNumber(response.data.account_number)
        } else {
            alert('Fail')
        }
        setLoading(false)
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
                                <Typography color='text.primary'>Bank account</Typography>
                            </Breadcrumbs>
                        </Grid>
                        <Grid item xs={1}></Grid>
                    </Grid>

                    <Grid item xs={12} container>
                        <Grid item xs={2}></Grid>
                        <Grid item xs={8}>
                        {
                            loading ?
                            <LoadingCircle loading={loading} centerY={true} />
                            :
                            <BankForm
                                dataSelect={bankBand}
                                dataFirstname={accountName?.split(' ')[0]}
                                dataLastname={accountName?.split(' ')[1]}
                                dataAccountNumber={accountNumber}
                                type='edit'
                            />
                        }
                        </Grid>
                        <Grid item xs={2}></Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default AccountManageInstructorBank