package model;

import using.AppointmentStatus;

/**
 * Represents an appointment scheduled between a patient and a doctor.
 * Each appointment has a unique ID, status, date, time slot, and may include an outcome.
 *
 * @author Chew En Zee
 * @version 4.6
 * @since 2024-10-27
 */
public class Appointment {

    /**
     * The date of the appointment.
     */
    private String date;

    /**
     * The time slot of the appointment (hour of the day).
     */
    private int timeSlot;

    /**
     * The current status of the appointment.
     */
    private AppointmentStatus appointmentStatus;

    /**
     * A unique identifier for the appointment, formatted as PatientID|DATE|TIME.
     */
    private String appointmentID;

    /**
     * The patient involved in the appointment.
     */
    private final Patient patient;

    /**
     * The doctor assigned to the appointment.
     */
    private final Doctor doctor;

    /**
     * The outcome of the appointment, if any.
     */
    private AppointmentOutcome appointmentOutcome;

    /**
     * Constructs a new Appointment with the specified date, time slot, patient, and doctor.
     * The appointment is initialized with a status of PENDING and a unique appointment ID.
     *
     * @param date     the date of the appointment
     * @param timeSlot the time slot of the appointment
     * @param patient  the patient involved in the appointment
     * @param doctor   the doctor involved in the appointment
     */
    public Appointment(String date, int timeSlot, Patient patient, Doctor doctor) {
        this.date = date;
        this.timeSlot = timeSlot;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentStatus = AppointmentStatus.PENDING;
        this.appointmentID = patient.getID() + date.replace("/", "") + (timeSlot < 10? "0" + timeSlot : timeSlot);
        appointmentOutcome = null;
    }

    /**
     * Constructs an Appointment with the specified ID, doctor, status, patient, date, and time slot.
     * This constructor is useful for initializing appointments with known IDs and statuses.
     *
     * @param appointmentID    the unique ID of the appointment
     * @param doctor           the doctor involved in the appointment
     * @param appointmentStatus the current status of the appointment
     * @param patient          the patient involved in the appointment
     * @param date             the date of the appointment
     * @param timeSlot         the time slot of the appointment
     */
    public Appointment(String appointmentID, Doctor doctor, AppointmentStatus appointmentStatus, Patient patient, String date, int timeSlot) {
        this.appointmentID = appointmentID;
        this.doctor = doctor;
        this.appointmentStatus = appointmentStatus;
        this.patient = patient;
        this.date = date;
        this.timeSlot = timeSlot;
        appointmentOutcome = null;
    }

    /**
     * Gets the status of the appointment.
     *
     * @return the current status of the appointment
     */
    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    /**
     * Sets the status of the appointment.
     *
     * @param appointmentStatus the new status for the appointment
     */
    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    /**
     * Gets the date of the appointment.
     *
     * @return the date of the appointment
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the appointment.
     *
     * @param date the new date for the appointment
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the time slot of the appointment.
     *
     * @return the time slot of the appointment
     */
    public int getTimeSlot() {
        return timeSlot;
    }

    /**
     * Sets the time slot of the appointment.
     *
     * @param timeSlot the new time slot for the appointment
     */
    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    /**
     * Gets the unique appointment ID.
     *
     * @return the unique ID of the appointment
     */
    public String getAppointmentID() {
        return appointmentID;
    }

    /**
     * Sets the appointment ID.
     *
     * @param appointmentID the new appointment ID
     */
    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Gets the doctor associated with the appointment.
     *
     * @return the doctor for this appointment
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Gets the patient associated with the appointment.
     *
     * @return the patient for this appointment
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Gets the appointment outcome.
     *
     * @return the outcome of the appointment
     */
    public AppointmentOutcome getAppointmentOutcome() {
        return appointmentOutcome;
    }

    /**
     * Sets the appointment outcome and updates the appointment status to COMPLETED.
     *
     * @param appointmentOutcome the outcome of the appointment
     */
    public void setAppointmentOutcome(AppointmentOutcome appointmentOutcome) {
        this.appointmentOutcome = appointmentOutcome;
        appointmentStatus = AppointmentStatus.COMPLETED;
    }
}
