package com.springPaint.p1SpringBoot.dao;

import com.springPaint.p1SpringBoot.models.User;

import java.util.List;

public interface UserDao {
    User getUser(int id);
    User getUserByUsername(String username);
    void saveUser(User user);
    List<User> getAllUsers();
}
