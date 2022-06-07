import { Box, Paper ,Stack , Typography, TextField, Button } from '@mui/material';
import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import BankingRecipientSelect from "./BankingRecipientSelect";
import AccountManageInstructorSkeleton from "./AccountManageInstructorSkeleton"

const logoUrl = "https://raw.githubusercontent.com/omise/banks-logo/master/th/";

const AccountManageInstructor = ({drawerWidth,handleAddRecipent}) => {

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
        <Typography 
          // ml={3} 
          component='h1'
          variant='h5'
        >
          Begin Teaching Other
        </Typography>
        <Box sx={{ marginTop: 5 , marginX: 5 }}>
          <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <Typography 
              component="p"
              variant='p' 
            >
              Add Reciept detail paragraph
            </Typography>
          </Box>
        
        <Button
          fullWidth
          variant='contained'
          size='large'
          value='add'
          sx={{
            marginTop: 6,
            marginBottom: 2
          }}
          onClick={handleAddRecipent}
        >
          Add Receipt Account
        </Button>
        </Box>
      </Box>


    </Paper>
  );
}

export default AccountManageInstructor;