import React from "react"
import { Box, Button, Paper, Alert, AlertTitle } from '@mui/material';

const SuccessAlertBox = (props) => {


  const { handleClick, text, labelButton } = props

  return (
    <Paper sx={{ marginTop: 25, padding: 2 }}>
      <Alert severity="success" sx={{ margin: 1, marginBottom: 2 }} >
        <AlertTitle>Success</AlertTitle>
        {text}
      </Alert>
      <Box textAlign="center">
        <Button onClick={handleClick} type="button" variant="contained" color="success">
          {labelButton}
        </Button>
      </Box>
    </Paper>
  )
}

export default SuccessAlertBox; 