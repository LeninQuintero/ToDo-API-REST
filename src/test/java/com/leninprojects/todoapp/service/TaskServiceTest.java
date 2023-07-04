package com.leninprojects.todoapp.service;

import com.leninprojects.todoapp.exceptions.ToDoExceptions;
import com.leninprojects.todoapp.mapper.TaskInDTOToTask;
import com.leninprojects.todoapp.persistence.entity.Task;
import com.leninprojects.todoapp.persistence.entity.TaskStatus;
import com.leninprojects.todoapp.persistence.repository.TaskRepository;
import com.leninprojects.todoapp.service.dto.TaskInDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static java.util.Arrays.*;
import static org.mockito.BDDMockito.given;

class TaskServiceTest {
    private Task task;
    private TaskInDto taskInDto;

    @InjectMocks
    private TaskService taskService;
    @Mock
    TaskRepository taskRepository;
    @Mock
    TaskInDTOToTask taskInDTOToTask;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);

        taskInDto = new TaskInDto();
        taskInDto.setTitle("Task title 1");
        taskInDto.setDescription("Task description 1");
        taskInDto.setEta(LocalDateTime.now());

        task = new Task();
        task.setId(1L);
        task.setTitle("Task title 1");
        task.setDescription("Task description 1");
        task.setCreatedDate(LocalDateTime.now());
        task.setEta(LocalDateTime.now());
        task.setFinished(false);
        task.setTaskStatus(TaskStatus.ON_TIME);
    }

    @Test
    void createTaskTest() {
        given(taskInDTOToTask.map(taskInDto)).willReturn(task);
        given(taskRepository.save(task)).willReturn(task);
        Task result = taskService.createTask(taskInDto);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(task, result);
        Mockito.verify(taskInDTOToTask).map(taskInDto);
        Mockito.verify(taskRepository).save(task);
    }

    @Test
    void createTaskWithNullArgumentTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> taskService.createTask(null));
    }

    @Test
    void findAllTest() {
        given(taskRepository.findAll()).willReturn(Collections.singletonList(task));
        List<Task> result = taskService.findAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Collections.singletonList(task), result);
        Mockito.verify(taskRepository).findAll();
    }

    @ParameterizedTest
    @EnumSource(TaskStatus.class)
    void findAllByTaskStatusTest(TaskStatus status) {
        task.setTaskStatus(status);
        given(taskRepository.findAllByTaskStatus(status)).willReturn(Collections.singletonList(task));
        List<Task> result = taskService.findAllByTaskStatus(status);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Collections.singletonList(task), result);
        Mockito.verify(taskRepository).findAllByTaskStatus(status);
    }

    @ParameterizedTest
    @EnumSource(TaskStatus.class)
    void findAllByTaskStatusWithNoMatchingTasksTest(TaskStatus status) {
        given(taskRepository.findAllByTaskStatus(status)).willReturn(Collections.emptyList());
        List<Task> result = taskService.findAllByTaskStatus(status);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(taskRepository).findAllByTaskStatus(status);
    }
    @Test
    void findAllByTaskStatusWithNullArgumentTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> taskService.findAllByTaskStatus(null));
    }

    @Test
    void markTaskAsFinishedTest() {
        given(taskRepository.findById(task.getId())).willReturn(Optional.of(task));
        Assertions.assertDoesNotThrow(() -> taskService.markTaskAsFinished(task.getId()));
        Mockito.verify(taskRepository).findById(task.getId());
        Mockito.verify(taskRepository).markTaskAsFinished(task.getId());
    }

    @Test
    void markTaskAsFinishedWithNullIdTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> taskService.markTaskAsFinished(null));
    }

    @Test
    void markTaskAsFinishedWithNonExistentIdTest() {
        Long nonExistentId = 999L;
        given(taskRepository.findById(nonExistentId)).willReturn(Optional.empty());
        Assertions.assertThrows(ToDoExceptions.class, () -> taskService.markTaskAsFinished(nonExistentId));
        Mockito.verify(taskRepository).findById(nonExistentId);
    }

    @Test
    void deleteTaskTest() {
        given(taskRepository.findById(task.getId())).willReturn(Optional.of(task));
        Assertions.assertDoesNotThrow(() -> taskService.deleteTask(task.getId()));
        Mockito.verify(taskRepository).findById(task.getId());
        Mockito.verify(taskRepository).deleteById(task.getId());
    }

    @Test
    void deleteTaskTestWithNullIdTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> taskService.deleteTask(null));
    }

    @Test
    void deleteTaskTestWithNonExistentIdTest() {
        Long nonExistentId = 999L;
        given(taskRepository.findById(nonExistentId)).willReturn(Optional.empty());
        Assertions.assertThrows(ToDoExceptions.class, () -> taskService.markTaskAsFinished(nonExistentId));
        Mockito.verify(taskRepository).findById(nonExistentId);
    }
}
