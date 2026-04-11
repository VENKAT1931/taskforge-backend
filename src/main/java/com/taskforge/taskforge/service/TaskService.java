package com.taskforge.taskforge.service;
import com.taskforge.taskforge.dto.TaskResponseDTO;
import com.taskforge.taskforge.entity.Task;
import com.taskforge.taskforge.entity.User;
import com.taskforge.taskforge.repository.TaskRepository;
import com.taskforge.taskforge.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // 🔥 GET MY TASKS
    public List<Task> getMyTasks(Authentication authentication) {

        String username = authentication.getName();

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findByAssignedToOrCreatedBy(user, user);
    }

    // 🔥 CREATE TASK (ADMIN ONLY)
    public Task createTask(Task task, Authentication authentication) {

        String username = authentication.getName();

        User createdBy = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ ROLE CHECK (FIXED)
        if (!"ADMIN".equalsIgnoreCase(createdBy.getRole())) {
            throw new AccessDeniedException("Only ADMIN can create tasks");
        }

        task.setCreatedBy(createdBy);

        // 🔥 ASSIGN USER (if provided)
        if (task.getAssignedTo() != null && task.getAssignedTo().getId() != null) {

            Long assignedUserId = task.getAssignedTo().getId();

            User assignedUser = userRepository.findById(assignedUserId)
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));

            task.setAssignedTo(assignedUser);
        }

        return taskRepository.save(task);
    }

    // 🔥 UPDATE STATUS (ONLY ASSIGNED USER)
    public Task updateStatus(Long taskId, String status, Authentication authentication) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        String username = authentication.getName();

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (task.getAssignedTo() == null) {
            throw new RuntimeException("Task is not assigned to anyone");
        }

        // ✅ PERMISSION CHECK (FIXED)
        if (!task.getAssignedTo().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not allowed to update this task");
        }

        task.setStatus(status);

        return taskRepository.save(task);
    }
    public Task assignTask(Long taskId, Long userId, Authentication authentication) {

        String username = authentication.getName();

        // 🔥 get logged-in user
        User admin = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 allow only ADMIN
        if (!"ADMIN".equalsIgnoreCase(admin.getRole())) {
            throw new org.springframework.security.access.AccessDeniedException("Only ADMIN can assign tasks");
        }

        // 🔥 get task
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // 🔥 get user to assign
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 assign task
        task.setAssignedTo(user);

        return taskRepository.save(task);
    }

    // 🔥 GET ALL TASKS
    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(TaskResponseDTO::new)
                .toList();
    }
}