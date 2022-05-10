package volk.gantt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import volk.gantt.model.Task;

public interface TaskRepo extends JpaRepository<Task, Integer> {
    void deleteTaskById(Integer id);
}
