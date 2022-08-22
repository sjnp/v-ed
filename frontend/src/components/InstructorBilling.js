import Paper from "@mui/material/Paper";
import {
  CircularProgress,
  Divider,
  Stack,
  Table, TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography
} from "@mui/material";
import React, {useEffect, useState} from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import Grid from "@mui/material/Grid";
import TableSortLabel from "@mui/material/TableSortLabel";
import {visuallyHidden} from "@mui/utils";
import {Box} from "@mui/system";
import {URL_GET_ALL_TRANSACTIONS} from "../utils/url";

const InstructorBilling = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [order, setOrder] = useState('desc');
  const [orderBy, setOrderBy] = useState('date'); //date
  const [dataRows, setDataRows] = useState([])

  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    axiosPrivate.get(URL_GET_ALL_TRANSACTIONS)
      .then(res => {
        const newDataRows = res.data.map((row) => {
          const date = new Date(row.date);
          return {...row, date: date.toLocaleString()}
        })
        setDataRows(newDataRows)
      })
      .then(() => setIsLoading(false))
      .catch(err => console.error(err));
  }, [])

  function descendingComparator(a, b, orderBy) {
    if (b[orderBy] < a[orderBy]) {
      return -1;
    }
    if (b[orderBy] > a[orderBy]) {
      return 1;
    }
    return 0;
  }

  function getComparator(order, orderBy) {
    return order === 'desc'
      ? (a, b) => descendingComparator(a, b, orderBy)
      : (a, b) => -descendingComparator(a, b, orderBy);
  }

  const handleRequestSort = (property) => {
    const isAsc = orderBy === property && order === 'asc';
    setOrder(isAsc ? 'desc' : 'asc');
    setOrderBy(property);
  }

  function stableSort(array, comparator) {
    return array.sort(comparator);
  }

  const headCells = [
    {
      id: 'date',
      alignLeft: true,
      label: 'Transfer Date'
    },

    {
      id: 'courseName',
      alignLeft: true,
      label: 'Course'
    },
    {
      id: 'studentName',
      alignLeft: true,
      label: 'Student'
    },
    {
      id: 'amount',
      alignLeft: true,
      label: 'Amount(THB)'
    },


  ]

  return (
    <Paper variant='outlined'>
      <Stack
        spacing={2}
        sx={{padding: 2}}
      >
        <Stack direction='row' justifyContent='space-between' alignItems='center'>
          <Typography variant='h5'>Billing</Typography>
        </Stack>
        <Divider/>
        {isLoading
          ? <Stack alignItems='center' sx={{mt: 5, padding: 5}}>
            <CircularProgress/>
          </Stack>
          : <TableContainer component={Paper} variant='outlined'>
            <Table
              style={{tableLayout: 'fixed'}}
            >
              <TableHead>
                <TableRow>
                  {headCells.map((headCell) => (
                    <TableCell
                      key={headCell.id}
                      align={headCell.alignLeft ? 'left' : 'right'}
                      sortDirection={orderBy === headCell.id ? order : false}
                    >
                      <TableSortLabel
                        active={orderBy === headCell.id}
                        direction={orderBy === headCell.id ? order : 'asc'}
                        onClick={() => handleRequestSort(headCell.id)}
                      >
                        {headCell.label}
                        {orderBy === headCell.id ? (
                          <Box component="span" sx={visuallyHidden}>
                            {order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                          </Box>
                        ) : null}
                      </TableSortLabel>
                    </TableCell>
                  ))}
                </TableRow>
              </TableHead>
              <TableBody>
                {stableSort(dataRows, getComparator(order, orderBy))
                  .map((row, index) => (
                    <TableRow
                      key={index}
                      hover
                    >
                      <TableCell component='th'>{row.date}</TableCell>
                      <TableCell align='left'>{row.courseName}</TableCell>
                      <TableCell align='left'>{row.studentName}</TableCell>
                      <TableCell align='left'>{(row.amount/100.0).toFixed(2)}</TableCell>
                    </TableRow>
                  ))}

              < /TableBody>
            </Table>
          </TableContainer>
        }
      </Stack>
    </Paper>
  );
}

export default InstructorBilling;