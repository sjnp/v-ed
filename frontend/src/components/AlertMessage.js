import React from 'react'

// Materail UI core
import { makeStyles } from '@material-ui/core'

// Material UI component
import Snackbar from '@mui/material/Snackbar'
import Alert from '@mui/material/Alert'

// Materail UI icon
import InfoOutlinedIcon from '@mui/icons-material/InfoOutlined'
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline'
import WarningAmberOutlinedIcon from '@mui/icons-material/WarningAmberOutlined'
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline'

const AlertMessage = ({ open, type, message, onClose }) => {

    const textColor = 'white'
    const useStyles = makeStyles({
        info: { backgroundColor: '#2979ff', color: textColor },
        success: { backgroundColor: '#43A047', color: textColor },
        warning: { backgroundColor: '#ed6c02', color: textColor },
        error: { backgroundColor: '#d32f2f', color: textColor },
    })
    const classes = useStyles()

    const icons = {
        info: <InfoOutlinedIcon style={{ color: textColor }} />,
        success: <CheckCircleOutlineIcon style={{ color: textColor }} />,
        warning: <WarningAmberOutlinedIcon style={{ color: textColor }} />,
        error: <ErrorOutlineIcon style={{ color: textColor }} />,
    }

    return (
        <Snackbar open={open} autoHideDuration={5000} onClose={onClose}>
            <Alert severity={type} className={classes[type]} icon={icons[type]} onClose={onClose}>
                {message}
            </Alert>
        </Snackbar>
    )
}

export default AlertMessage