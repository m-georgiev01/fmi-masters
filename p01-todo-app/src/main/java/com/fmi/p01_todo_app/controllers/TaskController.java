package com.fmi.p01_todo_app.controllers;

import com.fmi.p01_todo_app.models.Task;
import com.fmi.p01_todo_app.services.TaskService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    @Value("${example_user}")
    private String userName;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public List<Task> getAll(){
        return taskService.GetAll();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer id) {
        var task = taskService.GetById(id);

        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> addTask(@RequestBody Task task){
        return new ResponseEntity<Task>(taskService.Add(task), HttpStatus.CREATED);
    }

    @PutMapping("/tasks")
    public ResponseEntity<Task> updateTask(@RequestBody Task updatedTask) {
        var searchedTask = taskService.GetById(updatedTask.getId());
        if (searchedTask == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        taskService.Update(updatedTask);
        return new ResponseEntity<Task>(HttpStatus.OK);
    }

    @DeleteMapping("tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        var searchedTask = taskService.GetById(id);
        if (searchedTask == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        taskService.Delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
