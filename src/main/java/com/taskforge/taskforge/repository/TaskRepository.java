package com.taskforge.taskforge.repository;

import com.taskforge.taskforge.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.taskforge.taskforge.entity.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedToOrCreatedBy(User assignedTo, User createdBy);
}