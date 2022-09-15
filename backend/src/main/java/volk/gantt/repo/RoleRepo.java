package volk.gantt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import volk.gantt.model.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long>{
    
}
