package view;

import helper.Helper;

public class PatientView implements View{

    public PatientView() {}

    public void printViewMenu() {
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule an Appointment");
        System.out.println("5. Reschedule an Appointment");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. View Scheduled Appointments");
        System.out.println("8. View Past Appointment Outcome Records");
        System.out.println("9. Logout");
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

            }
        } while (choice != 9);

    }

    public void viewTitle() { System.out.println("Patient Menu"); }
}
