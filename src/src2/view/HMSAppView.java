package src2.view;

import src2.controller.AdminstratorManager;
import src2.controller.UserManager;
import src2.database.DataBase;
import src2.helper.Encryption;
import src2.helper.Helper;
import src2.using.BloodType;
import src2.using.Gender;
import src2.using.Role;

/**
 * Represents the main view for the Hospital Management System (HMS) application.
 * This class implements {@link View} interface.
 * Provides methods for logging in, registering new users, and handling user-specific views based on roles.
 * 
 * @author Eldon Lim Kai Jie
 * @version 14.7
 * @since 2024-10-27
 */
public class HMSAppView implements View {

    /**
     * Instance of PatientView for handling patient-related operations.
     */
    protected static PatientView patientView;

    /**
     * Instance of AdminstratorView for handling administrator-related operations.
     */
    protected static AdminstratorView adminstratorView;

    /**
     * Instance of DoctorView for handling doctor-related operations.
     */
    protected static DoctorView doctorView;

    /**
     * Instance of PharmacistView for handling pharmacist-related operations.
     */
    protected static PharmacistView pharmacistView;

    /**
     * Constructs the HMSAppView instance and initializes all role-specific views.
     * Displays the application title and main menu.
     */
    public HMSAppView() {
        patientView = new PatientView();
        adminstratorView = new AdminstratorView();
        doctorView = new DoctorView();
        pharmacistView = new PharmacistView();
        this.viewTitle();
        this.handleView();
    }

    /**
     * Displays the login view, prompting the user for credentials.
     * Validates the credentials and directs the user to a password reset if it's their first login.
     */
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
        if (DataBase.getUsers().get(DataBase.getCurrentUserID()).getPassword().equals(Encryption.encode("password"))) {
            resetPassword();
            return;
        }
        System.out.println("Login Successful");
        Helper.pauseApplication();
        handleLogin();
    }

    /**
     * Prompts the user to reset their password upon first-time login.
     * After changing password, prompts user to login again with new password.
     */
    private static void resetPassword() {
        System.out.println("Please reset password for first-time login");
        System.out.print("New password: ");
        String password = Helper.readString();
        UserManager.resetPassword(password);
        System.out.println("Please Login Again With the New Password");
        Helper.pauseApplication();
    }

    /**
     * Directs the user to their respective role-specific view based on their role.
     */
    public static void handleLogin() {
        Role role = DataBase.getUsers().get(DataBase.getCurrentUserID()).getRole();
        switch (role) {
            case PATIENT -> patientView.handleView();
            case ADMINISTRATOR -> adminstratorView.handleView();
            case DOCTOR -> doctorView.handleView();
            case PHARMACIST -> pharmacistView.handleView();
        }
    }

    /**
     * Displays the registration view, prompting the user for their personal information.
     * Registers a new patient in the system.
     */
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

    /**
     * Prints the title for the HMS application.
     */
    public void viewTitle() {
        System.out.println("========================================");
        System.out.println("||                                    ||");
        System.out.println("||  HOSPITAL MANAGEMENT SYSTEM (HMS)  ||");
        System.out.println("||                                    ||");
        System.out.println("========================================");
    }

    /**
     * Handles the main view menu, allowing the user to choose between login, registration, and exit.
     * Executes the chosen option and handles invalid input.
     */
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

    /**
     * Prints the main menu options for the HMS application.
     */
    public void printViewMenu() {
        System.out.println("""
                1. Login
                2. Register
                3. Exit""");
    }
}
