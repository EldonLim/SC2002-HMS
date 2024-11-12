package view;

import controller.AdministratorManager;
import controller.AppointmentManager;
import controller.InventoryManager;
import database.DataBase;
import helper.Helper;
import model.Medicine;
import model.Patient;
import model.Staff;
import using.Gender;
import using.Role;

import java.util.List;

/**
 * Represents the administrator's view in the Hospital Management System (HMS).
 * This class implements {@link View} interface.
 * Provides methods for managing hospital staff, appointments, and inventory.
 *
 * @author Lean Yi Fan
 * @version 7.7
 * @since 2024-10-27
 */
public class AdminstratorView implements View {

    /**
     * Constructs an instance of AdministratorView
     */
    public AdminstratorView() {
    }

    /**
     * Handles the viewing and managing of hospital staff.
     * Displays options to view staff, manage staff, or go back.
     */
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

            Helper.pauseApplication();

            switch (choice) {
                case 1 -> handleListStaff();
                case 2 -> handleManageStaff();
            }
        } while (choice != 3);
    }

    /**
     * Handles the listing of staff with filtering options.
     * Prompts the user to filter staff by gender, role, age range, or view all.
     */
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

            Helper.pauseApplication();

            switch (choice) {
                case 1 -> listStaffByGender();
                case 2 -> listStaffByRole();
                case 3 -> listStaffByAgeRange();
                case 4 -> listAllStaff();
                case 5 -> {
                    return;
                }
            }
        } while (true);
    }

    /**
     * Lists staff filtered by gender.
     * Prompts the user to input a gender and displays the corresponding staff members.
     */
    public static void listStaffByGender() {
        System.out.print("Please Enter Gender (M/F): ");
        char gender = Helper.readChar();
        Gender genderPick = gender == 'm' ? Gender.MALE : Gender.FEMALE;

        System.out.println("\nFilter By Gender: " + genderPick.getLabel());
        printStaff(AdministratorManager.getAllStaffByGender(genderPick));
    }

    /**
     * Lists all staff members without any filters.
     */
    public static void listAllStaff() {
        printStaff(AdministratorManager.getAllStaff());
    }

    /**
     * Prints the details of the given staff list in a formatted manner.
     *
     * @param staffList List of staff members to be printed.
     */
    public static void printStaff(List<Staff> staffList) {
        System.out.printf("%-10s %-15s %-15s %-5s %-10s\n", "Staff ID", "Name", "Role", "Age", "Gender");
        System.out.println("-------------------------------------------------------");
        for (Staff staff : staffList)
            System.out.printf("%-10s %-15s %-15s %-5d %-10s\n", staff.getID(), staff.getName(), staff.getRole().getLabel(), staff.getAge(), staff.getGender().getLabel());
        System.out.println();
    }

    /**
     * Lists staff filtered by their role.
     * Displays available roles and shows staff members belonging to the selected role.
     */
    public static void listStaffByRole() {
        Role[] roles = {Role.DOCTOR, Role.PHARMACIST, Role.ADMINISTRATOR};
        String[] roleLabels = {"Doctor", "Pharmacist", "Administrator"};

        System.out.println("\nRoles Available:");
        for (int i = 0; i < roles.length; i++)
            System.out.println((i + 1) + ". " + roleLabels[i]);

        System.out.print("Please Enter your Choice (1/2/3): ");
        int choice = Helper.readInt() - 1;

        if (choice >= 0 && choice < roles.length) {
            System.out.println("Filter By Role: " + roles[choice].getLabel());
            printStaff(AdministratorManager.getAllStaffByRole(roles[choice]));
        } else System.out.println("Invalid choice. Please try again.");
    }

    /**
     * Lists staff filtered by their age range.
     * Prompts the user to select an age range and displays corresponding staff members.
     */
    public static void listStaffByAgeRange() {
        String[] ageRanges = {"20 - 29", "30 - 39", "40 - 49"};
        int[] age = {20, 30, 40};

        System.out.println("Age Range Availables:");
        for (int i = 0; i < ageRanges.length; i++)
            System.out.println((i + 1) + ". " + ageRanges[i]);

        System.out.print("Please Enter your Choice (1/2/3): ");
        int choice = Helper.readInt() - 1;

        if (choice >= 0 && choice < age.length) {
            System.out.println("\nFilter By AgeRange: " + ageRanges[choice]);
            printStaff(AdministratorManager.getAllStaffByAgeGroup(age[choice]));
        } else System.out.println("Invalid choice. Please try again");
    }

    /**
     * Handles the management of hospital staff, including adding and removing staff members.
     */
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

            Helper.pauseApplication();

            switch (choice) {
                case 1 -> handleAddStaff();
                case 2 -> handleRemoveStaff();
            }
        } while (choice != 3);
    }

    /**
     * Prompts the user to add a new staff member to the system.
     * Collects and validates the staff member's name, role, gender, and age, and calls the manager to add the staff.
     */
    public static void handleAddStaff() {
        System.out.println("\nADD NEW STAFF");
        Role[] roles = {Role.DOCTOR, Role.PHARMACIST, Role.ADMINISTRATOR};

        System.out.print("Enter Staff Name: ");
        String userName = Helper.readString();

        int userRole;
        while (true) {
            System.out.print("Enter Staff Role (1 - Doctor, 2 - Pharmacist, 3 - Administrator): ");
            userRole = Helper.readInt();
            if (userRole >= 1 && userRole <= 3)
                break;

            System.out.println("Invalid role. Please enter a number between 1 and 3.");
        }

        char userGender;
        while (true) {
            System.out.print("Enter Gender (M/F): ");
            userGender = Helper.readChar();
            if (userGender == 'm' || userGender == 'f')
                break;

            System.out.println("Invalid gender. Please enter M or F.");
        }

        System.out.print("Enter Age: ");
        int userAge = Helper.readInt();

        AdministratorManager.addNewStaff(userName, roles[userRole - 1], userGender == 'm' ? Gender.MALE : Gender.FEMALE, userAge);

        System.out.println("Staff Added Successfully");
        Helper.pauseApplication();
    }

    /**
     * Prompts the user to remove a staff member from the system.
     * The user must enter the staff ID of the staff member for validation before removal.
     */
    public static void handleRemoveStaff() {
        System.out.println("\nREMOVE STAFF");

        String staffID;
        while (true) {
            System.out.print("Enter Staff ID: ");
            staffID = Helper.readString();

            if (DataBase.getUsers().containsKey(staffID))
                break;

            System.out.println("No such staff with this staff ID");
        }

        AdministratorManager.removeStaff(staffID);

        System.out.println("Staff Removed Successfully");
        Helper.pauseApplication();
    }

    /**
     * Displays all appointments for all patients in the system.
     * If no appointments are found, a message is displayed to indicate this.
     */
    public static void handleViewAllPatientsAppointment() {
        System.out.println("VIEW ALL PATIENTS APPOINTMENT");

        boolean hasAppointments = DataBase.getUsers().values().stream()
                .filter(user -> user instanceof Patient)
                .map(user -> (Patient) user)
                .flatMap(patient -> patient.getAppointments().stream())
                .findFirst()
                .isPresent();

        if (hasAppointments) {
            DataBase.getUsers().values().stream()
                    .filter(user -> user instanceof Patient)
                    .map(user -> (Patient) user)
                    .forEach(patient -> patient.getAppointments().forEach(appointment ->
                            AppointmentManager.viewAppointmentDetail(appointment, Role.ADMINISTRATOR)
                    ));
        } else System.out.println("No appointments found.");
    }

    /**
     * Handles viewing and managing the medication inventory.
     * Displays options for viewing the inventory, managing the inventory, or going back.
     */
    public static void handleViewManageMedicationInventory() {
        int choice;
        do {
            System.out.println("\nVIEW AND MANAGE MEDICATION INVENTORY");
            System.out.println("1. View Inventory");
            System.out.println("2. Manage Inventory");
            System.out.println("3. Back");
            System.out.print("Please Enter your Choice: ");
            choice = Helper.readInt();

            while (choice < 1 || choice > 3) {
                System.out.println("Invalid choice. Please try again.");
                System.out.print("Please Enter your Choice: ");
                choice = Helper.readInt();
            }

            Helper.pauseApplication();

            switch (choice) {
                case 1 -> InventoryManager.listInventory();
                case 2 -> handleManageInventory();
            }
        } while (choice != 3);
    }

    /**
     * Manages the inventory of medications, allowing the user to add stock, remove stock,
     * update low stock alerts, or go back to the previous menu.
     */
    public static void handleManageInventory() {
        do {
            System.out.println("Manage Inventory");
            System.out.println("1. Add Stock");
            System.out.println("2. Remove Stock");
            System.out.println("3. Update Low Stock Alert");
            System.out.println("4. Back");
            System.out.print("Please Enter your Choice: ");
            int choice = Helper.readInt();

            if (choice == 4) {
                Helper.pauseApplication();
                return;
            }

            while (choice < 1 || choice > 4) {
                System.out.println("\nInvalid choice. Please try again.");
                System.out.print("Please Enter your Choice: ");
                choice = Helper.readInt();
            }

            String medicineName;
            do {
                System.out.print("Enter Medicine Name: ");
                medicineName = Helper.readString();

                if (DataBase.getMedicines().containsKey(medicineName)) break;

                System.out.println("\nInvalid Medicine Name. Please try again.");
            } while (true);

            switch (choice) {
                case 1 -> InventoryManager.updateStock(medicineName, true);
                case 2 -> InventoryManager.updateStock(medicineName, false);
                case 3 -> AdministratorManager.updateLowStockAlert(medicineName);
            }
            Helper.pauseApplication();
        } while (true);
    }

    /**
     * Handles the approval of replenishment requests for medicines that are low in stock.
     * Displays a list of medicines with low stock alerts and prompts the user to approve
     * or deny each request.
     */
    public static void handleApproveReplenishmentRequests() {
        System.out.println("APPROVE REPLENISHMENT REQUEST");
        List<Medicine> medicinesLowStock = InventoryManager.getAllMedicineWithLowStockAlert();

        if (medicinesLowStock.isEmpty()) {
            InventoryManager.listInventory();
            System.out.println("\nNo Request Submitted");
            return;
        }

        for (Medicine medicine : medicinesLowStock) {
            System.out.println("Medicine: " + medicine.getMedicineName());
            System.out.println("Stock: " + medicine.getStock());
            System.out.print("Approve Replenishment Request (Y/N): ");
            char choice = Helper.readChar();

            while (choice != 'y' && choice != 'n') {
                System.out.println("Invalid Input. Please Key in Again.");
                System.out.print("Approve Replenishment Request (Y/N): ");
                choice = Helper.readChar();
            }

            if (choice == 'y') {
                InventoryManager.handleApproveReplenishmentRequest(medicine);
                InventoryManager.listInventory();
                Helper.pauseApplication();
            }
        }
    }

    /**
     * Prompts the user to enter details for a new medicine and adds it to the inventory.
     * Collects the medicine's name, quantity, and low stock alert threshold.
     */
    public static void handleAddNewMedicine() {
        System.out.println("ADDING NEW MEDICINE");
        System.out.println("Please Enter the following Details");
        System.out.print("Medicine Name: ");
        String medicineName = Helper.readString();
        System.out.print("Quantity: ");
        int quantity = Helper.readInt();
        System.out.print("Low Stock Alert: ");
        int lowStockAlertQuantity = Helper.readInt();

        InventoryManager.addNewMedicine(medicineName, quantity, lowStockAlertQuantity);
        Helper.pauseApplication();
    }

    /**
     * Prints the view menu for the administrator, displaying options for managing staff,
     * appointments, inventory, and other functionalities.
     */
    @Override
    public void printViewMenu() {
        this.viewTitle();
        System.out.println("1. View and Manage Hospital Staff");
        System.out.println("2. View Appointment details");
        System.out.println("3. View and Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Add New Medicine");
        System.out.println("6. Logout");
        System.out.print("Please Enter your Choice: ");
    }

    /**
     * Handles the view of the administrator menu, allowing the user to select different
     * management options until they choose to log out.
     */
    @Override
    public void handleView() {
        int choice;

        do {
            this.printViewMenu();
            choice = Helper.readInt();

            while (choice < 1 || choice > 6) {
                System.out.println("\nInvalid choice. Please try again.");
                System.out.print("Please Enter your Choice: ");
                choice = Helper.readInt();
            }

            Helper.pauseApplication();

            switch (choice) {
                case 1 -> handleViewManageStaff();
                case 2 -> handleViewAllPatientsAppointment();
                case 3 -> handleViewManageMedicationInventory();
                case 4 -> handleApproveReplenishmentRequests();
                case 5 -> handleAddNewMedicine();
                case 6 -> System.out.println("Thanks for Using HMS");
            }
        } while (choice != 6);
    }

    /**
     * Displays the title of the administrator menu.
     */
    @Override
    public void viewTitle() {
        System.out.println("Adminstrator Menu");
    }
}