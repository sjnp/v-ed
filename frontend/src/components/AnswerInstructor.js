import React from 'react'
import moment from 'moment'

// Material UI component
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Accordion from '@mui/material/Accordion'
import AccordionSummary from '@mui/material/AccordionSummary'
import AccordionDetails from '@mui/material/AccordionDetails'

// Material UI icon
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'
import AssignmentIcon from '@mui/icons-material/Assignment'
import AssignmentTurnedInIcon from '@mui/icons-material/AssignmentTurnedIn'

const AnswerInstructor = ({ answer }) => {

    const { answerId, chapterIndex, noIndex, datetime, fileName, commentInstructor, studentName } = answer

    return (
        <Accordion>
            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                <Grid container spacing={4}>
                    <Grid item>
                    {
                        commentInstructor === null ?
                        <AssignmentIcon color='action' />
                        :
                        <AssignmentTurnedInIcon color='success' />
                    }
                    </Grid>
                    <Grid item>
                        <Grid container direction='row' alignItems='center' spacing={1}>
                            <Grid item>
                                <Typography variant='caption' color='gray'>Date:</Typography>
                            </Grid>
                            <Grid item>
                                <Typography variant='button' fontWeight='normal'>
                                    {moment(datetime).format("DD/MM/YYYY")}
                                </Typography>
                            </Grid>
                        </Grid>
                    </Grid>
                    <Grid item>
                        <Grid container direction='row' alignItems='center' spacing={1}>
                            <Grid item>
                                <Typography variant='caption' color='gray'>Time:</Typography>
                            </Grid>
                            <Grid item>
                                <Typography variant='button' fontWeight='normal'>
                                    {moment(datetime).format("kk:mm")}
                                </Typography>
                            </Grid>
                        </Grid>
                    </Grid>
                    <Grid item>
                        <Grid container direction='row' alignItems='center' spacing={1}>
                            <Grid item>
                                <Typography variant='caption' color='gray'>Student name:</Typography>
                            </Grid>
                            <Grid item>
                                <Typography>{studentName}</Typography>
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            </AccordionSummary>
            <AccordionDetails>
                <Grid container>
                    <Grid item xs={12}>
                        Download
                    </Grid>
                    <Grid item xs={12}>
                        instructor comment
                    </Grid>
                </Grid>
            </AccordionDetails>
        </Accordion>
    )
}

export default AnswerInstructor