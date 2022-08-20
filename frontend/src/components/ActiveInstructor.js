import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'

// Material UI component
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Checkbox from '@mui/material/Checkbox'
import Button from '@mui/material/Button'

const ActiveInstructor = () => {

    const navigate = useNavigate()

    const [ isAccept, setIsAccept ] = useState(false)

    const handleChangeCheckbox = (event) => {
        setIsAccept(event.target.checked)
    }

    const handleClickActiveToInstructor = () => {
        navigate('/account-manage/instructor/active')
    }

    return (
        <Grid container border='solid 1px #d3d3d3' borderRadius={3} mt={3} p={2}>
            <Grid item xs={12} p={2} bgcolor='#f3f3f3' borderRadius={2}>
                <Typography>Detail condition</Typography>
            </Grid>
            <Grid item xs={12} container direction='row' alignItems='center' pt={1}>
                <Grid item>
                    <Checkbox checked={isAccept} onChange={handleChangeCheckbox} />
                </Grid>
                <Grid item>
                    <Typography>I accept this Detail condition</Typography>
                </Grid>
            </Grid>
            <Grid item xs={12} container direction='row' justifyContent='center' pt={2}>
                <Button variant='contained' onClick={handleClickActiveToInstructor} disabled={!isAccept}>
                    Active to instructor
                </Button>
            </Grid>
        </Grid>
    )
}

export default ActiveInstructor