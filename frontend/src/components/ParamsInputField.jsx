/* eslint-disable react/prop-types */

import { TextField } from "@mui/material";

const ParamsInputField = ({labelName,name,type,paramsData,handleParamsData, isDisabled}) =>{
    return (<div><TextField sx={{ margin:'10px', width:'50%' }}  label={labelName + (type != undefined ? " ("+type+")" : "")} name={name} value={paramsData[name] || ''} onChange={handleParamsData} variant="outlined" disabled={isDisabled} /></div>);
 }
 
 export default ParamsInputField;