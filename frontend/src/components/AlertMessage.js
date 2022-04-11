import React from "react"
import { Box, Button, Paper, Alert, AlertTitle, Modal } from '@mui/material';

const AlertMessage = ({ open, handleOpen, type, text, labelButton }) => {

  const handleClick = () => handleOpen(false)

  return (
    <Modal open={open} sx={{ width: 300, m: 'auto' }}>
      <Paper sx={{ marginTop: 25, padding: 2 }}>
        <Alert severity={type} sx={{ margin: 1, marginBottom: 2 }} >
          <AlertTitle>{type}</AlertTitle>
          {text}
        </Alert>
        <Box textAlign="center">
          <Button onClick={handleClick} type="button" variant="contained" color={type}>
            {labelButton}
          </Button>
        </Box>
      </Paper>
    </Modal>
  )
}

export default AlertMessage; 