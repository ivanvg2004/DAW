package com.paint.servlets.services;

import com.paint.servlets.DAOS.UserDAO;
import com.paint.servlets.models.User;

public class UserService {
    public String loginUser(String username, String password) {
        boolean userExist = UserDAO.checkUser(username, password);
        if (userExist){
            return UserDAO.getName(username);
        }
        return null;
    }

    public boolean registerUser(String name, String username, String password) {
        if (UserDAO.doesUsernameExist(username)){
            return false;
        }
        User newUser = new User(name, username , password);
        UserDAO.addUser(newUser);
        return true;
    }
    public User getUserByUsername(String username){
        return UserDAO.getUserByUsername(username);
    }
}
