import React, { useState, useEffect } from 'react'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'
import AccountManageMenu from '../components/AccountManageMenu'
import DisplayEditor from '../components/DisplayEditor'
import NameEditor from '../components/NameEditor'
import BiographyEditor from '../components/BiographyEditor'
import OccupationEditor from '../components/OccupationEditor'
import LoadingCircle from '../components/LoadingCircle'

// Material UI component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Breadcrumbs from '@mui/material/Breadcrumbs'
import Typography from '@mui/material/Typography'
import Divider from '@mui/material/Divider'

// custom hook
import useApiPrivate from '../hooks/useApiPrivate'

// url
import { URL_GET_PROFILE } from '../utils/url'

const AccountManageProfile = () => {

    const apiPrivate = useApiPrivate()

    const [ loading, setLoading ] = useState(true)
    const [ displayUrl, setDisplayUrl ] = useState('')
    const [ firstname, setFirstname ] = useState('')
    const [ lastname, setLastname ] = useState('')
    const [ biography, setBiography ] = useState('')
    const [ occupation, setOccupation ] = useState('')

    useEffect(async () => {
        const response = await apiPrivate.get(URL_GET_PROFILE)
        if (response.status === 200) {
            setDisplayUrl(response.data.displayUrl)
            setFirstname(response.data.firstname)
            setLastname(response.data.lastname)
            setBiography(response.data.biography)
            setOccupation(response.data.occupation)
        }
        setLoading(false)
    }, [])

    return (
        <Container>
            <AppBarSearchHeader />
            <Grid container mt={3} mb={5}>
                <Grid item xs={3}>
                    <AccountManageMenu active='profile' />
                </Grid>
                <Grid item xs={9} container>
                    <Grid item xs={1}></Grid>
                    <Grid item xs={10} container>
                        <Grid item xs={12}>
                            <Breadcrumbs>
                                <Typography color='text.primary'>Profile</Typography>
                            </Breadcrumbs>
                        </Grid>
                        {
                            loading ?
                            <Grid item xs={12}>
                                <LoadingCircle loading={loading} centerY={true} />
                            </Grid>
                            :
                            <Grid item xs={12} container border='solid 1px #d3d3d3' borderRadius={3} mt={3}>
                                <Grid item xs={12} pl={3} pr={3} pt={2} pb={2}>
                                    <DisplayEditor
                                        defaultDisplayUrl={displayUrl}
                                    />
                                </Grid>
                                <Grid item xs={12}><Divider /></Grid>
                                <Grid item xs={12} pl={3} pr={3} pt={2} pb={2}>
                                    <NameEditor
                                        defaultFirstname={firstname}
                                        defaultLastname={lastname}
                                    />
                                </Grid>
                                <Grid item xs={12}><Divider /></Grid>
                                <Grid item xs={12} pl={3} pr={3} pt={2} pb={2}>
                                    <BiographyEditor
                                        defaultBiography={biography}
                                    />
                                </Grid>
                                <Grid item xs={12}><Divider /></Grid>
                                <Grid item xs={12} pl={3} pr={3} pt={2} pb={2}>
                                    <OccupationEditor
                                        defaultOccupation={occupation}
                                    />
                                </Grid>
                            </Grid>
                        }
                    </Grid>
                    <Grid item xs={1}></Grid>
                </Grid>
            </Grid>
        </Container>
    )
}

export default AccountManageProfile