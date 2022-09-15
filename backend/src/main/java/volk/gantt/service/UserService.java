package volk.gantt.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import volk.gantt.model.User;
import volk.gantt.repo.UserRepo;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User updateUser(User user) {
        return userRepo.save(user);
    }

    public User findUserById(Long id) {
        return userRepo.getById(id);
    }

    public User findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
