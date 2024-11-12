package src2.controller;

import src2.database.DataBase;
import src2.helper.Encryption;

/**
 * Manages user-related operations such as validating users and resetting passwords.
 * Provides methods to validate a user's credentials and reset a user's password.
 * 
 * @author Chin Linn Sheng
 * @version 2.4
 * @since 2024-10-27
 */
public class UserManager {

    /**
     * Default constructor for the UserManager class.
     */
    public UserManager() {
    }

    /**
     * Validates a user's credentials by checking their user ID and password.
     * The password is encoded before comparison.
     *
     * @param id       the user ID to validate
     * @param password the password to validate
     * @return true if the user ID exists and the password is correct, false otherwise
     */
    public static boolean validateUser(String id, String password) {
        if (DataBase.getUsers().containsKey(id))
            return DataBase.getUsers().get(id).getPassword().equals(Encryption.encode(password));
        return false;
    }

    /**
     * Resets the password for the current user.
     * The new password is encoded before being saved.
     *
     * @param newPassword the new password to set for the current user
     */
    public static void resetPassword(String newPassword) {
        DataBase.getUsers().get(DataBase.getCurrentUserID()).setPassword(Encryption.encode(newPassword));
        System.out.println("Password Reset Successfully");
    }
}
