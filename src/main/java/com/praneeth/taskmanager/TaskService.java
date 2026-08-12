package com.praneeth.taskmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public TaskResponse createTask(String title) {
        Task task = new Task(null, title, false);
        Task saved = taskRepository.save(task);
        log.info("Task created with id {}", saved.getId());
        return TaskResponse.fromEntity(saved);
    }

    public TaskResponse updateTask(Long id, String title, Boolean isDone) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Attempted to update non-existent task with id {}", id);
                    return new TaskNotFoundException(id);
                });
        task.setTitle(title);
        task.setDone(isDone);
        Task saved = taskRepository.save(task);
        return TaskResponse.fromEntity(saved);
    }

    public List<TaskResponse> getTasksByStatus(Boolean isDone) {
        return taskRepository.findAll().stream()
                .filter(task -> task.isDone().equals(isDone))
                .map(TaskResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public void deleteTask(Long id) {
        log.info("Deleting task with id {}", id);
        taskRepository.deleteById(id);
    }

    public TaskResponse assignCategory(Long taskId, Long categoryId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + categoryId));
        task.setCategory(category);
        Task saved = taskRepository.save(task);
        return TaskResponse.fromEntity(saved);
    }
}