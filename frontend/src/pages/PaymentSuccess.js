import { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom';
import useAxiosPrivate from "../hooks/useAxiosPrivate";

// Material Ui component
import Container from '@mui/material/Container'
import Grid from '@mui/material/Grid'
import Box from '@mui/material/Box'
import Stack from '@mui/material/Stack'
import Typography from '@mui/material/Typography'
import Button from '@mui/material/Button'
import Alert from '@mui/material/Alert'
import AlertTitle from '@mui/material/AlertTitle'
import Skeleton from '@mui/material/Skeleton';

// component
import AppBarSearchHeader from '../components/AppBarSearchHeader'


const PaymentSuccess = () => {

    const { courseId } = useParams()
    const [isPaid,setIsPaid] = useState(false);
    const [isDataLoad,setIsDataLoad] = useState(false);
    const axiosPrivate = useAxiosPrivate();

    const navigate = useNavigate()

    useEffect(async () => {
        console.log("Before Enter")
        try {
            const result = await axiosPrivate.post("/api/students/purchase/check-purchase", courseId )
            console.log(result);
        setIsPaid(result)
        console.log("Enter")
        setIsDataLoad(true);
        }
        catch(error){
            console.log(error)
        }
        
      }, [])
       
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
                {console.log(isDataLoad)}
                {!isDataLoad ? 
                    <Skeleton variant="rectangular" height={150} />
                    : 
                    isPaid ? 
                        <Alert severity="success">
                            <AlertTitle>Payment Success</AlertTitle>
                            Your payment has been processed successfully.
                        </Alert>
                        :
                        <Alert severity="error">
                            <AlertTitle>Payment Failed</AlertTitle>
                            Your payment has problem occur. 
                        </Alert>
                }
                </Grid>
                <Grid item>
                {isDataLoad ? 
                    <Button variant='contained' onClick={() => navigate(`/student/course`)}>
                        Go to my course
                    </Button>
                    :
                    <Skeleton variant="text" />
                }
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