import { Box, Paper ,Stack , Typography, TextField, Button } from '@mui/material';
import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import BankingRecipientSelect from "./BankingRecipientSelect";
import AccountManageInstructorActiveMemo from "./AccountManageInstructorActiveMemo"
import AccountManageInstructorInput from "./AccountManageInstructorInput"
import AccountManageInstructorReciept from "./AccountManageInstructorReciept"
import AccountManageInstructorSkeleton from "./AccountManageInstructorSkeleton"

const drawerWidth = 720;

const AccountManageInstructor = () => {
  const roles = useSelector((state) => state.auth.value.roles);
  const [tiggerInputRecipient,setTiggerInputRecipient] = useState(false)
  const [addOrChange,setAddOrChange] = useState('')
  
  const handleAddRecipent = () => {
    tiggerInputRecipient ? setTiggerInputRecipient(false) : setTiggerInputRecipient(true);
  }

  return (
    tiggerInputRecipient ? 
    <AccountManageInstructorInput drawerWidth={drawerWidth} handleAddRecipent={handleAddRecipent} addOrChange={addOrChange} /> :
    !(roles.includes('INSTRUCTOR')) ? 
    <AccountManageInstructorActiveMemo drawerWidth={drawerWidth} handleAddRecipent={handleAddRecipent} /> :
    <AccountManageInstructorReciept drawerWidth={drawerWidth} handleAddRecipent={handleAddRecipent} />
  );
}

export default AccountManageInstructor;