package com.dynamic.scheduler.application.controller;

import com.dynamic.scheduler.application.ClassReader;
import com.dynamic.scheduler.application.domain.TaskEditDomain;
import com.dynamic.scheduler.application.domain.TaskMetaDataDomain;
import com.dynamic.scheduler.application.domain.TaskMethod;
import com.dynamic.scheduler.application.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {


    ClassReader classReader;
    TaskService taskService;


    @Autowired
    public ApiController(ClassReader classReader, TaskService taskService) {
        this.classReader = classReader;
        this.taskService = taskService;
    }

    @GetMapping("/get-tasks")
    public List<TaskMethod> getTasks() throws ClassNotFoundException {
        return classReader.getAllDynamicScheduledClasses();
    }

    @GetMapping("/view-tasks")
    public List<TaskMetaDataDomain> viewTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/get-task-by-id")
    public TaskMetaDataDomain getTaskById(@RequestParam("id") Long id) {
        return taskService.getTaskById(id);
    }
    
    @PostMapping("/invoke-task")
    public void invokeTask (@RequestBody TaskMetaDataDomain tmd) throws ClassNotFoundException, NoSuchMethodException, IOException, InvocationTargetException, InstantiationException, IllegalAccessException {
        taskService.invokeTask(tmd,true);
    }




    @DeleteMapping("/remove-task")
    public void removeTask(@RequestParam Long id){
        taskService.removeTask(id);
    }

    @PostMapping("/edit-task")
    public void editTask(@RequestBody TaskEditDomain taskEditDomain){
        taskService.editTask(taskEditDomain.getId(),taskEditDomain);
    }

}
