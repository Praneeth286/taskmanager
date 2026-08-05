package com.praneeth.taskmanager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TaskTest {

    @Test
    void newTaskStartsNotDone() {
        Task task = new Task(1L, "Buy groceries", false);
        assertFalse(task.isDone());
    }

    @Test
    void taskTitleIsSetCorrectly() {
        Task task = new Task(1L, "Walk the dog", true);
        assertEquals("Walk the dog", task.getTitle());
    }

    @Test
    void taskCanBeMarkedDone() {
        Task task = new Task(1L, "Learn Spring Boot", false);
        task.setDone(true);
        assertEquals(true, task.isDone());
    }
}