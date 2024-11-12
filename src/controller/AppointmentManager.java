package controller;

import database.DataBase;
import helper.Helper;
import model.Appointment;
import model.AppointmentOutcome;
import model.Doctor;
import model.Patient;
import using.*;

import java.util.Iterator;
import java.util.List;

/**
 * The AppointmentManager class provides methods for scheduling, viewing, rescheduling, and canceling appointments,
 * as well as handling appointment requests and recording outcomes.
 *
 * @author Chin Linn Sheng
 * @version 12.11
 * @since 2024-10-31
 */
public class AppointmentManager {

    /**
     * Constructs an AppointmentManager instance.
     */
    public AppointmentManager() {
    }

    /**
     * Schedules an appointment for a patient with a specified doctor on a given date and time slot.
     *
     * @param doctor   the doctor for the appointment
     * @param date     the date of the appointment
     * @param timeSlot the time slot for the appointment
     * @return true if the appointment was successfully scheduled; false if the doctor is unavailable
     */
    public static boolean scheduleAppointment(Doctor doctor, String date, int timeSlot) {
        Availability doctor_availability = doctor.getSchedule().getWeeklySlots().get(date).get(timeSlot);

        if (doctor_availability == Availability.NOT_AVAILABLE || doctor_availability == Availability.BOOKED)
            return false;

        Patient patient = (Patient) DataBase.getUsers().get(DataBase.getCurrentUserID());
        Appointment appointment = new Appointment(date, timeSlot, patient, doctor);

        patient.addAppointment(appointment);
        doctor.addAppointment(appointment);
        return true;
    }

    /**
     * Displays all scheduled appointments for a given patient.
     *
     * @param patient the patient whose scheduled appointments will be displayed
     * @return true if there are no scheduled appointments; false otherwise
     */
    public static boolean viewPatientScheduledAppointments(Patient patient) {
        if (patient.getAppointments().isEmpty()) {
            System.out.println("No Scheduled Appointment");
            return true;
        }

        System.out.println("SCHEDULED APPOINTMENTS");
        for (Appointment appointment : patient.getAppointments())
            viewAppointmentDetail(appointment, Role.PATIENT);
        return false;
    }

    /**
     * Reschedules an appointment for a patient with a new date and time slot.
     * New appointment timing request sent to doctor for confirmation.
     *
     * @param patient       the patient requesting the reschedule
     * @param appointmentID the ID of the appointment to be rescheduled
     * @param date          the new date for the appointment
     * @param timeSlot      the new time slot for the appointment
     */
    public static void rescheduleAppointment(Patient patient, String appointmentID, String date, int timeSlot) {
        Appointment appointment = null;

        for (Appointment appointment_ : patient.getAppointments())
            if ((appointment_.getAppointmentID().equals(appointmentID)) && appointment_.getAppointmentStatus() != AppointmentStatus.COMPLETED) {
                appointment = appointment_;
                break;
            }

        String oldAppointmentSubStr = appointmentID.substring(5, 11);
        String oldAppointmentDate = oldAppointmentSubStr.substring(0, 2) + '/' + oldAppointmentSubStr.substring(2, 4) + '/' + oldAppointmentSubStr.substring(4);

        appointment.getDoctor().getSchedule().setAvailabilityForParticularDate_Time(oldAppointmentDate, Integer.parseInt(appointmentID.substring(11)), Availability.AVAILABLE);
        appointment.setAppointmentID(patient.getID() + date.replace("/", "") + timeSlot);
        appointment.setDate(date);
        appointment.setTimeSlot(timeSlot);
        appointment.setAppointmentStatus(AppointmentStatus.PENDING);

    }

    /**
     * Displays the details of a given appointment based on the user's role.
     *
     * @param appointment the appointment to display
     * @param role        the role of the viewer (Administrator, Doctor, or Patient)
     */
    public static void viewAppointmentDetail(Appointment appointment, Role role) {
        System.out.println("Appointment ID: " + appointment.getAppointmentID());
        System.out.println("Date: " + appointment.getDate());
        System.out.printf("Time Slot: %2d:00 - %2d:00\n", appointment.getTimeSlot(), appointment.getTimeSlot() + 1);

        if (role == Role.ADMINISTRATOR) {
            System.out.println("Doctor ID: " + appointment.getDoctor().getID());
            System.out.println("Patient ID: " + appointment.getPatient().getID());
            System.out.println("Appointment Status: " + appointment.getAppointmentStatus().getLabel());
            if (appointment.getAppointmentOutcome() != null && appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED) {
                System.out.println("Service: " + appointment.getAppointmentOutcome().getService().getLabel());
                System.out.println("Medicine: " + appointment.getAppointmentOutcome().getMedicine());
                System.out.println("Medication Status: " + appointment.getAppointmentOutcome().getMedicationStatus().getLabel());
                System.out.println("Consultation Note: " + appointment.getAppointmentOutcome().getConsultationNotes());
            }
        } else if (role == Role.DOCTOR) System.out.println("Patient: " + appointment.getPatient().getName());
        else if (role == Role.PATIENT) {
            System.out.println("Doctor: " + appointment.getDoctor().getName());
            System.out.println("Appointment Status: " + appointment.getAppointmentStatus().getLabel());
        }
        Helper.pauseApplication();
    }

    /**
     * Cancels an appointment for a given patient by appointment ID.
     *
     * @param patient       the patient whose appointment is to be canceled
     * @param appointmentID the ID of the appointment to be canceled
     */
    public static void cancelAppointment(Patient patient, String appointmentID) {
        Appointment appointment = null;

        for (Appointment appointment_ : patient.getAppointments())
            if (appointment_.getAppointmentID().equals(appointmentID) && appointment_.getAppointmentStatus() != AppointmentStatus.COMPLETED) {
                appointment = appointment_;
                break;
            }

        Doctor doctor = appointment.getDoctor();
        doctor.getSchedule().setAvailabilityForParticularDate_Time(appointment.getDate(), appointment.getTimeSlot(), Availability.AVAILABLE);
        if (doctor.getPatientList().contains(patient))
            DoctorManager.removePatientUnderCare(doctor, patient);

        doctor.getAppointments().remove(appointment);
        patient.getAppointments().remove(appointment);
    }

    /**
     * Displays all of a patient's upcoming appointments that are not canceled or completed.
     *
     * @param patient the patient whose upcoming appointments will be displayed
     */
    public static void cancel_viewPatientScheduledAppointments(Patient patient) {
        for (Appointment appointment : patient.getAppointments())
            if (appointment.getAppointmentStatus() != AppointmentStatus.CANCEL && appointment.getAppointmentStatus() != AppointmentStatus.COMPLETED) {
                System.out.println("Appointment ID: " + appointment.getAppointmentID());
                System.out.println("Date: " + appointment.getDate());
                System.out.printf("Time Slot: %2d:00 - %2d:00\n", appointment.getTimeSlot(), appointment.getTimeSlot() + 1);
                System.out.println("Doctor: " + appointment.getDoctor().getName());
                System.out.println("Appointment Status: " + appointment.getAppointmentStatus().getLabel());
                System.out.println();
            }
    }

    /**
     * Handles pending appointment requests for a doctor, allowing them to accept or cancel each appointment.
     *
     * @param doctor the doctor handling the appointment requests
     */
    public static void handleDoctorAppointmentRequest(Doctor doctor) {
        final boolean[] noPendingAppointment = {true};

        Iterator<Appointment> iterator = doctor.getAppointments().iterator();

        while (iterator.hasNext()) {
            Appointment appointment = iterator.next();

            if (appointment.getAppointmentStatus() == AppointmentStatus.PENDING) {
                viewAppointmentDetail(appointment, doctor.getRole());
                noPendingAppointment[0] = false;

                if (doctor.getSchedule().getWeeklySlots().get(appointment.getDate()).get(appointment.getTimeSlot()) == Availability.BOOKED) {
                    System.out.println("This timeslot is booked by another patient");
                    appointment.setAppointmentStatus(AppointmentStatus.CANCEL);
                    iterator.remove();
                } else {
                    System.out.print("Accept Appointment (y/n): ");
                    char choice = Helper.readChar();
                    if (choice == 'y') {
                        appointment.setAppointmentStatus(AppointmentStatus.CONFIRM);
                        doctor.getSchedule().getWeeklySlots().get(appointment.getDate()).put(appointment.getTimeSlot(), Availability.BOOKED);
                        DoctorManager.addPatientUnderCare(doctor, appointment.getPatient());
                    } else {
                        appointment.setAppointmentStatus(AppointmentStatus.CANCEL);
                        iterator.remove();
                    }
                }
            }
        }

        if (noPendingAppointment[0])
            System.out.println("\nNo Pending Appointment");
    }

    /**
     * Retrieves a list of all upcoming appointments for a doctor.
     *
     * @param doctor the doctor whose upcoming appointments will be retrieved
     * @return a list of the doctor's upcoming appointments
     */
    public static List<Appointment> getDoctorUpComingAppointments(Doctor doctor) {
        return doctor.getAppointments();
    }

    /**
     * Records the outcome of an appointment, including service details and consultation notes.
     *
     * @param appointmentID     the ID of the appointment for which to record the outcome
     * @param service           the service provided during the appointment
     * @param consultationNotes notes recorded during the consultation
     * @param medicineName      the name of the medicine prescribed
     */
    public static void recordAppointmentOutcome(String appointmentID, Service service, String consultationNotes, String medicineName) {
        Doctor doctor = (Doctor) DataBase.getUsers().get(DataBase.getCurrentUserID());
        Appointment appointment = doctor.getAppointments().stream().filter(appointment_ -> appointment_.getAppointmentID().equals(appointmentID)).findFirst().orElse(null);

        AppointmentOutcome appointmentOutcome = new AppointmentOutcome(appointment.getDate(), service, consultationNotes,
                appointmentID, medicineName, MedicationStatus.PENDING);
        appointment.setAppointmentOutcome(appointmentOutcome);

        // delete from doctor
        doctor.removeAppointment(appointment);
        appointment.getPatient().getMedicalRecord().addAppointmentOutcomes(appointmentOutcome, appointmentID);
    }

    /**
     * Prints the details of an appointment outcome.
     *
     * @param appointmentOutcome the outcome of the appointment to print
     */
    public static void printAppointmentOutcome(AppointmentOutcome appointmentOutcome) {
        System.out.println("Date: " + appointmentOutcome.getDate());
        System.out.println("Service: " + appointmentOutcome.getService());
        System.out.println("Medicine: " + appointmentOutcome.getMedicine());
        System.out.println("Consultation Note: " + appointmentOutcome.getConsultationNotes());
    }

}
