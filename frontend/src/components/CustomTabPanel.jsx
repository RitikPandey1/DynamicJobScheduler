/* eslint-disable react/prop-types */

import Box from '@mui/material/Box';

const CustomTabPanel = ({children,value,index, ...others})=>{

    return <div
            hidden={value!==index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...others}
    >
        {value==index && <Box sx={{p:3}}>{children}</Box>}
    </div>


}

export default CustomTabPanel;