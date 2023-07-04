package com.leninprojects.todoapp.mapper;

import com.leninprojects.todoapp.persistence.entity.Task;
import com.leninprojects.todoapp.persistence.entity.TaskStatus;
import com.leninprojects.todoapp.service.dto.TaskInDto;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class TaskInDTOToTaskTest {

    @Test
    void testMap() {
        TaskInDTOToTask taskMapper = new TaskInDTOToTask();
        TaskInDto in = new TaskInDto();
        in.setTitle("Test title");
        in.setDescription("Test description");
        in.setEta(LocalDateTime.now().plusDays(1));

        Task task = taskMapper.map(in);

        assertEquals(in.getTitle(), task.getTitle());
        assertEquals(in.getDescription(), task.getDescription());
        assertEquals(in.getEta(), task.getEta());
        assertNotNull(task.getCreatedDate());
        assertFalse(task.isFinished());
        assertEquals(TaskStatus.ON_TIME, task.getTaskStatus());
    }
}