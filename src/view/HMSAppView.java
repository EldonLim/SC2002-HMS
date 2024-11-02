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

    public static void loginView() {
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

        System.out.println("Login Successful");
        Helper.pauseApplication();

        if (DataBase.getUsers().get(DataBase.getCurrUserID()).getPassword().equals("password")) {
            System.out.println("Please reset password for first time login");
            System.out.print("New password: ");
            String password = Helper.readString();

            UserManager.resetPassword(password);
            Helper.pauseApplication();
        }
        handleLogin();
    }

    public static void handleLogin() {
        Role role = DataBase.getUsers().get(DataBase.getCurrUserID()).getRole();

        switch (role) {
            case Role.PATIENT: patientView.handleView(); break;
            case Role.ADMINISTRATOR: adminstratorView.handleView(); break;
            case Role.DOCTOR: doctorView.handleView(); break;
            case Role.PHARMACIST: pharmacistView.handleView(); break;
        }

    }

    public void handleView() {
        int choice;

        do {
            this.printViewMenu();
            System.out.print("Please Enter Your Choice: ");
            choice = Helper.readInt();

            while (choice < 1 || choice > 3) {
                System.out.println("\nInvalid choice. Please try again.");
                System.out.print("Please Enter your Choice: ");
                choice = Helper.readInt();
            }

            Helper.pauseApplication();

            switch (choice) {
                case 1: loginView(); break;
                case 2: registerView(); break;
                case 3: System.out.println("Thanks For Using HMSApp");
            }
        } while (choice != 3);
    }

    public void printViewMenu() {
       System.out.println("1. Login");
       System.out.println("2. Register");
       System.out.println("3. Exit");
    }

    public static void registerView() {
        System.out.println("REGISTRATION PAGE");
        System.out.println("Please Enter the Following Details:");
        System.out.print("Name: ");
        String name = Helper.readString();
        System.out.print("Gender (M/F): ");
        char gender = Helper.readChar();
        System.out.print("Phone No: ");
        String phoneNo = Helper.readString();
        System.out.print("Email Address: ");
        String emailAddress = Helper.readString();
        System.out.print("Date of Birth: ");
        String dateOfBirth = Helper.readString();
        System.out.print("Blood Type (A-/+, B-/+, O-/+, AB-/+): ");
        String bloodType = Helper.readString();

        AdminstratorManager.registerNewPatient(name, phoneNo, emailAddress, BloodType.fromString(bloodType), dateOfBirth, gender == 'm'? Gender.MALE : Gender.FEMALE);

        Helper.pauseApplication();
    }
}
