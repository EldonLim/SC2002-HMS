package view;

import database.DataBase;
import helper.Helper;
import controller.*;
import using.*;

public class HMSAppView implements View{

    protected static PatientView patientView;
    private static String currUserID;

    public HMSAppView() {
        patientView = new PatientView();
        this.viewTitle();
        this.handleView();
    }

    public void viewTitle() {
        System.out.println("========================================");
        System.out.println("||                                    ||");
        System.out.println("||  HOSPITAL MANAGEMENT SYSTEM (HMS)  ||");
        System.out.println("||                                    ||");
        System.out.println("========================================");
    }

    public void loginView() {
        boolean validate = false;

        while (!validate) {
            System.out.println("Login Page");
            System.out.print("ID: ");
            currUserID = Helper.readString();
            System.out.print("Password: ");
            String password = Helper.readString();

            validate = UserManager.validateUser(currUserID, password);

            if (!validate) {
                System.out.println("Invalid Username or Password");
                System.out.println("Please try again");
                Helper.pauseApplication();
            }

        }

        System.out.println("Login Successful");
        Helper.pauseApplication();

        this.handleLogin();

    }

    public void handleLogin() {
        Role role = DataBase.Users.get(currUserID).getRole();

        switch (role) {
            case Role.PATIENT:
                patientView.handleView();
                break;

            case Role.ADMINISTRATOR:
                break;

            case Role.DOCTOR:
                break;

            case Role.PHARMACIST:
                break;
        }

    }

    public void handleView() {
        int choice;

        do {
            this.printViewMenu();
            choice = Helper.readInt();
            System.out.println();

            switch (choice) {
                case 1:
                    this.loginView();
                    break;
                case 2:

            }
        } while (choice != 3);
    }
    public void printViewMenu() {
       System.out.println("1. Login");
       System.out.println("2. Register");
       System.out.println("3. Exit");
    }

    public static String getCurrUserID() {
        return currUserID;
    }
}
