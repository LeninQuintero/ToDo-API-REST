package com.leninprojects.todoapp.service;

import com.leninprojects.todoapp.exceptions.ToDoExceptions;
import com.leninprojects.todoapp.mapper.TaskInDTOToTask;
import com.leninprojects.todoapp.persistence.entity.Task;
import com.leninprojects.todoapp.persistence.entity.TaskStatus;
import com.leninprojects.todoapp.persistence.repository.TaskRepository;
import com.leninprojects.todoapp.service.dto.TaskInDto;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository repository;
    private final TaskInDTOToTask taskMapper;

    public TaskService(TaskRepository repository, TaskInDTOToTask taskMapper) {
        this.repository = repository;
        this.taskMapper = taskMapper;
    }

    public Task createTask(TaskInDto taskInDto){
        if(taskInDto == null) {
            throw new IllegalArgumentException();
        }
        Task task = taskMapper.map(taskInDto);
        return this.repository.save(task);
    }

    public List<Task> findAll() {
        return this.repository.findAll();
    }

    public  List<Task> findAllByTaskStatus(TaskStatus status) {
        if(status == null) {
            throw new IllegalArgumentException();
        }
        return this.repository.findAllByTaskStatus(status);
    }
    @Transactional
    public void markTaskAsFinished(Long id){
        if(id == null) {
            throw new IllegalArgumentException();
        } else {
            Optional<Task> optionalTask = this.repository.findById(id);
            if(optionalTask.isEmpty()){
                throw new ToDoExceptions("Task not found", HttpStatus.NOT_FOUND);
            }
            this.repository.markTaskAsFinished(id);
        }
    }

    public void deleteTask(Long id){
        if(id == null) {
            throw new IllegalArgumentException();
        } else {
            Optional<Task> optionalTask = this.repository.findById(id);
            if (optionalTask.isEmpty()) {
                throw new ToDoExceptions("Task not found", HttpStatus.NOT_FOUND);
            }
            this.repository.deleteById(id);
        }
    }
}
