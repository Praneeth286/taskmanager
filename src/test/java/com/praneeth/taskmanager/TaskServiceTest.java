package com.praneeth.taskmanager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;

public class TaskServiceTest {
    @BeforeEach
    void setUpSecurityContext(){
        Authentication mockAuth = mock(Authentication.class);
        when(mockAuth.getName()).thenReturn("testuser");
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuth);
        SecurityContextHolder.setContext(mockSecurityContext);
    }
    @AfterEach
    void clearSecurityContext(){
        SecurityContextHolder.clearContext();
    }
    @Test
    void createTaskSavesAndReturnsTask() {
        TaskRepository mockRepo = mock(TaskRepository.class);
        CategoryRepository mockCategoryRepo = mock(CategoryRepository.class);
        UserRepository mockUserRepo = mock(UserRepository.class);

        Task fakeSavedTask = new Task(1L, "Test task", false);
        User fakeUser = new User("testuser", "hashedpassword");

        when(mockUserRepo.findByUsername(anyString())).thenReturn(Optional.of(fakeUser));
        when(mockRepo.save(any(Task.class))).thenReturn(fakeSavedTask);

        TaskService service = new TaskService(mockRepo, mockCategoryRepo, mockUserRepo);
        TaskResponse result = service.createTask("Test task");

        assertEquals("Test task", result.title());
        assertEquals(1L, result.id());


        // Note: this test will need a mocked SecurityContext to work correctly now,
        // since createTask() calls getCurrentUsername() internally
    }
}