/* eslint-disable react/prop-types */
import { useState, useEffect } from "react";
import CustomTabPanel from "./CustomTabPanel"
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import { Button, Paper, Typography } from "@mui/material";
import ParamsInputField from "./ParamsInputField";
import ResponseView from "./ResponseView";
import CronExpressionParser from "./CronExpressionParser";



const ScheduleJobsTab = ({value,index}) =>{
  
    const [jobParams, setJobParams] = useState(null);
    const [jobs,setJobs] = useState([]);
    const [paramsData,setParamsData] = useState({});
    const[job,setJob] = useState({name:'Select Job'});
    const[open,setOpen] = useState(false)
    const[msg,setMsg] = useState('')
    useEffect(()=>{
         fetchjobs()
    },[])

     async function fetchjobs(){
        const response = await fetch("http://localhost:8080/get-tasks")
        const data = await response.json()
        setJobs(data)
    }

    const handleParamsData = (e)=>{
      const {name,value} = e.target;
       setParamsData({...paramsData,[name]:value})
    }

    const submitJob = async () =>{
       let res = {...job, "cronExp" : paramsData.cronExp }
       delete paramsData["cronExp"]
       res = {...res, paramObject : JSON.stringify({...paramsData})}
       console.log(res)

         try {
         const response = await fetch("http://localhost:8080/invoke-task", {
         method: 'POST',
         headers: {
            'Content-Type':  'application/json', 
         },
         body: JSON.stringify(res)
         });

         if (response.ok) {
         console.log('Response:', response.status);
         setMsg("Job Scheduled Succefully !")
         setOpen(true)         
         setParamsData({})

         } else {
            setMsg("Job Scheduled Failed!")
            setOpen(true)      
         console.error('Failed to submit:', response.status, response.statusText);
         }
      } catch (error) {
         console.error('Error occurred:', error);
      }
    }

    const handleClose = (event, reason) => {
      if (reason === 'clickaway') {
        return;
      }
  
      setOpen(false);
    };

    return <><CustomTabPanel value={value} index={index} >
           <Autocomplete
      disablePortal
      value={job}
        onChange={(event, newValue) => {
            console.log(newValue);
           setJobParams(newValue.params) 
          setJob(newValue);
        }}
      options={jobs}
      getOptionLabel={(option) => option.name}
      sx={{ width: 600 }}
      renderOption={(props,option)=>{
         const {key, ...optionProps} = props;
         return <Box key = {key} {...optionProps}>{option.name}</Box>
      }}
      renderInput={(params) => <TextField {...params} label="Jobs" />}
    />

     { jobParams &&
     <Paper elevation={1} sx={{p:3,m:3}}>
        <Typography variant="h6" sx={{mb:3,  fontWeight: 'bold'}}>Input Parameters</Typography>
     {jobParams.map((e,i) => <ParamsInputField key={i} name={e.name} labelName={e.name} type={e.type} handleParamsData={handleParamsData} paramsData={paramsData} isDisabled={false}/>)}
     <ParamsInputField  name="cronExp" labelName="Cron Expression"  handleParamsData={handleParamsData} paramsData={paramsData} isDisabled={false} />
     <CronExpressionParser cronExpression={paramsData["cronExp"] || ''}/>

     <Button  variant="contained" sx={{m:3}} onClick={submitJob}>Submit Job</Button>
     </Paper>
    } 
       <ResponseView handleClose={handleClose} msg={msg} open={open}/>
        </CustomTabPanel></>
}

export default ScheduleJobsTab;