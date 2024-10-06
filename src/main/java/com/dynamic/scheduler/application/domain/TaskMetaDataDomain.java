package com.dynamic.scheduler.application.domain;

import com.dynamic.scheduler.application.model.ParamData;

import java.util.List;

public class TaskMetaDataDomain {

    Long id;
    String name;
    List<ParamDataDomain> params;
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

    public List<ParamDataDomain> getParams() {
        return params;
    }

    public void setParams(List<ParamDataDomain> params) {
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
