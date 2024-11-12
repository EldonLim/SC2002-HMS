package controller;

import database.DataBase;
import helper.Helper;
import model.Appointment;
import model.Doctor;
import model.Patient;
import using.Availability;

import java.util.List;

/**
 * The DoctorManager class provides methods for managing doctor-related operations, such as viewing and managing
 * availability, medical records, and appointments.
 *
 * @author Chew En Zee
 * @version 8.10
 * @since 2024-10-29
 */
public class DoctorManager {

    /**
     * Constructs a DoctorManager instance.
     */
    public DoctorManager() {
    }

    /**
     * Prints the available slots for all doctors.
     */
    public static void printAllAvailableSlots() {
        DataBase.getUsers().values().stream()
                .filter(user -> user instanceof Doctor)
                .map(user -> (Doctor) user)
                .forEach(doctor -> ScheduleManager.printDoctorSchedule(doctor.getID()));
    }

    /**
     * Views the medical records of patients under the care of a specified doctor.
     *
     * @param doctorID the ID of the doctor whose patient records will be viewed
     */
    public static void viewMedicalRecord(String doctorID) {
        Doctor doctor = (Doctor) DataBase.getUsers().get(doctorID);

        if (doctor.getPatientList().isEmpty()) {
            System.out.println("\nNo Patient Under Your Care");
            return;
        }
        for (Patient patient : doctor.getPatientList()) {
            MedicalRecordManager.printMedicalRecord(patient.getID());
            if (!patient.getMedicalRecord().getAppointmentOutcomes().isEmpty())
                patient.getMedicalRecord().getAppointmentOutcomes().values().forEach(AppointmentManager::printAppointmentOutcome);
        }
    }

    /**
     * Views the personal schedule of a specified doctor.
     *
     * @param doctorID the ID of the doctor whose schedule will be viewed
     */
    public static void viewPersonalSchedule(String doctorID) {
        ScheduleManager.printPersonalSchedule(doctorID);
    }

    /**
     * Sets the availability of a doctor's schedule by marking specific time slots as unavailable.
     *
     * @param doctor the doctor whose availability will be set
     */
    public static void setAvailability(Doctor doctor) {
        do {
            viewPersonalSchedule(doctor.getID());
            System.out.print("Enter the date: ");
            String date = Helper.readString();
            System.out.print("Enter the time: ");
            int timeSlot = Helper.readInt();

            if (!doctor.getSchedule().getWeeklySlots().containsKey(date) || !doctor.getSchedule().getWeeklySlots().get(date).containsKey(timeSlot)) {
                System.out.println("Invalid Date or Time Slot");
                Helper.pauseApplication();
                continue;
            }

            doctor.getSchedule().setAvailabilityForParticularDate_Time(date, timeSlot, Availability.NOT_AVAILABLE);

            System.out.print("Set more slot (y/n): ");
            char choice = Helper.readChar();

            if (choice == 'n') break;
        } while (true);
    }

    /**
     * Handles pending appointment requests for a doctor, allowing them to accept or decline each request.
     *
     * @param doctor the doctor handling the appointment requests
     */
    public static void handleAppointmentRequest(Doctor doctor) {
        AppointmentManager.handleDoctorAppointmentRequest(doctor);
    }

    /**
     * Adds a patient to a doctor's list of patients under care.
     *
     * @param doctor  the doctor who will care for the patient
     * @param patient the patient to be added under the doctor's care
     */
    public static void addPatientUnderCare(Doctor doctor, Patient patient) {
        doctor.addPatient(patient);
    }

    /**
     * Removes a patient from a doctor's list of patients under care.
     *
     * @param doctor  the doctor who will no longer care for the patient
     * @param patient the patient to be removed from the doctor's care
     */
    public static void removePatientUnderCare(Doctor doctor, Patient patient) {
        doctor.removePatient(patient);
    }

    /**
     * Retrieves a list of all patients under a doctor's care.
     *
     * @param doctor the doctor whose patients will be listed
     * @return a list of patients under the specified doctor's care
     */
    public static List<Patient> getAllPatientUnderCare(Doctor doctor) {
        return doctor.getPatientList();
    }

    /**
     * Updates a patient's medical record with new diagnosis and treatment information.
     *
     * @param patient   the patient whose medical record will be updated
     * @param diagnosis the diagnosis to add to the medical record
     * @param treatment the treatment to add to the medical record
     */
    public static void handleUpdateMedicalRecord(Patient patient, String diagnosis, String treatment) {
        MedicalRecordManager.updateMedicalRecord(patient, diagnosis, treatment);
    }

    /**
     * Retrieves a list of all upcoming appointments for a specified doctor.
     *
     * @param doctor the doctor whose upcoming appointments will be listed
     * @return a list of the doctor's upcoming appointments
     */
    public static List<Appointment> handleGetDoctorUpComingAppointment(Doctor doctor) {
        return AppointmentManager.getDoctorUpComingAppointments(doctor);
    }
}
