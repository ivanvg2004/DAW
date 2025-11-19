package com.liceu.demo.service;

import com.liceu.demo.dao.TestDao;
import com.liceu.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    TestDao testDao;

    public int suma(int a, int b){
        return a+b;
    }

    public User getUser(int id) {
        return testDao.getUser(id);
    }

    public void saveUser(String name, String password){
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        testDao.saveUser(user);
    }
}
