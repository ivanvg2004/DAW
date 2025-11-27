package com.paint.servlets.DAOS;

import com.paint.servlets.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO{

    private final static List<User> userDatabase = new ArrayList<>();

    static {
        userDatabase.add(new User("admin","admin", "admin"));
        userDatabase.add(new User("Ivan Vargas", "ivaanvargaas", "20004"));
    }

    public static boolean checkUser(String username, String password) {
        for (User user : userDatabase) {
            if (user.getUser().equals(username)) {
                return user.getPassword().equals(password);
            }
        }
        return false;
    }

    public static boolean doesUsernameExist(String username) {
        for (User user : userDatabase) {
            if (user.getUser().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static synchronized void addUser(User newUser) {
        userDatabase.add(newUser);
    }

    public static String getName(String username) {
        String res = "";
        for (User user : userDatabase) {
            if (user.getUser().equals(username)) {
                res = user.getName();
                break;
            }
        }
        return res;
    }

    public static User getUserByUsername(String username) {
        for (User user : userDatabase){
            if (user.getUser().equals(username)) {
                return user;
            }
        }
        return null;
    }
}