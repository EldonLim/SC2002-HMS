package view;

import helper.Helper;

public class AdminstratorView implements View{

    public AdminstratorView() {}

    @Override
    public void printViewMenu() {
        this.viewTitle();
        System.out.println("1. View and Manage Hospital Staff");
        System.out.println("2. View Appointment details");
        System.out.println("3. View and Manage Medication Inventory");
        System.out.println("4. Approve Replenishment Requests");
        System.out.println("5. Logout");
    }

    @Override
    public void handleView() {
        int choice;

        do {
            this.viewTitle();
            this.printViewMenu();
            choice = Helper.readInt();
            System.out.println();

        } while (choice != 5);
    }

    @Override
    public void viewTitle() {
        System.out.println("Adminstrator Menu");
    }
}
