import React from "react"

// Material UI component
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import Paper from '@mui/material/Paper'
import Alert from '@mui/material/Alert'
import AlertTitle from '@mui/material/AlertTitle'

const SuccessAlertBox = ({ handleClick, text }) => {

  return (
    <Paper sx={{ mt: 25, p: 2 }}>
      <Alert severity="success" sx={{ m: 1, mb: 2 }} >
        <AlertTitle>Success</AlertTitle>
        {text}
      </Alert>
      <Box textAlign="center">
        <Button onClick={handleClick} type="button" variant="contained" color="success">
          Done
        </Button>
      </Box>
    </Paper>
  )
}

export default SuccessAlertBox; 