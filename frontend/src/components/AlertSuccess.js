import React from "react"
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { Box, Button, CssBaseline, Paper, Container, Alert, AlertTitle } from '@mui/material';

const AlertSuccess = (props) => {

    const theme = createTheme()

    const { handleClick, text, labelButton } = props

    return (
        <ThemeProvider theme={theme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline />
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
            </Container>
        </ThemeProvider>
    )
}

export default AlertSuccess