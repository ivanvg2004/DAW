package com.liceu.hibernate.services;

import com.liceu.hibernate.models.Job;
import com.liceu.hibernate.models.User;
import com.liceu.hibernate.repository.JobRepo;
import com.liceu.hibernate.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    JobRepo jobRepo;

    public void newUser(String username, int birthYear, String password, Long jobId) {
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setBirthYear(birthYear);
        Job job = jobRepo.findById(jobId).get();
        user.setJob(job);
        userRepo.save(user);
    }

    public List<User> allUsers() {
        return userRepo.findAll();//Devuelve todos
    }

    public User getUserByName(String username) {
        return userRepo.findByName(username);
    }

    public List<Job> allJobs() {
        return jobRepo.findAll();
    }
}
