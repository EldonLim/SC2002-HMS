package controller;

import database.DataBase;
import helper.Encryption;

public class UserManager {

    public UserManager() {
    }

    public static boolean validateUser(String id, String password) {
        if (DataBase.getUsers().containsKey(id))
            return DataBase.getUsers().get(id).getPassword().equals(Encryption.encode(password));
        return false;
    }

    public static void resetPassword(String newPassword) {
        DataBase.getUsers().get(DataBase.getCurrentUserID()).setPassword(Encryption.encode(newPassword));
        System.out.println("\nPassword Reset Successfully");
    }
}
