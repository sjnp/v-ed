import { Box, Paper ,Stack , Typography, TextField, Button, Skeleton } from '@mui/material';

const drawerWidth = 720;
const logoUrl = "https://raw.githubusercontent.com/omise/banks-logo/master/th/";

const AccountManageInstructorSkeleton = () => {
  
  return (
    <Paper
      elevation={2}
      sx={{
        width: drawerWidth,
        flexShrink: 0
      }}
    >
      <Box
        component="form"
        // onSubmit={handleSubmit}
        // noValidate
        sx={{
          margin: 5
        }}
      >
        <Skeleton variant='text' width={200} height={50}/>
        <Box sx={{ marginTop: 3 , marginX: 5 }}>
          <Box sx={{ display: 'flex', alignItems: 'center' }}>
          {/* <Stack alignItems='center' justifyContent='center' sx= {{ width : 80 , height : 80 , borderRadius : '3px' }}>
            <Skeleton style={{ width : "80%" , height : "80%" }} />
          </Stack> */}
          <Skeleton variant="rectangular" width={80} height={80} />
          <Stack justifyContent='center'>
          <Skeleton variant='text' height={40} width={300} sx={{marginLeft : 3}} />
          <Skeleton variant='text' height={30} width={200} sx={{marginLeft : 3}} />
          </Stack>
          </Box>
        
        <Skeleton
          height={80}
          sx={{
            marginTop: 3
          }}
        />
        <Skeleton
          height={80}
          sx={{
            marginTop: 0
          }}
        />
        <Skeleton
          height={80}
          sx={{
            marginTop: 2
          }}
        >
        </Skeleton>
        </Box>
      </Box>


    </Paper>
  );
}

export default AccountManageInstructorSkeleton;