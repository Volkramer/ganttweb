package volk.gantt;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import volk.gantt.model.Task;
import volk.gantt.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskResource {
    private final TaskService taskService;

    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.findAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Integer id) {
        Task task = taskService.findTaskByUd(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        Task newTask = taskService.addTask(task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        Task updateTask = taskService.updateTask(task);
        return new ResponseEntity<>(updateTask, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") Integer id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}