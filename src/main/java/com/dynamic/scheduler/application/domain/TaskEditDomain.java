package com.dynamic.scheduler.application.domain;

public class TaskEditDomain {

    Long id;
    String paramObject;

    String cronExp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
