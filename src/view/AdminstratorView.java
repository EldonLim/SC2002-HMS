package view;

import controller.AdminstratorManager;
import controller.AppointmentManager;
import database.DataBase;
import helper.Helper;
import model.Appointment;
import model.Patient;
import model.Staff;
import model.User;
import using.Gender;
import using.Role;

import java.util.List;

public class AdminstratorView implements View{

    public AdminstratorView() {}

    @Override
    public void printViewMenu() {
        this.viewTitle();
        System.out.println("1. View and Manage Hospital Staff");
        System.out.println("2. View Appointment details");
        System.out.println("3. View and Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Logout");
        System.out.print("Please Enter your Choice: ");
    }

    @Override
    public void handleView() {
        int choice;

        do {
            this.printViewMenu();
            choice = Helper.readInt();

            while (choice < 1 || choice > 5) {
                System.out.println("\nInvalid choice. Please try again.");
                System.out.print("Please Enter your Choice: ");
                choice = Helper.readInt();
            }

            System.out.println();

            switch (choice) {
                case 1: handleViewManageStaff(); break;
                case 2: handleViewAllPatientsAppointment(); break;
                case 3:
                case 4:
                case 5: System.out.println("Thanks for Using HMS");
            }
            Helper.pauseApplication();
        } while (choice != 5);
    }

    @Override
    public void viewTitle() { System.out.println("Adminstrator Menu"); }

    public static void handleViewManageStaff() {
        int choice;
        do {
            System.out.println("VIEW AND MANAGE STAFF");
            System.out.println("1. View Staff");
            System.out.println("2. Manage Staff");
            System.out.println("3. Back");
            System.out.print("Please Enter your Choice: ");
            choice = Helper.readInt();

            while (choice < 1 || choice > 3) {
                System.out.println("Invalid choice. Please try again.");
                System.out.print("Please Enter your Choice: ");
                choice = Helper.readInt();
            }

            System.out.println();

            switch (choice) {
                case 1: handleListStaff(); break;
                case 2: handleManageStaff(); break;
                case 3: break; // Exit loop
            }
        } while (choice != 3);
    }

    public static void handleListStaff() {
        do {
            System.out.println("List Of Staff Filter By: ");
            System.out.println("1. Gender");
            System.out.println("2. Role");
            System.out.println("3. Age Range");
            System.out.println("4. No Filter");
            System.out.println("5. Back");
            System.out.print("Please Enter your Choice: ");
            int choice = Helper.readInt();

            while (choice < 1 || choice > 5) {
                System.out.println("\nInvalid choice. Please try again.");
                System.out.print("Please Enter your Choice: ");
                choice = Helper.readInt();
            }

            switch (choice) {
                case 1: listStaffByGender(); break;
                case 2: listStaffByRole(); break;
                case 3: listStaffByAgeRange(); break;
                case 4: listAllStaff(); break;
                case 5: Helper.pauseApplication(); return;
            }
            Helper.pauseApplication();
        } while (true);
    }

    public static void listStaffByGender() {
        System.out.print("\nPlease Enter Gender (M/F): ");
        char gender = Helper.readChar();
        Gender genderPick = gender == 'm'? Gender.MALE : Gender.FEMALE;

        System.out.println("\nFilter By Gender: " + genderPick.getLabel());
        printStaff(AdminstratorManager.getAllStaffByGender(genderPick));
    }

    public static void listAllStaff() { printStaff(AdminstratorManager.getAllStaff()); }

    public static void printStaff(List<Staff> staffList) {
        System.out.printf("%-10s %-15s %-15s %-5s %-10s\n", "Staff ID", "Name", "Role", "Age", "Gender");
        System.out.println("-------------------------------------------------------");
        for (Staff staff : staffList)
            System.out.printf("%-10s %-15s %-15s %-5d %-10s\n", staff.getID(), staff.getName(), staff.getRole().getLabel(), staff.getAge(), staff.getGender().getLabel());
    }

    public static void listStaffByRole() {
        Role[] roles = {Role.DOCTOR, Role.PHARMACIST, Role.ADMINISTRATOR};
        String[] roleLabels = {"Doctor", "Pharmacist", "Administrator"};

        System.out.println("\nRoles Available:");
        for (int i = 0; i < roles.length; i++)
            System.out.println((i + 1) + ". " + roleLabels[i]);

        System.out.print("Please Enter your Choice (1/2/3): ");
        int choice = Helper.readInt() - 1;

        if (choice >= 0 && choice < roles.length) {
            System.out.println("\nFilter By Role: " + roles[choice].getLabel());
            printStaff(AdminstratorManager.getAllStaffByRole(roles[choice]));
        }
        else System.out.println("Invalid choice. Please try again.");
    }

    public static void listStaffByAgeRange() {
        String[] ageRanges = {"20 - 29", "30 - 39", "40 - 49"};
        int[] age = {20, 30, 40};

        System.out.println("\nAge Range Availables:");
        for (int i = 0; i < ageRanges.length; i++)
            System.out.println((i + 1) + ". " + ageRanges[i]);

        System.out.print("Please Enter your Choice (1/2/3): ");
        int choice = Helper.readInt() - 1;

        if (choice >= 0 && choice < age.length) {
            System.out.println("\nFilter By AgeRange: " + ageRanges[choice]);
            printStaff(AdminstratorManager.getAllStaffByAgeGroup(age[choice]));
        }
        else System.out.println("Invalid choice. Please try again");
    }

    public static void handleManageStaff() {
        int choice;
        do {
            System.out.println("MANAGE STAFF");
            System.out.println("1. Add Staff");
            System.out.println("2. Remove Staff");
            System.out.println("3. Back");
            System.out.print("Please Enter your Choice: ");
            choice = Helper.readInt();

            while (choice < 1 || choice > 3) {
                System.out.println("\nInvalid choice. Please try again.");
                System.out.print("Please Enter your Choice: ");
                choice = Helper.readInt();
            }

            switch (choice) {
                case 1: handleAddStaff(); break;
                case 2: handleRemoveStaff(); break;
                case 3: System.out.println("\nGoing Back to Main Page");
            }
        } while (choice != 3);
        Helper.pauseApplication();
    }

    public static void handleAddStaff() {
        System.out.println("\nADD NEW STAFF");
        Role[] roles = {Role.DOCTOR, Role.PHARMACIST, Role.ADMINISTRATOR};

        System.out.print("Enter Staff Name: ");
        String userName = Helper.readString();

        System.out.print("Enter Staff Role (1 - Doctor, 2 - Pharmacist, 3 - Adminstrator: ");
        int userRole = Helper.readInt();

        System.out.print("Enter Gender (M/F): ");
        char userGender = Helper.readChar();

        System.out.print("Enter Age: ");
        int userAge = Helper.readInt();

        AdminstratorManager.addNewStaff(userName, roles[userRole - 1], userGender == 'm'? Gender.MALE : Gender.FEMALE, userAge);

        System.out.println("Staff Added Successfully");
        Helper.pauseApplication();
    }

    public static void handleRemoveStaff() {
        System.out.println("\nREMOVE STAFF");
        System.out.print("Enter Staff ID: ");
        String staffID = Helper.readString();

        AdminstratorManager.removeStaff(staffID);

        System.out.println("Staff Removed Successfully");
        Helper.pauseApplication();
    }

    public static void handleViewAllPatientsAppointment() {
        System.out.println("VIEW ALL PATIENTS APPOINTMENT");

        DataBase.getUsers().values().stream()
                .filter(user -> user instanceof Patient)
                .map(user -> (Patient) user)
                .forEach(patient -> patient.getAppointments().forEach(appointment ->
                        AppointmentManager.viewAppointmentDetail(appointment, Role.ADMINISTRATOR)
                ));
    }
}
