package com.leninprojects.todoapp.service.dto;

import com.leninprojects.todoapp.persistence.entity.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskInDto {
    private String title;
    private String description;
    private LocalDateTime eta;
}
