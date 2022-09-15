package volk.gantt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import volk.gantt.model.Link;

@Repository
public interface LinkRepo extends JpaRepository<Link, Long> {
    void deleteLinkById(Long id);

    Optional<Link> findLinkById(Long id);
}
