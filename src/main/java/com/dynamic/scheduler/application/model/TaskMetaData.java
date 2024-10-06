package com.dynamic.scheduler.application.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class TaskMetaData {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

    String name;

    @OneToMany(mappedBy = "taskMetaData", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
     List<ParamData> params;
     String paramObject;

      String cronExp;

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

    public List<ParamData> getParams() {
        return params;
    }

    public void setParams(List<ParamData> params) {
        this.params = params;
    }

    public String getParamObject() {
        return paramObject;
    }

    public void setParamObject(String paramObject) {
        this.paramObject = paramObject;
    }

    public String getCronExp() {
        return cronExp;
    }

    public void setCronExp(String cronExp) {
        this.cronExp = cronExp;
    }
}
