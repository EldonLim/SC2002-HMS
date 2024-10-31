package view;

import database.DataBase;
import helper.Helper;
import model.Doctor;
import model.Patient;
import controller.*;
import using.*;


public class HMSAppView implements View{

    protected static PatientView patientView;
    protected static AdminstratorView adminstratorView;
    protected static DoctorView doctorView;
    protected static PharmacistView pharmacistView;

    //protected String currUserID;
    private static String currUserID;

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
            currUserID = Helper.readString();
            System.out.print("Password: ");
            String password = Helper.readString();

            validate = UserManager.validateUser(currUserID, password);

            if (!validate) {
                System.out.println("Invalid Username or Password");
                System.out.println("Please try again");
                Helper.pauseApplication();
                Helper.readString();  // clear input buffer
            }

        }

        System.out.println("Login Successful");
        Helper.pauseApplication();

        if (DataBase.Users.get(currUserID).getPassword().equals("password")) {
            System.out.println("Please reset password for first time login");
            System.out.print("New password: ");
            String password = Helper.readString();

            UserManager.resetPassword(password);
        }

        this.handleLogin();

    }

    public void handleLogin() {
        Role role = DataBase.Users.get(currUserID).getRole();

        switch (role) {
            case Role.PATIENT:
                patientView.handleView((Patient) DataBase.Users.get(currUserID));
                break;

            case Role.ADMINISTRATOR:
                adminstratorView.handleView();
                break;

            case Role.DOCTOR:
                doctorView.handleView((Doctor) DataBase.Users.get(currUserID));
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
            choice = Helper.readInt();
            System.out.println();

            switch (choice) {
                case 1:
                    this.loginView();
                    break;
                case 2:
                    break;
                case 3:
                    System.out.println("Thanks For Using HMSApp");
                    break;
            }
        } while (choice != 3);
    }
    public void printViewMenu() {
       System.out.println("1. Login");
       System.out.println("2. Register");
       System.out.println("3. Exit");
    }

    public static String getCurrUserID() { return currUserID; }
}
