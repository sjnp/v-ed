import {
  Button,
  CircularProgress,
  Paper,
  Stack,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow
} from "@mui/material";
import React, {useEffect, useState} from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import {useNavigate} from "react-router-dom";
import {
  URL_GET_ALL_PENDING_COURSES, URL_GET_ALL_PENDING_POST_REPORTS,
  URL_GET_ALL_PENDING_REVIEW_REPORTS,
  URL_PUT_PENDING_COURSE, URL_PUT_PENDING_POST_REPORT,
  URL_PUT_PENDING_REVIEW_REPORT
} from "../utils/url";
import CheckIcon from "@mui/icons-material/Check";
import LoadingButton from "@mui/lab/LoadingButton";
import CloseIcon from "@mui/icons-material/Close";

const PendingPostReportList = () => {

  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();

  const [pendingPostReports, setPendingPostReports] = useState([]);
  const [isFinishFetching, setIsFinishFetching] = useState(false);
  const [isApproving, setIsApproving] = useState(false);
  const [isRejecting, setIsRejecting] = useState(false);

  useEffect(() => {
    axiosPrivate.get(URL_GET_ALL_PENDING_POST_REPORTS)
      .then(response => setPendingPostReports(response.data))
      .then(() => setIsFinishFetching(true))
      .catch(err => console.error(err));
  }, [axiosPrivate])

  const handleApproval = async (isApproved, reportId) => {
    try {
      isApproved ? setIsApproving(true) : setIsRejecting(false)
      const url = URL_PUT_PENDING_POST_REPORT.replace('{postReportId}', reportId)
      await axiosPrivate.put(url,
        null,
        {
          params: {
            isApproved: isApproved
          }
        }
      );
      await axiosPrivate.get(URL_GET_ALL_PENDING_POST_REPORTS)
        .then(response => setPendingPostReports(response.data))
    } catch (err) {
      console.error(err)
    } finally {
      isApproved ? setIsApproving(false) : setIsRejecting(false)
    }
  }

  if (!isFinishFetching) {
    return (
      <Stack alignItems='center' sx={{mt: 5}}>
        <CircularProgress/>
      </Stack>
    );
  }

  return (<>
    <TableContainer component={Paper} variant='outlined'>
      <Table
        style={{tableLayout: 'fixed'}}
      >
        <TableHead>
          <TableRow>
            <TableCell>Post Topic</TableCell>
            <TableCell>Post Detail</TableCell>
            <TableCell>Report Reason</TableCell>
            <TableCell>Reporter</TableCell>
            <TableCell/>
          </TableRow>
        </TableHead>
        <TableBody>
          {pendingPostReports.map((report => (
            <TableRow
              key={report.id}
              sx={{'&:last-child td, &:last-child th': {border: 0}}}
              hover
            >
              <TableCell component='th'>{report.postTopic}</TableCell>
              <TableCell component='th'>{report.postDetail}</TableCell>
              <TableCell>{report.reportReason}</TableCell>
              <TableCell>{`${report.reporterName}#${report.studentId}`}</TableCell>
              <TableCell align='right'>
                <Stack
                  direction='row'
                  alignItems='center'
                  justifyContent='flex-end'
                  spacing={1}
                >
                <LoadingButton
                  size='small'
                  disabled={isRejecting}
                  variant='contained'
                  loading={isApproving}
                  loadingPosition="start"
                  startIcon={<CheckIcon/>}
                  onClick={() => handleApproval(true, report.id)}
                >
                  Approve
                </LoadingButton>
                <LoadingButton
                  size='small'
                  color="error"
                  disabled={isApproving}
                  variant='outlined'
                  loading={isRejecting}
                  loadingPosition="end"
                  endIcon={<CloseIcon/>}
                  onClick={() => handleApproval(false, report.id)}
                >
                  Reject
                </LoadingButton>
                </Stack>
              </TableCell>

            </TableRow>
          )))}
        </TableBody>
      </Table>
    </TableContainer>
  </>)
}

export default PendingPostReportList;