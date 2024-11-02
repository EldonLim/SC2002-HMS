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

public class PharmacistView implements View{

    public void printViewMenu() {
        System.out.println("""
                1. Display Appointment Outcome
                2. Update Prescription Status
                3. View Medication Inventory
                4. Submit Replenishment Request
                5. Logout """);
    }

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

    public void handleViewMedicationInventory() {
        System.out.println("MEDICAL INVENTORY");
        PharmacistManager.viewInventory();
    }

    public static void handleSubmitReplenishmentRequest() {
        System.out.println("SUBMIT REPLENISHMENT REQUEST");

        if (!InventoryManager.checkInventoryLowStock()) System.out.println("No Medicine Low In Stock");
        else if (submittedRequest()) System.out.println("Replenishment request already submitted");
        else {
            displayLowStockMeds();
            handleSubmitRequest();
        }
    }

    public static void handleSubmitRequest() {
        char choice;

        do {
            System.out.print("Submit Replenishment Request (y/n): ");
            choice = Helper.readChar();

            if (choice != 'y' && choice != 'n') {
                System.out.println("Invalid Choice!\nPlease Try Again!!");
                continue;
            }
            else break;
        } while (true);

        if (choice == 'y') PharmacistManager.submitRequest();
    }

    public static void displayLowStockMeds() {
        System.out.println("\nMedicines Low In Stock:");
        int count = 1;

        for (Map.Entry<String, Medicine> entry : DataBase.getMedicines().entrySet())
            if (InventoryManager.checkMedicineStockLevel(entry.getKey()))
                System.out.println(count++ + ". " + entry.getValue().getMedicineName());
    }

    public static boolean submittedRequest() {
        for (Medicine medicine : DataBase.getMedicines().values())
            if (medicine.getLowStockAlert() && !medicine.getRequestAddStock())
                return false;

        return true;
    }

    public static void handleDisplayAppointmentOutcome() {
        System.out.println("DISPLAY APPOINTMENT OUTCOME");

        boolean foundAppointOutcome = false;
        foundAppointOutcome = printAppointmentOutcomesForPatients(false);

        if (!foundAppointOutcome) System.out.println("No Appointment Outcome Record Stored");
    }

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

    private static boolean printAppointmentOutcomesForPatients(boolean onlyPending) {
        boolean foundAppointOutcome = false;

        for (User user : DataBase.getUsers().values())
            if (user instanceof Patient) {
                Patient patient = (Patient) user;
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

    public void viewTitle() { System.out.println("Pharmacist Menu"); }
}
