package com.liceu.demo.dao;

import com.liceu.demo.models.User;

public interface TestDao {
    User getUser(int id);
    int lastId();
    void saveUser(User user);
}
