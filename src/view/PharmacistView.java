package view;

import helper.Helper;

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


        } while (choice != 5);
    }
    public void viewTitle() {
        System.out.println("Pharmacist Menu");
    }
}
