package com.taskforge.taskforge.dto;

import com.taskforge.taskforge.entity.Task;

public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String status;

    private String createdBy;
    private String assignedTo;

    // 🔥 Constructor
    public TaskResponseDTO(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();

        this.createdBy = task.getCreatedBy() != null
                ? task.getCreatedBy().getEmail()
                : null;

        this.assignedTo = task.getAssignedTo() != null
                ? task.getAssignedTo().getEmail()
                : null;
    }

    // 🔥 Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getCreatedBy() { return createdBy; }
    public String getAssignedTo() { return assignedTo; }
}