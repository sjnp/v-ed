import React from 'react'
import { useNavigate } from 'react-router-dom'

// Material Ui component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Box from '@mui/material/Box'
import Stack from '@mui/material/Stack'
import Typography from '@mui/material/Typography'
import Button from '@mui/material/Button'
import Alert from '@mui/material/Alert'
import AlertTitle from '@mui/material/AlertTitle'

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'


const PaymentSuccess = () => {

    // const { courseId } = useParams()

    const navigate = useNavigate()

    // const handleClickGotoCourseLibrary = () => {
    //     navigate(`/student/course`)
    // }

    return (
        <Container maxWidth='lg'>
            <AppBarSearchHeader />
            <Grid container spacing={0} direction="column" alignItems="center" justifyContent="center" minHeight='80vh'>
                {/* <Grid item marginBottom={3}>
                    <Typography variant='h4' fontSize='bold'color='success.main' >
                        Successful
                    </Typography>
                </Grid>    */}
                {/* <Grid item marginBottom={3}>
                    <Typography variant='h6' color='gray' >
                        Your payment has been processed successfully.
                    </Typography>
                </Grid> */}
                <Grid item marginBottom={3}>
                    <Alert severity="success">
                        <AlertTitle>Success</AlertTitle>
                        {/* This is a success alert — <strong>check it out!</strong> */}
                        Your payment has been processed successfully
                    </Alert>
                </Grid>
                <Grid item>
                    <Button variant='contained' onClick={() => navigate(`/student/course`)}>
                        Go to my course
                    </Button>
                </Grid>   
            </Grid> 

            {/* <Box display='flex'  flexDirection='column' alignItems='center' alignContent='center'>
                Success
            </Box> */}
            {/* <Box */}
            {/* <Box > */}

                {/* <Box position={'relative'} alignItems='center'> */}
                    {/* Success */}
                {/* </Box> */}

            {/* </Box> */}
            {/* <Box position='relative'  alignSelf='center' >
                Success
            </Box>
            <Box position='absolute' top='50%' >
                Your payment has been processed successfully.
            </Box>
            <Box position='absolute' top='60%' >
                <Button variant='contained'>Go to course library</Button>
            </Box> */}
        </Container>
    )
}

export default PaymentSuccess

{/* <Box textAlign='center'> */}
                    // Success
                // </Box>
                {/* <Typography alignContent='center'></Typography> */}
                {/* <Typography textAlign='center'>Your payment has been processed successfully</Typography>
                <Button variant='contained'>Go to course library</Button> */}