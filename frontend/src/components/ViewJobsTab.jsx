/* eslint-disable react/prop-types */
import { useEffect, useState } from "react";
import CustomTabPanel from "./CustomTabPanel"
import Box from '@mui/material/Box';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import ParamsInputField from "./ParamsInputField";
import CronExpressionParser from "./CronExpressionParser";

function Row(props) {
  const { row } = props;
  const [open, setOpen] = useState(false);
  const [jobParams, setJobParams] = useState(null);
  const [paramsData,setParamsData] = useState({});

  useEffect(()=>{
    setJobParams(row.params)
    setParamsData({...JSON.parse(row.paramObject), "cronExp":row.cronExp})
  },[row]);

  

  return (
    <>
      <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
        <TableCell>
          <IconButton
            aria-label="expand row"
            size="small"
            onClick={() => setOpen(!open)}
          >
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell align="right" component="th" scope="row">
          {row.id}
        </TableCell>
        <TableCell align="right">{row.name}</TableCell>
        <TableCell align="right">{row.cronExp}</TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box sx={{ margin: 1 }}>
              { jobParams &&
     <Paper elevation={1} sx={{p:3,m:3}}>
        <Typography variant="h6" sx={{mb:3,  fontWeight: 'bold'}}>Input Parameters</Typography>
     {jobParams.map((e,i) => <ParamsInputField key={i} name={e.name} labelName={e.name} type={e.type}  paramsData={paramsData} isDisabled={true} />)}
     <ParamsInputField  name="cronExp" labelName="Cron Expression"  paramsData={paramsData} isDisabled={true} />
     <CronExpressionParser cronExpression={paramsData["cronExp"] || ''}/>
     </Paper>
    }  
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </>
  );
}


const ViewJobsTab = ({value,index}) =>{

    const [tasks,setTasks] = useState([]);
    
    useEffect(()=>{
      if(value==index) fetchTasks()
    },[value,index]);

    async function fetchTasks(){
      const response = await fetch("http://localhost:8080/view-tasks")
      const data = await response.json()
      setTasks(data)
    
  }

    return <><CustomTabPanel value={value} index={index} >
          <TableContainer component={Paper} sx={{}}>
      <Table aria-label="collapsible table">
        <TableHead>
          <TableRow>
            <TableCell />
            <TableCell align="right" sx={{ fontWeight: 'bold'}}>Job Id</TableCell>
            <TableCell align="right" sx={{ fontWeight: 'bold'}}>Job Name</TableCell>
            <TableCell align="right" sx={{ fontWeight: 'bold'}}>Cron Expression</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {tasks.map((row) => (
            <Row key={row.id} row={row} />
          ))}
        </TableBody>
      </Table>
    </TableContainer>
        </CustomTabPanel></>
}

export default ViewJobsTab;