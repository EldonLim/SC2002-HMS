package view;

import controller.DoctorManager;
import database.DataBase;
import helper.Helper;

public class DoctorView implements View{

    public DoctorView() {}

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
    public void handleView() {
        int choice;

        do {
            this.viewTitle();
            this.printViewMenu();
            System.out.print("Please Enter Your Choice: ");
            choice = Helper.readInt();
            System.out.println();

            switch (choice) {
                case 1:
                    handlePatientViewMedicalRecord();
                    break;
                case 2:
                case 3:
                    DoctorManager.viewPersonalSchedule(DataBase.getCurrUserID());
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
            }
        } while (choice != 8);

    }

    @Override
    public void viewTitle() { System.out.println("Doctor Menu"); }

    public static void handlePatientViewMedicalRecord() {
        DoctorManager.viewMedicalRecord(DataBase.getCurrUserID());
    }
}
