package volk.gantt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import volk.gantt.model.Task;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    void deleteTaskById(Long id);

    Optional<Task> findTaskById(Long id);
}
