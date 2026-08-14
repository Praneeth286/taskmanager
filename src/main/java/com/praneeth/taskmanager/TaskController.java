package com.praneeth.taskmanager;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.* ;
import java.util.List;
@RestController
public class TaskController {
    private final TaskService taskService;
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }
    @GetMapping("/tasks")
    public List <TaskResponse> getTasks(@RequestParam(required=false)Boolean done) {
        if (done == null){
            return taskService.getAllTasks();
        }
        return taskService.getTasksByStatus(done);
    }
    @PostMapping ("/tasks")
 public TaskResponse createTask(@Valid @RequestBody Task newTask){
        return taskService.createTask(newTask.getTitle());
    }
    @PutMapping("/tasks/{taskId}/category/{categoryId}")
    public TaskResponse assignCategory(@PathVariable Long taskId, @PathVariable Long categoryId) {
        return taskService.assignCategory(taskId, categoryId);
    }
    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id) {
      taskService.deleteTask(id);
    }
}