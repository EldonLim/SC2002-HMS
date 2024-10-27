package view;

import helper.Helper;
import controller.*;

public class HMSAppView implements View{

    public HMSAppView() {}

    public void loginView() {
        viewTitle();
        boolean validate = false;

        while (!validate) {
            System.out.print("Username: ");
            String username = Helper.readString();
            System.out.print("Password: ");
            String password = Helper.readString();

            validate = UserManager.validateUser(username, password);

            if (!validate) {
                System.out.println("Invalid Username or Password");
                System.out.println("Please try again");
                Helper.pauseApplication();
            }

        }

        System.out.println("Login Successful");
        Helper.pauseApplication();
    }

    public void viewTitle() {
        System.out.println("LOGIN PAGE");
    }
}
