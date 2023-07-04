package com.leninprojects.todoapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.leninprojects.todoapp.persistence.entity.Task;
import com.leninprojects.todoapp.persistence.entity.TaskStatus;
import com.leninprojects.todoapp.service.TaskService;
import com.leninprojects.todoapp.service.dto.TaskInDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = TaskController.class)
class TaskControllerTest {
    private Task task;
    private TaskInDto taskInDto;
    private ObjectMapper taskJson;
    private String expectedCreatedDate;
    private String expectedEtaDate;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskService taskService;

    @BeforeEach
    void setUp() {
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

        expectedCreatedDate = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(task.getCreatedDate());
        expectedEtaDate = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(task.getEta());

        taskJson = new ObjectMapper();
        taskJson.registerModule(new JavaTimeModule());
    }

    @Test
    void createTaskControllerTest() throws Exception {
        given(taskService.createTask(taskInDto)).willReturn(task);
        mockMvc.perform(
                post("/tasks").contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson.writeValueAsString(taskInDto)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is(task.getTitle())))
                .andExpect(jsonPath("$.description", is(task.getDescription())))
                .andExpect(jsonPath("$.createdDate", is(expectedCreatedDate)))
                .andExpect(jsonPath("$.eta", is(expectedEtaDate)))
                .andExpect(jsonPath("$.finished", is(task.isFinished())))
                .andExpect(jsonPath("$.taskStatus", is(task.getTaskStatus().name())));
    }

    @Test
    void findAllControllerTest() throws Exception {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        given(taskService.findAll()).willReturn(tasks);
        mockMvc.perform(
                        get("/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is(task.getTitle())))
                .andExpect(jsonPath("$[0].description", is(task.getDescription())))
                .andExpect(jsonPath("$[0].createdDate", is(expectedCreatedDate)))
                .andExpect(jsonPath("$[0].eta", is(expectedEtaDate)))
                .andExpect(jsonPath("$[0].finished", is(task.isFinished())))
                .andExpect(jsonPath("$[0].taskStatus", is(task.getTaskStatus().name())));
    }

    @ParameterizedTest
    @EnumSource(TaskStatus.class)
    void findAllByStatus(TaskStatus status) throws Exception {
        List<Task> tasks = new ArrayList<>();
        task.setTaskStatus(status);
        tasks.add(task);
        given(taskService.findAllByTaskStatus(status)).willReturn(tasks);
        mockMvc.perform(
                        get("/tasks/status/{status}", status).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is(task.getTitle())))
                .andExpect(jsonPath("$[0].description", is(task.getDescription())))
                .andExpect(jsonPath("$[0].createdDate", is(expectedCreatedDate)))
                .andExpect(jsonPath("$[0].eta", is(expectedEtaDate)))
                .andExpect(jsonPath("$[0].finished", is(task.isFinished())))
                .andExpect(jsonPath("$[0].taskStatus", is(task.getTaskStatus().name())));
    }

    @Test
    void markAsFinishedTest() throws Exception {
        mockMvc.perform(patch("/tasks/mark_as_finished/{id}", task.getId()))
                .andExpect(status().isNoContent());
        verify(taskService).markTaskAsFinished(task.getId());
    }

    @Test
    void deleteTask() throws Exception {
        mockMvc.perform(delete("/tasks/{id}", task.getId()))
                .andExpect(status().isNoContent());
        verify(taskService).deleteTask(task.getId());
    }
}