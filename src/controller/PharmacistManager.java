package controller;

import database.DataBase;
import model.AppointmentOutcome;
import model.Medicine;
import model.Patient;
import using.MedicationStatus;

/**
 * Manages pharmacist-related operations, including viewing inventory, submitting replenishment requests,
 * and updating prescription statuses.
 *
 * @author Goh Jun Keat
 * @version 4.3
 * @since 2024-10-29
 */
public class PharmacistManager {

    /**
     * Default constructor for the PharmacistManager class.
     */
    public PharmacistManager() {
    }

    /**
     * Displays the list of medicines in the inventory. If the inventory is empty, a message is displayed.
     */
    public static void viewInventory() {
        if (DataBase.getMedicines().isEmpty()) {
            System.out.println("No medicines record in the inventory");
            return;
        }
        InventoryManager.listInventory();
    }

    /**
     * Submits a replenishment request for medicines that are low in stock and do not already have a pending request.
     * Prints a message for each medicine for which a replenishment request is submitted.
     */
    public static void submitRequest() {
        System.out.println("\nReplenishment Request Sent For:");
        for (Medicine medicine : DataBase.getMedicines().values())
            if (medicine.getLowStockAlert() && !medicine.getRequestAddStock()) {
                System.out.println(medicine.getMedicineName());
                medicine.setRequestAddStock();
            }
    }

    /**
     * Updates the prescription status of a specified appointment outcome for a given patient.
     * If the prescription is in "Pending" status, it is marked as "Dispensed" and the inventory is updated.
     * Displays error messages if:
     * - The medicine has already been dispensed.
     * - The appointment outcome is not found in the system.
     * - The current stock of the medicine is insufficient for dispensing.
     *
     * @param appointmentOutcomeID the ID of the appointment outcome to be updated
     * @param patientID            the ID of the patient associated with the appointment outcome
     */
    public static void updatePrescriptionStatus(String appointmentOutcomeID, String patientID) {
        if (DataBase.getUsers().containsKey(patientID) &&
                ((Patient) DataBase.getUsers().get(patientID)).getMedicalRecord().getAppointmentOutcomes().containsKey(appointmentOutcomeID)) {
            AppointmentOutcome appointmentOutcome = ((Patient) DataBase.getUsers().get(patientID)).getMedicalRecord().getAppointmentOutcomes().get(appointmentOutcomeID);
            if (appointmentOutcome.getMedicationStatus() == MedicationStatus.PENDING) {
                if (InventoryManager.dispenseMedicine(appointmentOutcome.getMedicine())) {
                    appointmentOutcome.setMedicationStatus(MedicationStatus.DISPENSED);
                    System.out.println("Update Successfully\n");
                }
                else
                    System.out.println("The current stock for " + appointmentOutcome.getMedicine() + " is not enough to dispense\n");
            } else System.out.println("The medicine for this appointment had dispensed\n");
        } else System.out.println("This appointment outcome is not recorded in the system\n");
    }
}
