package view;

import controller.AppointmentManager;
import controller.InventoryManager;
import controller.PatientManager;
import controller.PharmacistManager;
import database.DataBase;
import helper.Helper;
import model.AppointmentOutcome;
import model.Medicine;
import model.Patient;
import model.User;
import using.MedicationStatus;

import java.util.List;
import java.util.Map;

public class PharmacistView implements View{

    public void printViewMenu() {
        System.out.println("1. View Appointment Outcome Record");
        System.out.println("2. Update Prescription Status");
        System.out.println("3. View Medication Inventory");
        System.out.println("4. Submit Replenishment Request");
        System.out.println("5. Logout");
    }

    public void handleView() {
        int choice;

        do {
            this.viewTitle();
            this.printViewMenu();
            System.out.print("Please Enter Your Choice: ");
            choice = Helper.readInt();
            System.out.println();

            switch (choice) {
                case 1:
                    handleDisplayAppointmentOutcome();
                    break;

                case 2:
                    handleUpdatePrescriptionStatus();
                    break;

                case 3:
                    handleViewMedicationInventory();
                    break;

                case 4:
                    handleSubmitReplenishmentRequest();
                    break;

                case 5: System.out.println("Thanks for Using HMS\n");
            }
            Helper.pauseApplication();
        } while (choice != 5);
    }

    public void handleViewMedicationInventory() { PharmacistManager.viewInventory(); }

    public static void handleSubmitReplenishmentRequest() {
        if (submittedRequest())  System.out.println("Replenishment request already submitted");
        else if(!InventoryManager.checkInventoryLowStock()) System.out.println("No Medicine Low In Stock");
        else {
            displayLowStockMeds();
            handleSubmitRequest();
        }
    }

    public static void handleSubmitRequest() {
        char choice;

        do {
            System.out.println("Submit Replenishment Request (y/n):");
            choice = Helper.readChar();

            if (choice != 'y' || choice != 'n')
                System.out.println("Invalid Choice!\nPlease Try Again!!");
        } while (choice != 'y' || choice != 'n');

        if (choice == 'y')
            PharmacistManager.submitRequest();

    }

    public static void displayLowStockMeds() {
        System.out.println("Medicines Low In Stock:");
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
        printAppointmentOutcomesForPatients(false);

        if (!foundAppointOutcome) System.out.println("\nNo Appointment Outcome Record Stored");
    }

    public static void handleUpdatePrescriptionStatus() {
        System.out.println("UPDATE PRESCRIPTION STATUS");
        boolean hasPendingPrescription = printAppointmentOutcomesForPatients(true);

        if (!hasPendingPrescription) {
            System.out.println("No Pending Prescription\n");
            return;
        }

        do {
            System.out.print("\nPlease Enter the AppointmentOutcome ID: ");
            String appointmentOutcomeID = Helper.readString();
            String patientID = appointmentOutcomeID.substring(0, 5);

            if (DataBase.getUsers().containsKey(patientID) &&
                    ((Patient) DataBase.getUsers().get(patientID)).getMedicalRecord().getAppointmentOutcomes().containsKey(appointmentOutcomeID)) {
                AppointmentOutcome outcome = ((Patient) DataBase.getUsers().get(patientID)).getMedicalRecord().getAppointmentOutcomes().get(appointmentOutcomeID);

                if (outcome.getMedicationStatus() == MedicationStatus.PENDING) {
                    outcome.setMedicationStatus(MedicationStatus.DISPENDED);
                    System.out.println("Updated Successfully\n");
                }
                else System.out.println("The medicine for this appointment had dispended\n");
            }
            else System.out.println("This appointment outcome is not recorded in the system\n");


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
                if (!patient.getMedicalRecord().getAppointmentOutcomes().isEmpty()) {
                    foundAppointOutcome = true;
                    System.out.println("Patient Name: " + patient.getName());
                    System.out.println("Patient ID: " + patient.getID());
                    System.out.println("-----------------------------------------");
                    for (AppointmentOutcome outcome : patient.getMedicalRecord().getAppointmentOutcomes().values())
                        if (!onlyPending || outcome.getMedicationStatus() == MedicationStatus.PENDING) {
                            System.out.println("AppointOutcome ID: " + outcome.getAppointmentOutcomeID());
                            AppointmentManager.printAppointmentOutcome(outcome);
                            System.out.println("Prescription Status: " + outcome.getMedicationStatus().getLabel());
                        }

                }
            }

        return foundAppointOutcome;
    }

    public void viewTitle() { System.out.println("Pharmacist Menu"); }
}
