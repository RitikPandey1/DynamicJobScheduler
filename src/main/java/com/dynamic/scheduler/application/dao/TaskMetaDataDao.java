package com.dynamic.scheduler.application.dao;

import com.dynamic.scheduler.application.model.TaskMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskMetaDataDao extends JpaRepository<TaskMetaData,Long> {
}
