package view;

import controller.AdminstratorManager;
import helper.Helper;
import model.Staff;
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
            System.out.println();

            switch (choice) {
                case 1: handleViewManageStaff();
                case 2:
                case 3:
                case 4:
                case 5: System.out.println("Thanks for Using HMS\n");
            }
        } while (choice != 5);
    }

    @Override
    public void viewTitle() { System.out.println("Adminstrator Menu"); }

    public static void handleViewManageStaff() {
        System.out.println("VIEW AND MANAGE STAFF\n");
        handleListStaff();
        handleManageStaff();
    }

    public static void handleListStaff() {
        do {
            System.out.println("List Of Staff Filter By: ");
            System.out.println("1. Gender");
            System.out.println("2. Role");
            System.out.println("3. Age Range");
            System.out.println("4. No Filter");
            System.out.print("Please Enter your Choice: ");
            int choice = Helper.readInt();

            switch (choice) {
                case 1: listStaffByGender();
                        break;

                case 2: listStaffByRole();
                        break;

                case 3: listStaffByAgeRange();
                        break;

                case 4: listAllStaff();
            }

            System.out.print("View Again (y/n): ");
            char repeat = Helper.readChar();
            System.out.println();

            if (repeat == 'n') break;
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
        System.out.printf("%-10s %-15s %-20s %-5s %-10s\n", "Staff ID", "Name", "Role", "Age", "Gender");
        System.out.println("--------------------------------------------------");
        for (Staff staff : staffList)
            System.out.printf("%-10s %-15s %-20s %-5d %-10s\n", staff.getID(), staff.getName(), staff.getRole().getLabel(), staff.getAge(), staff.getGender().getLabel());
        System.out.println();
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

        System.out.println("Age Range Availables:");
        for (int i = 0; i < ageRanges.length; i++)
            System.out.println((i + 1) + ". " + ageRanges[i]);

        System.out.print("Please Enter your Choice (1/2/3): ");
        int choice = Helper.readInt() - 1;

        if (choice >= 0 && choice < age.length) {
            System.out.println("Filter By AgeRange: " + ageRanges[choice]);
            printStaff(AdminstratorManager.getAllStaffByAgeGroup(age[choice]));
        }
        else System.out.println("Invalid choice. Please try again");
    }

    public static void handleManageStaff() {
    }
}
