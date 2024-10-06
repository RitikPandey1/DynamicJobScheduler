package com.dynamic.scheduler.application.service;

import com.dynamic.scheduler.application.converters.DomainModelConverter;
import com.dynamic.scheduler.application.dao.TaskMetaDataDao;
import com.dynamic.scheduler.application.domain.ParamDataDomain;
import com.dynamic.scheduler.application.domain.TaskEditDomain;
import com.dynamic.scheduler.application.domain.TaskMetaDataDomain;
import com.dynamic.scheduler.application.model.ParamData;
import com.dynamic.scheduler.application.model.TaskMetaData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {

    DynamicCronTaskScheduler dynamicCronTaskScheduler;
    TaskMetaDataDao taskMetaDataDao;
    @Autowired
    public TaskService(DynamicCronTaskScheduler dynamicCronTaskScheduler,TaskMetaDataDao taskMetaDataDao) {
        this.dynamicCronTaskScheduler = dynamicCronTaskScheduler;
        this.taskMetaDataDao = taskMetaDataDao;
    }

    @PostConstruct
    void initTasks(){
        List<TaskMetaData> tasks = taskMetaDataDao.findAll();
        tasks.forEach(e-> {
            try {
                invokeTask(DomainModelConverter.INSTANCE.convert(e),false);
            } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                     InstantiationException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public List<TaskMetaDataDomain> getAllTasks(){
        List<TaskMetaData> tasks = taskMetaDataDao.findAll();
        return tasks.stream().map(e->DomainModelConverter.INSTANCE.convert(e)).collect(Collectors.toList());
    }

    public TaskMetaDataDomain getTaskById(Long id){
        Optional<TaskMetaData> task = taskMetaDataDao.findById(id);
        if(task.isPresent()) return DomainModelConverter.INSTANCE.convert(task.get());
        return null;
    }
    @Transactional
    public Long invokeTask(TaskMetaDataDomain tmd, Boolean save) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String className = getClassName(tmd.getName());
        String methodName = getMehodName(tmd.getName());

        List<Object> paramValues = getParamsValues(tmd.getParamObject(),tmd.getParams());
        Object[] values = new Object[paramValues.size()];
        paramValues.toArray(values);

        Method m = getMethod(tmd.getParams(),className,methodName);
        Class clazz = Class.forName(className);
        Object obj = clazz.getConstructor().newInstance();

        Runnable task = ()->{
            try {
                m.invoke(obj,values);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        };
        return save ? dynamicCronTaskScheduler.addCronTaskAndSave(task,tmd) : dynamicCronTaskScheduler.addCronTask(task,tmd);
    }
    private String getMehodName(String name){
        String[] spilt = name.split("\\.");
        return spilt[spilt.length - 1];
    }

    private String getClassName(String name){
        String[] spilt = name.split("\\.");
        StringBuffer clazzName = new StringBuffer();
        for(int i =0 ;i<=spilt.length-2; i++){
            clazzName.append(spilt[i]);
            if(i!= spilt.length-2)  clazzName.append(".");
        }
        return clazzName.toString();
    }

    private List<Object> getParamsValues(String object, List<ParamDataDomain> params) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(object);
        List<Object> paramValues = new ArrayList<>();

        for(ParamDataDomain pd : params){
            if(pd.getType().equals("java.lang.Integer")){
                paramValues.add(node.get(pd.getName()).asInt());
            }else if (pd.getType().equals("java.lang.String")){
                paramValues.add(node.get(pd.getName()).asText());
            }else if(pd.getType().equals("java.lang.Boolean")){
                paramValues.add(node.get(pd.getName()).asBoolean());
            }
        }
        return paramValues;
    }

    private Method getMethod(List<ParamDataDomain> params,String className,String methodName) throws ClassNotFoundException, NoSuchMethodException {
        List<Class<?>> pt = params.stream().map(e-> {
            try {
                return Class.forName(e.getType());
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }).collect(Collectors.toList());
        Class<?>[] ptt = new Class<?>[pt.size()];
        pt.toArray(ptt);
        Class<?> clazz = Class.forName(className);
        return clazz.getDeclaredMethod(methodName,ptt);
    }

    public void removeTask(Long id){
        dynamicCronTaskScheduler.removeCronTask(id,true);
    }

    @Transactional
    public void editTask(Long id,TaskEditDomain taskEditDomain){
            Optional<TaskMetaData> tmd =  taskMetaDataDao.findById(id);
            tmd.ifPresentOrElse(e->{
                e.setCronExp(taskEditDomain.getCronExp());
                e.setParamObject(taskEditDomain.getParamObject());
                taskMetaDataDao.save(e);
                dynamicCronTaskScheduler.removeCronTask(e.getId(),false);
                try {
                    invokeTask(DomainModelConverter.INSTANCE.convert(e),false);
                } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                         InstantiationException | IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            },()->{throw new RuntimeException("Task Not Found!");});

    }

    public List<TaskMetaData> getAllScheduledTasks(){
        return  taskMetaDataDao.findAll();
    }

}
