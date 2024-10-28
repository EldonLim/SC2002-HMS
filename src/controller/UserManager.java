package controller;

import database.DataBase;

public class UserManager {

    public UserManager() {
    }

    public static boolean validateUser(String id, String password) {
        if (DataBase.Users.containsKey(id)) {
            return DataBase.Users.get(id).getPassword().equals(password);
        }
        return false;
    }
}