package model;

import using.MedicationStatus;
import using.Service;

/**
 * Represents the outcome of an appointment, including details such as date, service provided, consultation notes,
 * medication prescribed, and medication status.
 *
 * @author Eldon Lim Kai Jie
 * @version 4.2
 * @since 2024-10-27
 */
public class AppointmentOutcome {

    /**
     * The date of the appointment outcome.
     */
    private String date;

    /**
     * The service provided during the appointment (e.g., consultation, treatment).
     */
    private final Service service;

    /**
     * Notes taken during the consultation, detailing observations or advice.
     */
    private final String consultationNotes;

    /**
     * A unique identifier for the appointment outcome, matching the associated appointment ID.
     */
    private final String appointmentOutcomeID;

    /**
     * The name of the medicine prescribed during the appointment.
     */
    private final String medicine;

    /**
     * The current medication status (e.g., PENDING, DISPENSED).
     */
    private MedicationStatus medicationStatus;

    /**
     * Constructs an AppointmentOutcome with the specified details.
     *
     * @param date               the date of the appointment outcome
     * @param service            the service provided during the appointment
     * @param consultationNotes  notes taken during the consultation
     * @param appointmentOutcomeID a unique identifier for the appointment outcome
     * @param medicine           the prescribed medicine
     * @param medicationStatus   the current medication status
     */
    public AppointmentOutcome(String date, Service service, String consultationNotes, String appointmentOutcomeID, String medicine, MedicationStatus medicationStatus) {
        this.date = date;
        this.service = service;
        this.consultationNotes = consultationNotes;
        this.appointmentOutcomeID = appointmentOutcomeID;
        this.medicationStatus = medicationStatus;
        this.medicine = medicine;
    }

    /**
     * Gets the date of the appointment outcome.
     *
     * @return the date of the appointment outcome
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the appointment outcome.
     *
     * @param date the new date of the appointment outcome
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the service provided during the appointment.
     *
     * @return the service provided during the appointment
     */
    public Service getService() {
        return service;
    }

    /**
     * Gets the consultation notes.
     *
     * @return the consultation notes
     */
    public String getConsultationNotes() {
        return consultationNotes;
    }

    /**
     * Gets the unique appointment outcome ID.
     *
     * @return the appointment outcome ID
     */
    public String getAppointmentOutcomeID() {
        return appointmentOutcomeID;
    }

    /**
     * Gets the prescribed medicine.
     *
     * @return the name of the prescribed medicine
     */
    public String getMedicine() {
        return medicine;
    }

    /**
     * Gets the current medication status.
     *
     * @return the medication status
     */
    public MedicationStatus getMedicationStatus() {
        return medicationStatus;
    }

    /**
     * Sets the medication status.
     *
     * @param medicationStatus the new medication status
     */
    public void setMedicationStatus(MedicationStatus medicationStatus) {
        this.medicationStatus = medicationStatus;
    }
}
