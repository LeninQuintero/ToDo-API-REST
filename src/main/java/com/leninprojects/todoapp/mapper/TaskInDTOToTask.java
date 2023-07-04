package com.leninprojects.todoapp.mapper;

import com.leninprojects.todoapp.persistence.entity.Task;
import com.leninprojects.todoapp.persistence.entity.TaskStatus;
import com.leninprojects.todoapp.service.dto.TaskInDto;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class TaskInDTOToTask implements IMapper<TaskInDto, Task> {
    @Override
    public Task map(TaskInDto in) {
        Task task = new Task();
        task.setTitle(in.getTitle());
        task.setDescription(in.getDescription());
        task.setEta(in.getEta());
        task.setCreatedDate(LocalDateTime.now());
        task.setFinished(false);
        task.setTaskStatus(TaskStatus.ON_TIME);
        return task;
    }
}
