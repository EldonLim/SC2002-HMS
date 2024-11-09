package view;

import controller.AppointmentManager;
import controller.DoctorManager;
import database.DataBase;
import helper.Helper;
import model.Appointment;
import model.Doctor;
import model.Medicine;
import model.Patient;
import using.AppointmentStatus;
import using.Role;
import using.Service;

import java.util.List;

/**
 * Represents the doctor's view in the Hospital Management System (HMS).
 * This class implements {@link View} interface.
 * Provides methods for managing appointments, viewing and updating patient records, and setting availability.
 * 
 * @author Chew En Zee
 * @version 12.8
 * @since 2024-10-27
 */
public class DoctorView implements View {

    /**
     * Constructs a new DoctorView instance.
     */
    public DoctorView() {
    }

    /**
     * Displays and handles the option to view the medical records of patients under the doctor's care.
     */
    public static void handlePatientViewMedicalRecord() {
        System.out.println("VIEW PATIENTS MEDICAL RECORD");
        DoctorManager.viewMedicalRecord(DataBase.getCurrentUserID());
    }

    /**
     * Allows the doctor to set unavailable time slots for appointments.
     */
    public static void handleSetAvailability() {
        System.out.println("SET UNAVAILABLE SLOT");
        DoctorManager.setAvailability((Doctor) DataBase.getUsers().get(DataBase.getCurrentUserID()));
    }

    /**
     * Allows the doctor to accept or decline appointment requests.
     */
    public static void handleAcceptDeclineAppointment() {
        System.out.println("ACCEPT/DECLINE APPOINTMENT");
        DoctorManager.handleAppointmentRequest((Doctor) DataBase.getUsers().get(DataBase.getCurrentUserID()));
    }

    /**
     * Handles updating the medical records of patients under the doctor's care.
     * Prompts the doctor for patient details and new diagnosis and treatment information.
     * If no patients are under the doctor's care, displays an appropriate message.
     */
    public static void handleUpdatePatientMedicalRecord() {
        System.out.println("UPDATE PATIENT MEDICAL RECORD");
        List<Patient> patients = DoctorManager.getAllPatientUnderCare((Doctor) DataBase.getUsers().get(DataBase.getCurrentUserID()));

        if (patients.isEmpty()) {
            System.out.println("No Patient Under Your Care");
        } else {
            System.out.println("Patients Under Your Care:");
            for (Patient patient : patients) {
                System.out.println(patient.getName() + " (" + patient.getID() + ")");
            }

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

    /**
     * Displays a list of upcoming appointments that have been confirmed.
     * If no confirmed appointments exist, displays an appropriate message.
     */
    public static void handleViewUpComingAppointment() {
        List<Appointment> upComingAppointments = DoctorManager.handleGetDoctorUpComingAppointment((Doctor) DataBase.getUsers().get(DataBase.getCurrentUserID()));
        boolean confirmedAppointment = upComingAppointments.stream().anyMatch(appointment -> appointment.getAppointmentStatus() == AppointmentStatus.CONFIRM);

        if (!confirmedAppointment) {
            System.out.println("No Upcoming Appointments");
            return;
        }

        System.out.println("VIEW UPCOMING APPOINTMENTS");
        for (Appointment appointment : upComingAppointments) {
            if (appointment.getAppointmentStatus() == AppointmentStatus.CONFIRM) {
                AppointmentManager.viewAppointmentDetail(appointment, Role.DOCTOR);
            }
        }
    }

    /**
     * Allows the doctor to record outcomes for completed appointments.
     * Prompts the doctor for appointment details, services provided, and consultation notes.
     * Validates appointment IDs before allowing updates.
     */
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

        Service service;
        do {
            System.out.print("Type of Services (X-Ray, Blood Test, Consultation or Other): ");
            service = Service.fromString(Helper.readString().trim());
            if (service == null) {
                System.out.println("Invalid service type. Please try again.");
            }
        } while (service == null);

        String medicine;
        while (true) {
            System.out.print("Medicine: ");
            medicine = Helper.readString();

            if (DataBase.getMedicines().containsKey(medicine)) {
                break;
            }
            System.out.println("No such medicine in the inventory");
        }

        System.out.print("Consultation Notes: ");
        String consultationNotes = Helper.readString();

        AppointmentManager.recordAppointmentOutcome(appointmentID, service, consultationNotes, medicine);

        System.out.println("Record Updated Successfully");
    }

    /**
     * Prints the menu options available to the doctor.
     */
    @Override
    public void printViewMenu() {
        System.out.println("1. View Patient Medical Record");
        System.out.println("2. Update Patient Medical Record");
        System.out.println("3. View Personal Schedule");
        System.out.println("4. Set Availability for Appointments");
        System.out.println("5. Accept or Decline Appointment Requests");
        System.out.println("6. View Upcoming Appointments");
        System.out.println("7. Record Appointment Outcome");
        System.out.println("8. Logout");
    }

    /**
     * Handles the main loop for the doctor's view.
     * Executes the selected option and validates the choice entered by the doctor.
     */
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

    /**
     * Prints the title of the doctor menu.
     */
    @Override
    public void viewTitle() {
        System.out.println("Doctor Menu");
    }
}
