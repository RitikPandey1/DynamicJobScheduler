/* eslint-disable react/prop-types */
import { useState } from "react";
import CustomTabPanel from "./CustomTabPanel"
import { Button, TextField, Paper, Typography } from "@mui/material";
import ParamsInputField from "./ParamsInputField";
import CronExpressionParser from "./CronExpressionParser";


const EditJobsTab = ({value,index}) =>{

    const[job,setJob] = useState();
    const[jobId,setJobId] = useState("");
    const [jobParams, setJobParams] = useState(null);
    const [paramsData,setParamsData] = useState({});

    const handleParamsData = (e)=>{
      const {name,value} = e.target;
       setParamsData({...paramsData,[name]:value})
    }

    const getJobFromId = async () => {
      if(jobId){
        const response = await fetch("http://localhost:8080/get-task-by-id?id="+jobId)
        const data = await response.json()
        setJob(data)
        setJobParams(data.params)
        console.log(JSON.parse(data.paramObject))
        setParamsData({...JSON.parse(data.paramObject), "cronExp":data.cronExp})
      }
    }

    const updateJob = async () =>{
      
      let res = {"id":job.id, "cronExp" : paramsData.cronExp }
      delete paramsData["cronExp"]
      res = {...res, "paramObject"  : JSON.stringify(paramsData)}
      console.log(res)

        try {
        const response = await fetch("http://localhost:8080/edit-task", {
        method: 'POST',
        headers: {
           'Content-Type':  'application/json', 
        },
        body: JSON.stringify(res)
        });

        if (response.ok) {
        console.log('Response:', response.status);
        setParamsData(null)
        setJob(null)
        setJobId('')
        setJobParams(null)
        } else {
        console.error('Failed to update:', response.status, response.statusText);
        }
     } catch (error) {
        console.error('Error occurred:', error);
     }
   }

    return <><CustomTabPanel value={value} index={index} >
            <TextField variant="outlined" label="Enter Job Id" value={jobId} onChange={(e) => {setJobId(e.target.value)}}  />
            <Button sx={{m:2}} variant="contained" onClick={getJobFromId} >Get Job</Button>
             
      { job && <Typography variant="h5" sx={{mt:3}}>Job Name :  {job.name}</Typography>}      
     { jobParams &&
     <Paper elevation={1} sx={{p:3,m:3}}>
        <Typography variant="h6" sx={{mb:3,  fontWeight: 'bold'}}>Input Parameters</Typography>
     {jobParams.map((e,i) => <ParamsInputField key={i} name={e.name} labelName={e.name} type={e.type} handleParamsData={handleParamsData} paramsData={paramsData} isDisabled={false} />)}
     <ParamsInputField  name="cronExp" labelName="Cron Expression"  handleParamsData={handleParamsData} paramsData={paramsData} isDisabled={false} />
     <CronExpressionParser cronExpression={paramsData["cronExp"] || ''}/>
     <Button  variant="contained" sx={{m:3}} onClick={updateJob}>Update Job</Button>
     </Paper>
    }  

        </CustomTabPanel></>
}

export default EditJobsTab;