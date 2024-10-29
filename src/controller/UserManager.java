package controller;
import database.DataBase;
import view.HMSAppView;

public class UserManager {

    public UserManager() {}
    public static boolean validateUser(String id, String password) {
        if (DataBase.Users.containsKey(id)) {
            return DataBase.Users.get(id).getPassword().equals(password);
        }
        return false;
    }

    public static void resetPassword(String newPassword) {
        DataBase.Users.get(HMSAppView.getCurrUserID()).setPassword(newPassword);
        System.out.println("Password Reset Successfully");
    }
}
