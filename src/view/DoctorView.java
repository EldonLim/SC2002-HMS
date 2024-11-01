package view;

import controller.AppointmentManager;
import controller.DoctorManager;
import database.DataBase;
import helper.Helper;
import model.Appointment;
import model.Doctor;
import model.Patient;
import using.AppointmentStatus;
import using.Role;

import java.util.List;

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
                    handleUpdatePatientMedicalRecord();
                    break;

                case 3:
                    DoctorManager.viewPersonalSchedule(DataBase.getCurrUserID());
                    break;
                case 4:
                    handleSetAvailability();
                    break;
                case 5:
                    handleAcceptDeclineAppointment();
                    break;
                case 6:
                    handleViewUpComingAppointment();
                    break;
                case 7:
                case 8:
            }

            Helper.pauseApplication();

        } while (choice != 8);

    }

    @Override
    public void viewTitle() { System.out.println("Doctor Menu"); }
    public static void handlePatientViewMedicalRecord() { DoctorManager.viewMedicalRecord(DataBase.getCurrUserID()); }

    public static void handleSetAvailability() {
        System.out.println("SET UNAVAILABLE SLOT");
        DoctorManager.setAvailability((Doctor) DataBase.getUsers().get(DataBase.getCurrUserID()));
    }
    public static void handleAcceptDeclineAppointment() { DoctorManager.handleAppointmentRequest((Doctor) DataBase.getUsers().get(DataBase.getCurrUserID())); }

    public static void handleUpdatePatientMedicalRecord() {
        List <Patient> patients = DoctorManager.getAllPatientUnderCare((Doctor) DataBase.getUsers().get(DataBase.getCurrUserID()));

        if (patients.isEmpty())
            System.out.println("No Patient Under Your Care");
        else {
            System.out.println("UPDATE PATIENT MEDICAL RECORD");
            System.out.println("Patients Under Your Care:");
            for (Patient patient : patients)
                System.out.println(patient.getName() + " (" + patient.getID() + ")");

            do {
                System.out.print("Please Enter Patient ID: ");
                String patientID = Helper.readString();

                if (!patients.contains((Patient) DataBase.getUsers().get(patientID))) {
                    System.out.println("\nNo such patient, please key in again\n");
                    continue;
                }

                System.out.print("Diagnosis: ");
                String diagnosis = Helper.readString();
                System.out.print("Treatment: ");
                String treatment = Helper.readString();

                DoctorManager.handleUpdateMedicalRecord((Patient) DataBase.getUsers().get(patientID), diagnosis, treatment);
                break;
            } while (true);
        }
        System.out.println();
    }

    public static void handleViewUpComingAppointment() {
        List<Appointment> upComingAppointments = DoctorManager.handleGetDoctorUpComingAppointment((Doctor) DataBase.getUsers().get(DataBase.getCurrUserID()));

        if (upComingAppointments.isEmpty())
            System.out.println("No Upcoming Appointments\n");

        System.out.println("Upcoming Appointments: ");
        for (Appointment appointment : upComingAppointments)
            if (appointment.getAppointmentStatus() == AppointmentStatus.CONFIRM)
                AppointmentManager.viewAppointmentDetail(appointment, Role.DOCTOR);
    }
}
