package com.pavan.taskscheduler.scheduler;

import com.pavan.taskscheduler.entity.Task;
import com.pavan.taskscheduler.repository.TaskRepository;
import com.pavan.taskscheduler.service.TaskService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskRunner {

    private final TaskRepository taskRepository;
    private final TaskService taskService;

    public TaskRunner(TaskRepository taskRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    @Scheduled(fixedDelay = 5000)
    public void runTasks() {

        long now = System.currentTimeMillis();

        List<Task> tasks =
                taskRepository.findByStatusAndExecutionTimeLessThanEqual("PENDING", now);

        for (Task task : tasks) {

            try {

                task.setStatus("RUNNING");
                taskRepository.save(task);

                taskService.executeTask(task);

                task.setStatus("COMPLETED");
                taskRepository.save(task);

            } catch (Exception e) {

                task.setStatus("FAILED");
                taskRepository.save(task);

                System.out.println("Task failed: " + e.getMessage());
            }
        }
    }
}