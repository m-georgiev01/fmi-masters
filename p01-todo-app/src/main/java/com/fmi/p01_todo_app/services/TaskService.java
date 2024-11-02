package com.fmi.p01_todo_app.services;

import com.fmi.p01_todo_app.models.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private ArrayList<Task> tasksCollection = new ArrayList<>();
    private SequenceService sequenceService;

    public TaskService(SequenceService sequenceService) {
        this.sequenceService = sequenceService;
    }

    public Task GetById(int id){
        return tasksCollection.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Task> GetAll(){
        return tasksCollection;
    }

    public Task Add(Task task){
        task.setId(sequenceService.GetNextId());
        tasksCollection.add(task);

        return task;
    }

    public void Update(Task task){
        for (int i = 0; i < tasksCollection.size(); i++) {
            Task currentTask = tasksCollection.get(i);

            if (currentTask.getId() == task.getId()) {
                tasksCollection.set(i, task);
            }
        }
    }

    public void Delete(int id){
        tasksCollection.removeIf(task -> task.getId() == id);
    }
}
