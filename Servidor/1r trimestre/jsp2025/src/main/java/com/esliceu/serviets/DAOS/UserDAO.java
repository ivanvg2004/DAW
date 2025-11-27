package com.esliceu.serviets.DAOS;

public class UserDAO {
    public boolean checkUser(String user, String password) {
        if (user.equals("bill") && password.equals("1234")) return true;
        if (user.equals("tom") && password.equals("abcde")) return true;
        return false;
    }
}
