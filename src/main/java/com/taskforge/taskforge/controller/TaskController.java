package com.taskforge.taskforge.controller;

import com.taskforge.taskforge.dto.TaskResponseDTO;
import com.taskforge.taskforge.entity.Task;
import com.taskforge.taskforge.service.TaskService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // 🔥 CREATE TASK (ADMIN ONLY)
    @PostMapping
    public Task createTask(@RequestBody Task task,
                           Authentication authentication) {
        return taskService.createTask(task, authentication);
    }

    // 🔥 GET MY TASKS (DTO)
    @GetMapping("/my")
    public List<Task> getMyTasks(Authentication authentication) {
        return taskService.getMyTasks(authentication); // ✅ FIXED
    }

    // 🔥 GET ALL TASKS (DTO)
    @GetMapping
    public List<TaskResponseDTO> getAllTasks() {
        return taskService.getAllTasks(); // ✅ FIXED
    }

    // 🔥 UPDATE STATUS
    @PutMapping("/{id}/status")
    public Task updateStatus(@PathVariable Long id,
                             @RequestParam String status,
                             Authentication authentication) {
        return taskService.updateStatus(id, status, authentication);
    }

    // 🔥 ASSIGN TASK
    @PutMapping("/{id}/assign")
    public Task assignTask(@PathVariable Long id,
                           @RequestParam Long userId,
                           Authentication authentication) {
        return taskService.assignTask(id, userId, authentication);
    }
    @GetMapping("/test")
    public String test() {
        return "WORKING";
    }
}