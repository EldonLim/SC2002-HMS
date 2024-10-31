package view;

import database.DataBase;
import helper.Helper;
import controller.*;
import using.*;

import javax.xml.crypto.Data;

public class HMSAppView implements View{

    protected static PatientView patientView;
    protected static AdminstratorView adminstratorView;
    protected static DoctorView doctorView;
    protected static PharmacistView pharmacistView;

    public HMSAppView() {
        patientView = new PatientView();
        adminstratorView = new AdminstratorView();
        doctorView = new DoctorView();
        pharmacistView = new PharmacistView();
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
            System.out.println("Login Page (The password for first time user is \"password\")");
            System.out.print("ID: ");
            DataBase.setCurrUserID(Helper.readString());
            System.out.print("Password: ");
            String password = Helper.readString();

            validate = UserManager.validateUser(DataBase.getCurrUserID(), password);

            if (!validate) {
                System.out.println("Invalid Username or Password");
                System.out.println("Please try again");
                Helper.pauseApplication();
            }

        }

        System.out.println();
        System.out.println("Login Successful");
        Helper.pauseApplication();

        if (DataBase.getUsers().get(DataBase.getCurrUserID()).getPassword().equals("password")) {
            System.out.println("Please reset password for first time login");
            System.out.print("New password: ");
            String password = Helper.readString();

            UserManager.resetPassword(password);
            System.out.println();
            Helper.pauseApplication();
        }

        this.handleLogin();

    }

    public void handleLogin() {
        Role role = DataBase.getUsers().get(DataBase.getCurrUserID()).getRole();

        switch (role) {
            case Role.PATIENT:
                patientView.handleView();
                break;

            case Role.ADMINISTRATOR:
                adminstratorView.handleView();
                break;

            case Role.DOCTOR:
                doctorView.handleView();
                break;

            case Role.PHARMACIST:
                pharmacistView.handleView();
                break;
        }

    }

    public void handleView() {
        int choice;

        do {
            this.printViewMenu();
            System.out.print("Please Enter Your Choice: ");
            choice = Helper.readInt();
            System.out.println();

            switch (choice) {
                case 1:
                    this.loginView();
                    break;
                case 2:
                case 3:
                    System.out.println("Thanks For Using HMSApp");
            }
        } while (choice != 3);
    }
    public void printViewMenu() {
       System.out.println("1. Login");
       System.out.println("2. Register");
       System.out.println("3. Exit");
    }
}
