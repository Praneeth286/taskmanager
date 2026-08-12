package com.praneeth.taskmanager;

public record TaskResponse(Long id, String title, boolean isDone, String categoryName) {
    public static TaskResponse fromEntity(Task task) {
        String categoryName = task.getCategory() != null ? task.getCategory().getName() : null;
        return new TaskResponse(task.getId(), task.getTitle(), task.isDone(), categoryName);
    }
}