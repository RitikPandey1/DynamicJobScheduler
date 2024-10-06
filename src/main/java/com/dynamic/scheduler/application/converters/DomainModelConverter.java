package com.dynamic.scheduler.application.converters;

import com.dynamic.scheduler.application.domain.ParamDataDomain;
import com.dynamic.scheduler.application.domain.TaskMetaDataDomain;
import com.dynamic.scheduler.application.model.ParamData;
import com.dynamic.scheduler.application.model.TaskMetaData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DomainModelConverter {

    public DomainModelConverter INSTANCE = Mappers.getMapper(DomainModelConverter.class);

    ParamData convert (ParamDataDomain paramDataDomain);
    ParamDataDomain convert(ParamData paramData);
    TaskMetaData convert(TaskMetaDataDomain taskMetaDataDomain);
    TaskMetaDataDomain convert(TaskMetaData taskMetaData);

}
