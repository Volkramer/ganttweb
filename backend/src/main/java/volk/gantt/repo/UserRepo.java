package volk.gantt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import volk.gantt.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
    
    User findByUsername(String username);

}
