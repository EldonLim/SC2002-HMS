package src2.controller;

import src2.database.DataBase;
import src2.model.AppointmentOutcome;
import src2.model.Medicine;
import src2.model.Patient;
import src2.using.MedicationStatus;

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
     * Displays appropriate messages if the medicine has already been dispensed or if the appointment outcome
     * is not found.
     *
     * @param appointmentOutcomeID the ID of the appointment outcome to be updated
     * @param patientID            the ID of the patient associated with the appointment outcome
     */
    public static void updatePrescriptionStatus(String appointmentOutcomeID, String patientID) {
        if (DataBase.getUsers().containsKey(patientID) &&
                ((Patient) DataBase.getUsers().get(patientID)).getMedicalRecord().getAppointmentOutcomes().containsKey(appointmentOutcomeID)) {
            AppointmentOutcome appointmentOutcome = ((Patient) DataBase.getUsers().get(patientID)).getMedicalRecord().getAppointmentOutcomes().get(appointmentOutcomeID);
            if (appointmentOutcome.getMedicationStatus() == MedicationStatus.PENDING) {
                appointmentOutcome.setMedicationStatus(MedicationStatus.DISPENSED);
                InventoryManager.dispendMedicine(appointmentOutcome.getMedicine());
                System.out.println("Updated Successfully\n");
            } else System.out.println("The medicine for this appointment had dispensed\n");
        } else System.out.println("This appointment outcome is not recorded in the system\n");
    }
}
