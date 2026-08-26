package com.praneeth.taskmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponse> getAllTasks() {
        String username = getCurrentUsername();
        return taskRepository.findAll().stream()
                .filter(task -> task.getOwner() != null && task.getOwner().getUsername().equals(username))
                .map(TaskResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public TaskResponse createTask(String title) {
        String username = getCurrentUsername();
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task(null, title, false);
        task.setOwner(owner);
        Task saved = taskRepository.save(task);
        log.info("Task created with id {} for user {}", saved.getId(), username);
        return TaskResponse.fromEntity(saved);
    }

    public TaskResponse updateTask(Long id, String title, Boolean isDone) {
        String username = getCurrentUsername();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Attempted to update non-existent task with id {}", id);
                    return new TaskNotFoundException(id);
                });
        if (task.getOwner() == null || !task.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("You do not have permission to update this task");
        }
        task.setTitle(title);
        task.setDone(isDone);
        Task saved = taskRepository.save(task);
        return TaskResponse.fromEntity(saved);
    }

    public void deleteTask(Long id) {
        String username = getCurrentUsername();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        if (task.getOwner() == null || !task.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("You do not have permission to delete this task");
        }
        log.info("Deleting task with id {}", id);
        taskRepository.deleteById(id);
    }

    public List<TaskResponse> getTasksByStatus(Boolean isDone) {
        return taskRepository.findAll().stream()
                .filter(task -> task.isDone().equals(isDone))
                .map(TaskResponse::fromEntity)
                .collect(Collectors.toList());
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

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}