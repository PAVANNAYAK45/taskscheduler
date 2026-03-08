package com.pavan.taskscheduler.service;

import com.pavan.taskscheduler.email.EmailService;
import com.pavan.taskscheduler.entity.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final EmailService emailService;

    public TaskService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void executeTask(Task task) {

        emailService.sendEmail(
                task.getEmail(),
                "Scheduled Task",
                "Your task executed: " + task.getTaskName()
        );

        System.out.println("Task executed: " + task.getTaskName());
    }
}