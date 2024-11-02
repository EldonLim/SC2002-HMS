package controller;

import database.DataBase;
import model.AppointmentOutcome;
import model.Medicine;
import model.Patient;
import using.MedicationStatus;

public class PharmacistManager {

    public PharmacistManager() {}

    public static void viewInventory() {
        if (DataBase.getMedicines().isEmpty()) {
            System.out.println("No medicines record in the inventory");
            return;
        }
        InventoryManager.listInventory();
    }

    public static void submitRequest() {
        System.out.println("\nReplenishment Request Sent For:");
        for (Medicine medicine : DataBase.getMedicines().values())
            if (medicine.getLowStockAlert() && !medicine.getRequestAddStock()) {
                System.out.println(medicine.getMedicineName());
                medicine.setRequestAddStock();
            }
    }

    public static void updatePrescriptionStatus(String appointmentOutcomeID, String patientID) {
        if (DataBase.getUsers().containsKey(patientID) &&
                ((Patient) DataBase.getUsers().get(patientID)).getMedicalRecord().getAppointmentOutcomes().containsKey(appointmentOutcomeID)) {
            AppointmentOutcome appointmentOutcome = ((Patient) DataBase.getUsers().get(patientID)).getMedicalRecord().getAppointmentOutcomes().get(appointmentOutcomeID);

            if (appointmentOutcome.getMedicationStatus() == MedicationStatus.PENDING) {
                appointmentOutcome.setMedicationStatus(MedicationStatus.DISPENDED);
                InventoryManager.dispendMedicine(appointmentOutcome.getMedicine());
                System.out.println("Updated Successfully\n");
            }
            else System.out.println("The medicine for this appointment had dispended\n");
        }
        else System.out.println("This appointment outcome is not recorded in the system\n");
    }
}
