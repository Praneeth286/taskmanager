package com.praneeth.taskmanager;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.* ;
import java.util.List;
@RestController
public class TaskController {
    private final TaskRepository taskRepository;
    public TaskController(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
    @GetMapping("/tasks")
    public List <Task> getTasks() {
         return taskRepository.findAll();
  }
    @PostMapping ("/tasks")
 public Task createTask(@Valid @RequestBody Task newTask){
    Task task = new Task(null, newTask.getTitle(),false);
    return taskRepository.save(task);
    }
    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask){
    Task task = taskRepository.findById(id)
            .orElseThrow(()-> new TaskNotFoundException(id));
    task.setTitle(updatedTask.getTitle());
    task.setDone(updatedTask .isDone());
      return taskRepository.save(task);
    }
    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }



}