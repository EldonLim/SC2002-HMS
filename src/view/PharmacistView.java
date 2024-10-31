package view;

import controller.InventoryManager;
import controller.PatientManager;
import controller.PharmacistManager;
import database.DataBase;
import helper.Helper;
import model.Medicine;

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
            choice = Helper.readInt();
            System.out.println();

            switch (choice) {
                case 1:
                    this.displayAppointmentOutcome();
                    break;

                case 2:
                    this.updatePrescriptionStatus();
                    break;

                case 3:
                    this.viewMedicationInventory();
                    break;

                case 4:
                    this.submitReplenishmentRequest();
            }

        } while (choice != 5);
    }

    public void displayAppointmentOutcome() {}
    public void updatePrescriptionStatus() {}
    public void viewMedicationInventory() { PharmacistManager.viewInventory(); }

    public void submitReplenishmentRequest() {
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
            choice = (char) Helper.readChar();

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
                System.out.println(count + ". " + entry.getValue().getMedicineName());

    }

    public static boolean submittedRequest() {
        for (Medicine medicine : DataBase.getMedicines().values())
            if (medicine.getLowStockAlert() && !medicine.getRequestAddStock())
                return false;

        return true;
    }
    public void viewTitle() { System.out.println("Pharmacist Menu"); }
}
