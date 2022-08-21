import React, { useState } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'

// component
import SelectBank from './SelectBank'

// Material UI component
import Grid from '@mui/material/Grid'
import TextField from '@mui/material/TextField'
import Typography from '@mui/material/Typography'
import LoadingButton from '@mui/lab/LoadingButton'

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'

const BankForm = () => {

    const apiPrivate = useApiPrivate()
    const navigate = useNavigate()

    const username = useSelector(state => state.auth.value.username)

    // select bank account    
    const [ accountSelect, setAccountSelect ] = useState('')
    const [ errorAccountSelect, setErrorAccountSelect ] = useState(false)
    const [ messageAccountSelect, setMessageAccountSelect ] = useState('')
    // account firstname
    const [ accountFirstname, setAccountFirstname ] = useState('')
    const [ errorAccountFirstname, setErrorAccountFirstname ] = useState(false)
    const [ messageAccountFirstname, setMessageAccountFirstname ] = useState('')
    // account lastname
    const [ accountLastname, setAccountLastname ] = useState('')
    const [ errorAccountLastname, setErrorAccountLastname ] = useState(false)
    const [ messageAccountLastname, setMessageAccountLastname ] = useState('')
    // account number
    const [ accountNumber, setAccountNumber ] = useState('')
    const [ errorAccountNumber, setErrorAccountNumber ] = useState(false)
    const [ messageAccountNumber, setMessageAccountNumber ] = useState('')
    // saving state
    const [ saving, setSaving ] = useState(false)
    
    const handleSelectBank = (event) => {
        setAccountSelect(event.target.value)
        setErrorAccountSelect(false)
        setMessageAccountSelect('')
    }

    const handleChangeAccountFirstname = (event) => {
        const reNameEnglishOnly = /^[A-Za-z]*$/
        if (reNameEnglishOnly.test(event.target.value.trim())) {
            setAccountFirstname(event.target.value.trim())
            setErrorAccountFirstname(false)
            setMessageAccountFirstname('')
        } else {
            setErrorAccountFirstname(true)
            setMessageAccountFirstname('Account firstname must English only')
        }
    }

    const handleChangeAccountLastname = (event) => {
        const reNameEnglishOnly = /^[A-Za-z]*$/
        if (reNameEnglishOnly.test(event.target.value.trim())) {
            setAccountLastname(event.target.value.trim())
            setErrorAccountLastname(false)
            setMessageAccountLastname('')
        } else {
            setErrorAccountLastname(true)
            setMessageAccountLastname('Account lastname must English only')
        }
    }

    const handleChangeAccountNumber = (event) => {
        const reNumber = /^[0-9]*$/
        if (reNumber.test(event.target.value)) {

            if (event.target.value.length <= 16) {
                setAccountNumber(event.target.value)
                setErrorAccountNumber(false)
                setMessageAccountNumber('')
            }
            
        } else {
            setErrorAccountNumber(true)
            setMessageAccountNumber('Account number must numeric only')
        }
    }

    const handleClickVerify = async () => {
        let isInvalid = false

        if (accountSelect === '') {
            setErrorAccountSelect(true)
            setMessageAccountSelect('Bank band is required')
            isInvalid = true
        }
        
        if (accountFirstname.trim() === '') {
            setErrorAccountFirstname(true)
            setMessageAccountFirstname('Account firstname is required')
            isInvalid = true
        } 

        if (accountLastname.trim() === '') {
            setErrorAccountLastname(true)
            setMessageAccountLastname('Account lastname is required')
            isInvalid = true
        }

        if (accountNumber.trim().length === 0) {
            setErrorAccountNumber(true)
            setMessageAccountNumber('Account number is required')
            isInvalid = true
        } else if (accountNumber.trim().length !== 16) {
            setErrorAccountNumber(true)
            setMessageAccountNumber('Account number must 16 character only')
            isInvalid = true
        }

        if (isInvalid) return

        setSaving(true)
        const url = '/api/students/active-instrustor'
        const payload = {
            bankBrand: accountSelect,
            bankAccountName: `${accountFirstname} ${accountLastname}`,
            bankAccountNumber: accountNumber,
            recipientName: username,
            type: 'individual'
        }
        const response = await apiPrivate.post(url, payload)
        if (response.status === 201) {
            // alert('success')
            navigate(`/account-manage/instructor/bank`)
        } else {
            // alert('Fail')
        }
    }

    return (
        <Grid container border='solid 1px #d3d3d3' borderRadius={3} mt={3} p={2} pb={4} spacing={2}>
            <Grid item xs={12}>
                <Typography variant='h6'>
                    Add bank account
                </Typography>
            </Grid>
            <Grid item xs={12}>
                <SelectBank
                    select={accountSelect} 
                    onChange={handleSelectBank} 
                    error={errorAccountSelect}
                    onSaving={saving}
                />
                <Typography variant='caption' color='red' pl={2}>{messageAccountSelect}</Typography>
            </Grid>
            <Grid item xs={6}>
                <TextField
                    id='account_firstname'
                    label='Account firstname'
                    size='small'
                    required
                    fullWidth
                    disabled={saving}
                    value={accountFirstname}
                    onChange={handleChangeAccountFirstname}
                    error={errorAccountFirstname}
                    helperText={messageAccountFirstname}
                />
            </Grid>
            <Grid item xs={6}>
                <TextField
                    id='account_lastname'
                    label='Account lastname'
                    size='small'
                    required
                    fullWidth
                    disabled={saving}
                    value={accountLastname}
                    onChange={handleChangeAccountLastname}
                    error={errorAccountLastname}
                    helperText={messageAccountLastname}
                />
            </Grid>
            <Grid item xs={12}>
                <TextField
                    id='account_number'
                    label='Account number'
                    size='small'
                    required
                    fullWidth
                    disabled={saving}
                    value={accountNumber}
                    onChange={handleChangeAccountNumber}
                    error={errorAccountNumber}
                    helperText={messageAccountNumber}
                />
            </Grid>
            <Grid item xs={12} container direction='row' alignItems='center' justifyContent='center' mt={2}>
                <Grid item>
                    <LoadingButton variant='contained' onClick={handleClickVerify} loading={saving}>
                        Verify and Activate Instructor
                    </LoadingButton>
                </Grid>
            </Grid>
        </Grid>
    )
}

export default BankForm