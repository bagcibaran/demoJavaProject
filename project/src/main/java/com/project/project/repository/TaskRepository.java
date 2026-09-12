package com.project.project.repository;

import com.project.project.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    //Buna Bak!!!!!!!!!!!!!!!!!
    TaskEntity findByTitleAndIsCompleted(String title, boolean isCompleted);
}
