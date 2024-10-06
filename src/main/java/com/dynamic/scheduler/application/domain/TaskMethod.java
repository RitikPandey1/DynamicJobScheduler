package com.dynamic.scheduler.application.domain;


import java.util.List;

public class TaskMethod {

 private     String name;
 private   List<ParamDataDomain> params;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParamDataDomain> getParams() {
        return params;
    }

    public void setParams(List<ParamDataDomain> paramData) {
        this.params = paramData;
    }
}
