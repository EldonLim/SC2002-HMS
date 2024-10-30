package view;

import helper.Helper;
import controller.PharmacistManager;

// for testing purposes
// import model.Medicine;
// import database.DataBase;

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

            if (choice < 1 || choice > 5){
                System.out.printf("Invalid choice!\n\n");
                continue;
            }

            switch(choice){
                case 1: 
                    displayAppointmentOutcome();
                    break;

                case 2: 
                    updatePrescriptionStatus();
                    break;

                case 3: 
                    viewMedicineInventory();
                    break;

                case 4: 
                    submitReplenishmentReq();
                    break;

                case 5: 
                    logout();

            }
        
        } while (choice != 5);

    }

    public void viewTitle() { System.out.println("Pharmacist Menu"); }

    public void displayAppointmentOutcome(){};

    public void updatePrescriptionStatus(){};

    public void viewMedicineInventory(){
        PharmacistManager.viewInventory();
    }

    public void submitReplenishmentReq(){
        PharmacistManager.submitReplenishmentReq();

        // for testing purposes
        // String m = "Paracetamol";
        // String n = "Ibuprofen";

        // for (Medicine medicine : DataBase.Medicines.values()){
        //     if (medicine.getMedicineName().compareTo(m) == 0)
        //         medicine.setStock(120);

        //     if (medicine.getMedicineName().compareTo(n) == 0)
        //         medicine.setStock(10);
        // }
    }

    public void logout(){
        System.out.println("Logging out ...");
    }

}
