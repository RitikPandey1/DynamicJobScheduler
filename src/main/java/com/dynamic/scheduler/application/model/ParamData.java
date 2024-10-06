package com.dynamic.scheduler.application.model;


import jakarta.persistence.*;

@Entity
public class ParamData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;
     String name;
     String type;

    @ManyToOne
     private TaskMetaData taskMetaData;
    public ParamData() {
    }

    public ParamData(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TaskMetaData getTaskMetaData() {
        return taskMetaData;
    }

    public void setTaskMetaData(TaskMetaData taskMetaData) {
        this.taskMetaData = taskMetaData;
    }
}
