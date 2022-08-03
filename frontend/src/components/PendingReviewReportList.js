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
  URL_GET_ALL_PENDING_COURSES,
  URL_GET_ALL_PENDING_REVIEW_REPORTS,
  URL_PUT_PENDING_COURSE,
  URL_PUT_PENDING_REVIEW_REPORT
} from "../utils/url";
import CheckIcon from "@mui/icons-material/Check";
import LoadingButton from "@mui/lab/LoadingButton";
import CloseIcon from "@mui/icons-material/Close";

const PendingReviewReportList = () => {

  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();

  const [pendingReviewReports, setPendingReviewReports] = useState([]);
  const [isFinishFetching, setIsFinishFetching] = useState(false);
  const [isApproving, setIsApproving] = useState(false);
  const [isRejecting, setIsRejecting] = useState(false);

  useEffect(() => {
    axiosPrivate.get(URL_GET_ALL_PENDING_REVIEW_REPORTS)
      .then(response => setPendingReviewReports(response.data))
      .then(() => setIsFinishFetching(true))
      .catch(err => console.error(err));
  }, [axiosPrivate])

  const handleApproval = async (isApproved, reportId) => {
    // try {
    //   isApproved ? setIsApproving(true) : setIsRejecting(true)
    //   const url = URL_PUT_PENDING_COURSE.replace('{courseId}', courseId)
    //   await axiosPrivate.put(url,
    //     null,
    //     {
    //       params: {
    //         isApproved: isApproved
    //       }
    //     }
    //   );
    //   navigate('/admin');
    // } catch (err) {
    //   isApproved ? setIsApproving(false) : setIsRejecting(false)
    //   console.error(err);
    // }
    try {
      isApproved ? setIsApproving(true) : setIsRejecting(false)
      const url = URL_PUT_PENDING_REVIEW_REPORT.replace('{reviewReportId}', reportId)
      await axiosPrivate.put(url,
        null,
        {
          params: {
            isApproved: isApproved
          }
        }
      );
      await axiosPrivate.get(URL_GET_ALL_PENDING_REVIEW_REPORTS)
        .then(response => setPendingReviewReports(response.data))
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
            <TableCell>Review Message</TableCell>
            <TableCell>Report Reason</TableCell>
            <TableCell>Reporter</TableCell>
            <TableCell/>
          </TableRow>
        </TableHead>
        <TableBody>
          {pendingReviewReports.map((report => (
            <TableRow
              key={report.id}
              sx={{'&:last-child td, &:last-child th': {border: 0}}}
              hover
            >
              <TableCell component='th'>{report.reviewComment}</TableCell>
              <TableCell>{report.reportReason}</TableCell>
              <TableCell>{`${report.reporterName}#${report.studentId}`}</TableCell>
              <TableCell align='right'>
                <LoadingButton
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
                  sx={{ml: 1}}
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
              </TableCell>
            </TableRow>
          )))}
        </TableBody>
      </Table>
    </TableContainer>
  </>)
}

export default PendingReviewReportList;