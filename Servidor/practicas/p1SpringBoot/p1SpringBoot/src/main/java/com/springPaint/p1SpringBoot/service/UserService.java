package com.springPaint.p1SpringBoot.service;

import com.springPaint.p1SpringBoot.dao.UserDao;
import com.springPaint.p1SpringBoot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUser(int id) {return userDao.getUser(id);}

    public boolean saveUser(String username, String name, String password){
        if (userDao.getUserByUsername(username) != null){
            return false;
        }
        User user = new User();
        user.setUsername(username);
        user.setName(name);
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userDao.saveUser(user);
        return true;
    }

    public User validateUser(String username, String password){
        User user = userDao.getUserByUsername(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())){
            return user;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
