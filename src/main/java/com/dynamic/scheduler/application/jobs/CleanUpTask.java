package com.dynamic.scheduler.application.jobs;

import com.dynamic.scheduler.application.annotations.DynamicScheduler;
import com.dynamic.scheduler.application.annotations.SchedulerFunction;

@DynamicScheduler
public class CleanUpTask {

    @SchedulerFunction
    public void cleanTempFiles(Integer code, String name, Boolean isPermanent){
        System.out.println("####### Running cleanTempFiles task code "+code+"  ################ ");
    }
    @SchedulerFunction
    public void cleanCachedFiles( String dirName, Boolean isPermanent){
        System.out.println("####### Running cleanCachedFiles task dirName : "+dirName+"   ################  ");

    }
}
