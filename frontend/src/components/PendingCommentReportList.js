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
  URL_GET_ALL_PENDING_COURSES, URL_GET_ALL_PENDING_POST_REPORTS,
  URL_GET_ALL_PENDING_REVIEW_REPORTS,
  URL_PUT_PENDING_COURSE, URL_PUT_PENDING_POST_REPORT,
  URL_PUT_PENDING_REVIEW_REPORT
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
  const [selectedReport, setSelectedReport] = useState(null);
  // const [isApproving, setIsApproving] = useState(false);
  // const [isRejecting, setIsRejecting] = useState(false);

  useEffect(() => {
    axiosPrivate.get(URL_GET_ALL_PENDING_POST_REPORTS)
      .then(response => setPendingCommentReports(response.data))
      .then(() => setIsFinishFetching(true))
      .catch(err => console.error(err));
  }, [axiosPrivate])

  // const handleApproval = async (isApproved, reportId) => {
  //   try {
  //     isApproved ? setIsApproving(true) : setIsRejecting(false)
  //     const url = URL_PUT_PENDING_POST_REPORT.replace('{postReportId}', reportId)
  //     await axiosPrivate.put(url,
  //       null,
  //       {
  //         params: {
  //           isApproved: isApproved
  //         }
  //       }
  //     );
  //     await axiosPrivate.get(URL_GET_ALL_PENDING_POST_REPORTS)
  //       .then(response => setPendingCommentReports(response.data))
  //   } catch (err) {
  //     console.error(err)
  //   } finally {
  //     isApproved ? setIsApproving(false) : setIsRejecting(false)
  //   }
  // }
  const handleCollapse = (newSelectedReport) => {
    setSelectedReport(newSelectedReport !== selectedReport ? newSelectedReport : null)
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
            <TableCell >Report ID</TableCell>
            <TableCell>Report Reason</TableCell>
            <TableCell>Reporter</TableCell>
            <TableCell style={{width: 100}}/>
          </TableRow>
        </TableHead>
        <TableBody>
          {pendingCommentReports.map((report => (
            <React.Fragment key={report.id}>
              <TableRow
                sx={{'& > *': { borderBottom: 'unset' }}}
                hover
              >
                <TableCell>#{report.id}</TableCell>
                <TableCell>{report.reportReason}</TableCell>
                <TableCell>{`${report.reporterName}#${report.studentId}`}</TableCell>
                <TableCell>
                  <IconButton
                    aria-label="expand row"
                    size="small"
                    onClick={() => {
                      handleCollapse(report.id)
                    }}
                  >
                    {selectedReport === report.id ? <KeyboardArrowUpIcon/> : <KeyboardArrowDownIcon/>}
                  </IconButton>
                </TableCell>
              </TableRow>
              <TableRow>
                <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={4}>
                  <Collapse in={selectedReport === report.id} timeout='auto' unmountOnExit>
                    <Stack alignItems='center' sx={{mt: 5}}>
                      <PostWithReportedComment selectedReport={selectedReport}/>
                    </Stack>
                  </Collapse>
                </TableCell>
              </TableRow>
            </React.Fragment>
          )))}
        </TableBody>
      </Table>
    </TableContainer>
  </>)
}

export default PendingCommentReportList;