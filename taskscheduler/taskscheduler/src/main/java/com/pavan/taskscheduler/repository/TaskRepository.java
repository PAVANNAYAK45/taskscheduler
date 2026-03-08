package com.pavan.taskscheduler.repository;

import com.pavan.taskscheduler.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(String status);

    List<Task> findByStatusAndExecutionTimeLessThanEqual(String status, long time);

}