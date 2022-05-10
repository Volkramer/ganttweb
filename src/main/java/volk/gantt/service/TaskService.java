package volk.gantt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import volk.gantt.exception.TaskNotFoundException;
import volk.gantt.model.Task;
import volk.gantt.repo.TaskRepo;

public class TaskService {
    private final TaskRepo taskRepo;

    @Autowired
    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public Task addTask(Task task) {
        return taskRepo.save(task);
    }

    public List<Task> findAllTasks() {
        return taskRepo.findAll();
    }

    public Task updateTask(Task task) {
        return taskRepo.save(task);
    }

    public Task findTaskByUd(Integer id) {
        return taskRepo.findTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task by id " + id + " was not found"));
    }

    public void deleteTask(Integer id) {
        taskRepo.deleteTaskById(id);
    }

}
