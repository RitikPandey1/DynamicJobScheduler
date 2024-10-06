package com.dynamic.scheduler.application.service;

import com.dynamic.scheduler.application.converters.DomainModelConverter;
import com.dynamic.scheduler.application.dao.TaskMetaDataDao;
import com.dynamic.scheduler.application.domain.TaskEditDomain;
import com.dynamic.scheduler.application.domain.TaskMetaDataDomain;
import com.dynamic.scheduler.application.domain.TaskMethod;
import com.dynamic.scheduler.application.model.ParamData;
import com.dynamic.scheduler.application.model.TaskMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

@Service
public class DynamicCronTaskScheduler {

    private TaskScheduler taskScheduler;
    private Map<Long, ScheduledFuture<?>> scheduledTaskMap;

    private TaskMetaDataDao taskMetaDataDao;
   @Autowired
    public DynamicCronTaskScheduler(TaskMetaDataDao taskMetaDataDao) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler =  new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.setThreadNamePrefix("[DynamicTaskScheduler]-");
        threadPoolTaskScheduler.initialize();
        this.taskScheduler = threadPoolTaskScheduler;
        this.scheduledTaskMap = new HashMap<>();
        this.taskMetaDataDao = taskMetaDataDao;
    }

    public CronTrigger getCronTrigger(String cronExp){
        return new  CronTrigger(cronExp);
    }

  private ScheduledFuture<?> scheduleTask(Runnable task, String cron){
      if(!CronExpression.isValidExpression(cron)){
          throw  new IllegalArgumentException("cron expression is not valid");
      }
      CronTrigger cronTrigger = this.getCronTrigger(cron);
      return   this.taskScheduler.schedule(task,cronTrigger);
  }

    public Long addCronTaskAndSave(Runnable task, TaskMetaDataDomain tmd){
        ScheduledFuture<?> future = scheduleTask(task,tmd.getCronExp());
        TaskMetaData taskMetaData = DomainModelConverter.INSTANCE.convert(tmd);
        for (ParamData pd : taskMetaData.getParams()) {
            pd.setTaskMetaData(taskMetaData);
        }
        taskMetaData = taskMetaDataDao.save(taskMetaData);

        this.scheduledTaskMap.put(taskMetaData.getId(),future);
        return taskMetaData.getId();
    }

    public Long addCronTask(Runnable task, TaskMetaDataDomain tmd){
        ScheduledFuture<?> future = scheduleTask(task,tmd.getCronExp());
       this.scheduledTaskMap.put(tmd.getId(),future);
        return tmd.getId();
    }

    public void removeCronTask(Long key,Boolean delete){
        ScheduledFuture<?> future = this.scheduledTaskMap.get(key);
        if(future!=null){
            future.cancel(true);
        }
        this.scheduledTaskMap.remove(key);
        if(delete){
            taskMetaDataDao.deleteById(key);
        }

    }





}
