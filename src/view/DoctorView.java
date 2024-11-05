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

public class DoctorView implements View {

    public DoctorView() {
    }

    public static void handlePatientViewMedicalRecord() {
        System.out.println("VIEW PATIENTS MEDICAL RECORD");
        DoctorManager.viewMedicalRecord(DataBase.getCurrentUserID());
    }

    public static void handleSetAvailability() {
        System.out.println("SET UNAVAILABLE SLOT");
        DoctorManager.setAvailability((Doctor) DataBase.getUsers().get(DataBase.getCurrentUserID()));
    }

    public static void handleAcceptDeclineAppointment() {
        System.out.println("ACCEPT/DECLINE APPOINTMENT");
        DoctorManager.handleAppointmentRequest((Doctor) DataBase.getUsers().get(DataBase.getCurrentUserID()));
    }

    public static void handleUpdatePatientMedicalRecord() {
        System.out.println("UPDATE PATIENT MEDICAL RECORD");
        List<Patient> patients = DoctorManager.getAllPatientUnderCare((Doctor) DataBase.getUsers().get(DataBase.getCurrentUserID()));

        if (patients.isEmpty()) System.out.println("No Patient Under Your Care");
        else {
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
    }

    public static void handleViewUpComingAppointment() {
        List<Appointment> upComingAppointments = DoctorManager.handleGetDoctorUpComingAppointment((Doctor) DataBase.getUsers().get(DataBase.getCurrentUserID()));
        boolean confirmedAppointment = upComingAppointments.stream().anyMatch(appointment -> appointment.getAppointmentStatus() == AppointmentStatus.CONFIRM);

        if (!confirmedAppointment) {
            System.out.println("No Upcoming Appointments");
            return;
        }

        System.out.println("VIEW UPCOMMING APPOINTMENTS");
        for (Appointment appointment : upComingAppointments)
            if (appointment.getAppointmentStatus() == AppointmentStatus.CONFIRM)
                AppointmentManager.viewAppointmentDetail(appointment, Role.DOCTOR);
    }

    public static void handleRecordAppointmentOutcome() {
        System.out.println("RECORD APPOINTMENT OUTCOME");
        List<Appointment> upcomingConfirmedAppointments =
                DoctorManager.handleGetDoctorUpComingAppointment((Doctor) DataBase.getUsers().get(DataBase.getCurrentUserID()))
                        .stream()
                        .filter(appointment -> appointment.getAppointmentStatus() == AppointmentStatus.CONFIRM)
                        .toList();
        if (upcomingConfirmedAppointments.isEmpty()) {
            System.out.println("No Appointments");
            return;
        }

        System.out.println("List of Appointments: ");
        for (Appointment appointment : upcomingConfirmedAppointments) {
            System.out.println("Appointment ID: " + appointment.getAppointmentID());
            System.out.println("Patient Name: " + appointment.getPatient().getName());
            System.out.println("Date: " + appointment.getDate());
            System.out.printf("Time Slot: %2d:00 - %2d:00\n\n", appointment.getTimeSlot(), appointment.getTimeSlot() + 1);
        }

        String appointmentID;

        do {
            System.out.print("Please Enter the Appointment ID: ");
            appointmentID = Helper.readString();

            String appointmentID_ = appointmentID;
            boolean isValidAppointmentID = upcomingConfirmedAppointments.stream()
                    .anyMatch(appointment -> appointment.getAppointmentID().equals(appointmentID_));

            if (!isValidAppointmentID) {
                System.out.println("There is no appointment with this ID");
                System.out.println("Please Enter Again!!");
                continue;
            }
            break;
        } while (true);

        System.out.print("Type of Services (X-Ray, Blood Test, Consultation or Other): ");
        String service = Helper.readString();
        System.out.print("Medicine: ");
        String medicine = Helper.readString();
        System.out.print("Consultation Notes: ");
        String consultationNotes = Helper.readString();

        AppointmentManager.recordAppointmentOutcome(appointmentID, service, consultationNotes, medicine);

        System.out.println("Record Updated Successfully");
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
    public void handleView() {
        int choice;

        do {
            this.viewTitle();
            this.printViewMenu();
            System.out.print("Please Enter Your Choice: ");
            choice = Helper.readInt();

            while (choice < 1 || choice > 8) {
                System.out.println("\nInvalid choice. Please try again.");
                System.out.print("Please Enter your Choice: ");
                choice = Helper.readInt();
            }

            Helper.pauseApplication();

            switch (choice) {
                case 1 -> handlePatientViewMedicalRecord();
                case 2 -> handleUpdatePatientMedicalRecord();
                case 3 -> DoctorManager.viewPersonalSchedule(DataBase.getCurrentUserID());
                case 4 -> handleSetAvailability();
                case 5 -> handleAcceptDeclineAppointment();
                case 6 -> handleViewUpComingAppointment();
                case 7 -> handleRecordAppointmentOutcome();
                case 8 -> System.out.println("Thanks for Using HMS");
            }
            Helper.pauseApplication();
        } while (choice != 8);
    }

    @Override
    public void viewTitle() {
        System.out.println("Doctor Menu");
    }
}
