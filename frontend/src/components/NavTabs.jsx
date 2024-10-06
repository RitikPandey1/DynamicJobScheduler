import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import { useState } from 'react';
import ScheduleJobsTab from './ScheduleJobsTab';
import ViewJobsTab from './ViewJobsTab';
import EditJobsTab from './EditJobsTab';


const NavTabs = () => {
    const [index,setIndex] = useState(0);

    function a11yProps(index) {
      return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
      };
    }

    return (<Box sx={{ width: '50%', border: '1px solid #ecf0f1' }}>
    <Box sx={{ borderBottom: 1, borderColor: 'divider'}}>
      <Tabs value={index} onChange={(e,v) => setIndex(v)}>
        <Tab label="Schedule Jobs" {...a11yProps(0)} />
        <Tab label="View Jobs" {...a11yProps(1)} />
        <Tab label="Edit Jobs" {...a11yProps(2)} />
      </Tabs>
    </Box>
     <ScheduleJobsTab value={index} index={0}/>
     <ViewJobsTab value={index} index={1} />
     <EditJobsTab value={index} index={2}/>
    </Box>
    )

}

export default NavTabs;