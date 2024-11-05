package view;

import controller.AdminstratorManager;
import controller.UserManager;
import database.DataBase;
import helper.Encryption;
import helper.Helper;
import using.BloodType;
import using.Gender;
import using.Role;

public class HMSAppView implements View {

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

    public static void loginView() {
        boolean isValid = false;
        while (!isValid) {
            System.out.println("Login Page (Default password: \"password\")");
            System.out.print("ID: ");
            DataBase.setCurrentUserID(Helper.readString());
            System.out.print("Password: ");
            String password = Helper.readString();
            isValid = UserManager.validateUser(DataBase.getCurrentUserID(), password);
            if (!isValid) {
                System.out.println("Invalid username or password. Please try again.");
                Helper.pauseApplication();
            }
        }
        Helper.pauseApplication();
        if (DataBase.getUsers().get(DataBase.getCurrentUserID()).getPassword().equals(Encryption.encode("password")))
            resetPassword();
        System.out.println("Login Successful");
        Helper.pauseApplication();
        handleLogin();
    }

    private static void resetPassword() {
        System.out.println("Please reset password for first-time login");
        System.out.print("New password: ");
        String password = Helper.readString();
        UserManager.resetPassword(password);
        Helper.pauseApplication();
    }

    public static void handleLogin() {
        Role role = DataBase.getUsers().get(DataBase.getCurrentUserID()).getRole();
        switch (role) {
            case PATIENT -> patientView.handleView();
            case ADMINISTRATOR -> adminstratorView.handleView();
            case DOCTOR -> doctorView.handleView();
            case PHARMACIST -> pharmacistView.handleView();
        }
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

        AdminstratorManager.registerNewPatient(name, phoneNo, emailAddress, BloodType.fromString(bloodType), dateOfBirth, gender == 'm' ? Gender.MALE : Gender.FEMALE);

        Helper.pauseApplication();
    }

    public void viewTitle() {
        System.out.println("========================================");
        System.out.println("||                                    ||");
        System.out.println("||  HOSPITAL MANAGEMENT SYSTEM (HMS)  ||");
        System.out.println("||                                    ||");
        System.out.println("========================================");
    }

    public void handleView() {
        int choice;
        do {
            printViewMenu();
            System.out.print("Enter your choice: ");
            choice = Helper.readInt();
            while (choice < 1 || choice > 3) {
                System.out.println("Invalid choice. Please try again.");
                System.out.print("Enter your choice: ");
                choice = Helper.readInt();
            }
            Helper.pauseApplication();
            switch (choice) {
                case 1 -> loginView();
                case 2 -> registerView();
                case 3 -> {
                    System.out.println("Thanks for using HMSApp");
                    DataBase.writeData();
                }
            }
        } while (choice != 3);
    }

    public void printViewMenu() {
        System.out.println("""
                1. Login
                2. Register
                3. Exit """);
    }
}
