package com.praneeth.taskmanager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
public class TaskServiceTest {
    @Test
    void createTaskSavesAndReturnsTask(){
        TaskRepository mockRepo=mock(TaskRepository.class);
        CategoryRepository mockCategoryRepo=mock(CategoryRepository.class);
        Task fakeSavedTask = new Task(1L, "Test task" , false);
        when(mockRepo.save(any(Task.class))).thenReturn(fakeSavedTask);
        TaskService service = new TaskService(mockRepo, mockCategoryRepo);
        TaskResponse result = service.createTask("Test task");
        assertEquals("Test task", result.title());
        assertEquals(1L, result.id());
    }
}
