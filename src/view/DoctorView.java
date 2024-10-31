package view;

import controller.AppointmentManager;
import controller.DoctorManager;
import database.DataBase;
import helper.Helper;
import model.Doctor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;






public class DoctorView implements View{

    public DoctorView() {}

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

    public void handleView(Doctor doctor) {
        int choice;

        do {
            this.viewTitle();
            this.printViewMenu();
            choice = Helper.readInt();
            System.out.println();

        } while (choice <1 || choice >8);

        String response;
        switch (choice) {
            case 1:
                handlePatientViewMedicalRecord(doctor);
                break;
            case 2:
                break;
            case 3:
                System.out.println("Personal Schedule: ");
                DoctorManager.viewPersonalSchedule(doctor.getID());
                break;
            case 4:
                // DoctorScheduleManager.setDoctorAvailability(doctor);
                // // TO-DO: add a loop for a doctor to set its availability
                // String schedule1 = DoctorScheduleManager.serealizeSchedule(doctor.getSchedule());
                // System.out.println("Updated Schedule: ");
                // System.out.println(schedule1);
                break;
            case 5:
                // String appointmentSlots;
                // appointmentSlots = AppointmentManager.getAppointmentSlots(doctor);
                // System.out.println("All Appointment Requests: ");
                // System.out.println(appointmentSlots);
                // DoctorScheduleManager.setDoctorAvailability(doctor);
                // System.out.println("Appointment Request Updated.");

                // FOR SHOWING PURPOSE (CAN DELETE AFTERWARDS)
                // String schedule2 = DoctorScheduleManager.serealizeSchedule(doctor.getSchedule());
                // System.out.println("Updated Schedule: ");
                // System.out.println(schedule2);
                break;
            case 6:
                // String confirmedSlots;
                // confirmedSlots = AppointmentManager.getConfirmedSlots(doctor);
                // System.out.println("All Confirmed Appointments: ");
                // System.out.println(confirmedSlots);
                // TO-DO
                // link up each confirmed appointments with patient details.

                break;
            case 7:
                break;
            case 8:
                break;
        }
    }

    public void viewTitle() { System.out.println("Doctor Menu"); }

    public static void handlePatientViewMedicalRecord(Doctor doctor) {
        DoctorManager.viewMedicalRecord(doctor.getID());
    }
}
