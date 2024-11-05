package view;

import controller.AppointmentManager;
import controller.InventoryManager;
import controller.PharmacistManager;
import database.DataBase;
import helper.Helper;
import model.AppointmentOutcome;
import model.Medicine;
import model.Patient;
import model.User;
import using.MedicationStatus;

import java.util.Map;

/**
 * Represents the pharmacist's view in the Hospital Management System (HMS).
 * This class implements the {@link View} interface
 * Provides methods for managing inventory, viewing appointment outcomes, and updating prescription statuses.
 * 
 * @author Goh Jun Keat
 * @version 11.6
 * @since 2024-10-27
 */
public class PharmacistView implements View {

    /**
     * Handles the submission of a replenishment request for low-stock medications.
     * Checks if low-stock items exist and if a request has already been submitted.
     * If there are no medicines low in stock or if a request has already been submitted, a message is displayed.
     * Otherwise, the low-stock medicines are displayed, and the user is prompted to submit a request.
     */
    public static void handleSubmitReplenishmentRequest() {
        System.out.println("SUBMIT REPLENISHMENT REQUEST");

        if (!InventoryManager.checkInventoryLowStock()) System.out.println("No Medicine Low In Stock");
        else if (submittedRequest()) System.out.println("Replenishment request already submitted");
        else {
            displayLowStockMeds();
            handleSubmitRequest();
        }
    }

    /**
     * Submits a request to replenish stock based on user confirmation.
     * Prompts the user for confirmation to submit the request.
     */
    public static void handleSubmitRequest() {
        char choice;

        do {
            System.out.print("Submit Replenishment Request (y/n): ");
            choice = Helper.readChar();

            if (choice != 'y' && choice != 'n') {
                System.out.println("Invalid Choice!\nPlease Try Again!!");
                continue;
            } else break;
        } while (true);

        if (choice == 'y') PharmacistManager.submitRequest();
    }

    /**
     * Displays a list of medications that are low in stock.
     */
    public static void displayLowStockMeds() {
        System.out.println("\nMedicines Low In Stock:");
        int count = 1;

        for (Map.Entry<String, Medicine> entry : DataBase.getMedicines().entrySet())
            if (InventoryManager.checkMedicineStockLevel(entry.getKey()))
                System.out.println(count++ + ". " + entry.getValue().getMedicineName());
    }

    /**
     * Checks if a replenishment request has already been submitted for low-stock items.
     *
     * @return true if all low-stock items have a pending request; false otherwise.
     */
    public static boolean submittedRequest() {
        for (Medicine medicine : DataBase.getMedicines().values())
            if (medicine.getLowStockAlert() && !medicine.getRequestAddStock())
                return false;

        return true;
    }

    /**
     * Displays appointment outcomes for patients.
     * If no outcomes are stored, notifies the user.
     */
    public static void handleDisplayAppointmentOutcome() {
        System.out.println("DISPLAY APPOINTMENT OUTCOME");

        boolean foundAppointOutcome = printAppointmentOutcomesForPatients(false);

        if (!foundAppointOutcome) System.out.println("No Appointment Outcome Record Stored");
    }

    /**
     * Updates the prescription status of appointments with pending prescriptions.
     * Allows the pharmacist to input an appointment outcome ID for updating.
     */
    public static void handleUpdatePrescriptionStatus() {
        System.out.println("UPDATE PRESCRIPTION STATUS");
        boolean hasPendingPrescription = printAppointmentOutcomesForPatients(true);

        if (!hasPendingPrescription) {
            System.out.println("No Pending Prescription\n");
            return;
        }

        do {
            System.out.print("Please Enter the AppointmentOutcome ID: ");
            String appointmentOutcomeID = Helper.readString();
            String patientID = appointmentOutcomeID.substring(0, 5);

            PharmacistManager.updatePrescriptionStatus(appointmentOutcomeID, patientID);

            System.out.print("Update other appointment outcome prescription status (y/n)?: ");
            char choice = Helper.readChar();

            if (choice != 'y') break;

        } while (true);
    }

    /**
     * Prints appointment outcomes for patients.
     *
     * @param onlyPending if true, displays only pending appointments.
     * @return true if appointment outcomes are found; false otherwise.
     */
    private static boolean printAppointmentOutcomesForPatients(boolean onlyPending) {
        boolean foundAppointOutcome = false;

        for (User user : DataBase.getUsers().values())
            if (user instanceof Patient patient) {
                if (!patient.getMedicalRecord().getAppointmentOutcomes().isEmpty())
                    for (AppointmentOutcome outcome : patient.getMedicalRecord().getAppointmentOutcomes().values())
                        if (!onlyPending || outcome.getMedicationStatus() == MedicationStatus.PENDING) {
                            foundAppointOutcome = true;
                            System.out.println("Patient Name: " + patient.getName());
                            System.out.println("Patient ID: " + patient.getID());
                            System.out.println("-----------------------------------------");
                            System.out.println("AppointOutcome ID: " + outcome.getAppointmentOutcomeID());
                            AppointmentManager.printAppointmentOutcome(outcome);
                            System.out.println("Prescription Status: " + outcome.getMedicationStatus().getLabel());
                        }
            }
        return foundAppointOutcome;
    }

    /**
     * Prints the pharmacist's menu options.
     */
    public void printViewMenu() {
        System.out.println("""
                1. Display Appointment Outcome
                2. Update Prescription Status
                3. View Medication Inventory
                4. Submit Replenishment Request
                5. Logout """);
    }

    /**
     * Handles user input and interactions for the pharmacist view.
     * Prompts the user to select an option and processes it accordingly.
     */
    public void handleView() {
        int choice;

        do {
            this.viewTitle();
            this.printViewMenu();
            System.out.print("Please Enter Your Choice: ");
            choice = Helper.readInt();

            while (choice < 1 || choice > 5) {
                System.out.println("\nInvalid choice. Please try again.");
                System.out.print("Please Enter your Choice: ");
                choice = Helper.readInt();
            }

            Helper.pauseApplication();

            switch (choice) {
                case 1 -> handleDisplayAppointmentOutcome();
                case 2 -> handleUpdatePrescriptionStatus();
                case 3 -> handleViewMedicationInventory();
                case 4 -> handleSubmitReplenishmentRequest();
                case 5 -> System.out.println("Thanks for Using HMS");
            }
            Helper.pauseApplication();
        } while (choice != 5);
    }

    /**
     * Displays the medication inventory to the user.
     */
    public void handleViewMedicationInventory() {
        System.out.println("MEDICAL INVENTORY");
        PharmacistManager.viewInventory();
    }

    /**
     * Prints the title of the pharmacist view.
     */
    public void viewTitle() {
        System.out.println("Pharmacist Menu");
    }
}
