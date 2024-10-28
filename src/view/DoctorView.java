package view;

import helper.Helper;

public class DoctorView implements View {

    public DoctorView() {
    }

    @Override
    public void printViewMenu() {
        System.out.println("1. View Patient Medical Record");
        System.out.println("2. Update Patient Medical Record");
        System.out.println("3. View Personal Schedule");
        System.out.println("4. Set Availability for Appointments");
        System.out.println("5. Accept or Decline Appointment Requests");
        System.out.println("6. View Upcomming Appointments");
        System.out.println("7. Record Appointment Outcome");
        System.out.println("8. Logout");
    }

    @Override
    public void viewTitle() {
        System.out.println("Doctor Menu");
    }

    @Override
    public void handleView() {
        int choice;

        do {
            this.viewTitle();
            this.printViewMenu();
            choice = Helper.readInt();
            System.out.println();

        } while (choice != 8);

    }
}
