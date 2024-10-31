package view;

import controller.InventoryManager;
import helper.Helper;
import model.Medicine;
import java.util.ArrayList;

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


        } while (choice < 1 || choice > 5);

        ArrayList<Medicine> medicineList = new ArrayList<Medicine>();
        switch(choice){
            case 1: 
                // displayAppointmentOutcome();
                break;

            case 2: 
                // updatePrescriptionStatus();
                break;

            case 3: 
                medicineList = InventoryManager.getMedicationList();
                for (int i=0; i<medicineList.size(); i++) {
                    System.out.println("Medicine: " + medicineList.get(i).getMedicineName());
                    System.out.println("Quantity: " + medicineList.get(i).getStock());
                }
                break;

            case 4: 
                // submitReplenishmentReq();
                medicineList = InventoryManager.getMedicationList();
                String medicineName;
                boolean flag = false;
                for (int i=0; i<medicineList.size(); i++) {
                    medicineName = medicineList.get(i).getMedicineName();
                    if (InventoryManager.getMedicineStock(medicineName) < InventoryManager.getLowLevelThreshold(medicineName)) {
                        flag = true;
                        System.out.println(medicineName + " is low stock: " + InventoryManager.getMedicineStock(medicineName));
                        Character req;
                        do {
                            System.out.println("Submit replenishment request? (y/n):");
                            req = Helper.readChar();

                            if (req != 'y' && req != 'n') {
                                System.out.println("Invalid choice!");
                            }
                        } while (req != 'y' && req != 'n');
                        
                        if (req.equals('y')) {
                            System.out.println("Enter order quantity: ");
                            int quantity = Helper.readInt();
                            InventoryManager.orderMedicine(medicineName, quantity);
                            System.out.println("Replenishment request for " + medicineName + " is submitted successfully, pending approval from the administrator!");
                        }
                    }
                }

                if (!flag) {
                    System.out.println("All medicine stocks are above the low-stock threshold!");
                }

                break;

            case 5: 
                // logout();

        }
    }
    public void viewTitle() { System.out.println("Pharmacist Menu"); }
}
