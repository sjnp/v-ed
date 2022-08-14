import {
  Button,
  CircularProgress, Collapse,
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
  URL_GET_ALL_PENDING_COMMENT_REPORTS, URL_PUT_PENDING_COMMENT_REPORT,
} from "../utils/url";
import CheckIcon from "@mui/icons-material/Check";
import LoadingButton from "@mui/lab/LoadingButton";
import CloseIcon from "@mui/icons-material/Close";
import IconButton from "@mui/material/IconButton";
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import PostWithReportedComment from "./PostWithReportedComment";

const PendingCommentReportList = () => {

  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();

  const [pendingCommentReports, setPendingCommentReports] = useState([]);
  const [isFinishFetching, setIsFinishFetching] = useState(false);
  const [isApproving, setIsApproving] = useState(false);
  const [isRejecting, setIsRejecting] = useState(false);

  useEffect(() => {
    axiosPrivate.get(URL_GET_ALL_PENDING_COMMENT_REPORTS)
      .then(response => setPendingCommentReports(response.data))
      .then(() => setIsFinishFetching(true))
      .catch(err => console.error(err));
  }, [axiosPrivate])

  const handleApproval = async (isApproved, reportId) => {
    try {
      isApproved ? setIsApproving(true) : setIsRejecting(false)
      const url = URL_PUT_PENDING_COMMENT_REPORT.replace('{commentReportId}', reportId)
      await axiosPrivate.put(url,
        null,
        {
          params: {
            isApproved: isApproved
          }
        }
      );
      await axiosPrivate.get(URL_GET_ALL_PENDING_COMMENT_REPORTS)
        .then(response => setPendingCommentReports(response.data))
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
            <TableCell>Reported Comment</TableCell>
            <TableCell>Report Reason</TableCell>
            <TableCell>Reporter</TableCell>
            <TableCell/>
          </TableRow>
        </TableHead>
        <TableBody>
          {pendingCommentReports.map((report => (
            <TableRow
              key={report.id}
              sx={{'&:last-child td, &:last-child th': {border: 0}}}
              hover
            >
              <TableCell>{report.comment}</TableCell>
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

export default PendingCommentReportList;