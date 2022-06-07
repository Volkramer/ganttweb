package volk.gantt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import volk.gantt.model.Task;

@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {
    void deleteTaskById(Integer id);

    Optional<Task> findTaskById(Integer id);
}
