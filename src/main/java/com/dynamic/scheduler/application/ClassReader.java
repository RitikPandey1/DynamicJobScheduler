package com.dynamic.scheduler.application;


import com.dynamic.scheduler.application.annotations.DynamicScheduler;
import com.dynamic.scheduler.application.annotations.SchedulerFunction;
import com.dynamic.scheduler.application.domain.ParamDataDomain;
import com.dynamic.scheduler.application.model.ParamData;
import com.dynamic.scheduler.application.domain.TaskMethod;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;


import java.lang.reflect.Method;

import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class ClassReader {

    public List<TaskMethod> getAllDynamicScheduledClasses() throws ClassNotFoundException {

        Class clazz = ClassReader.class;

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(DynamicScheduler.class));
       List<String> classes = new ArrayList<>();
        Set<BeanDefinition> res = scanner.findCandidateComponents(clazz.getPackageName());

        for(BeanDefinition bd : res){
            classes.add(bd.getBeanClassName());
        }
        List<TaskMethod> avalTasks = new ArrayList<>();
        for (String c: classes){
           avalTasks.addAll(getSchedulerFunctionMethod(c));
        }
          return avalTasks;
    }

    private List<TaskMethod> getSchedulerFunctionMethod(String className) throws ClassNotFoundException {
          List<TaskMethod> tasks = new ArrayList<>();
        Class<?> claaz = Class.forName(className);
       for(Method m :  claaz.getDeclaredMethods()){
           if(m.isAnnotationPresent(SchedulerFunction.class)){
               TaskMethod tm = new TaskMethod();
               tm.setName(className+"."+ m.getName());
                List<ParamDataDomain> paramsList = new ArrayList<>();
               for(Parameter p : m.getParameters()){
                   paramsList.add(new ParamDataDomain(p.getName(),p.getType().getName()));
               }
               tm.setParams(paramsList);

               tasks.add(tm);
           }
       }
       return tasks;
    }


}
