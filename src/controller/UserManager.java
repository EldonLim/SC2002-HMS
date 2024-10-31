package controller;
import database.DataBase;
import view.HMSAppView;

public class UserManager {

    public UserManager() {}
    public static boolean validateUser(String id, String password) {
        if (DataBase.getUsers().containsKey(id)) {
            return DataBase.getUsers().get(id).getPassword().equals(password);
        }
        return false;
    }

    public static void resetPassword(String newPassword) {
        DataBase.getUsers().get(DataBase.getCurrUserID()).setPassword(newPassword);
        System.out.println("Password Reset Successfully");
    }
}
