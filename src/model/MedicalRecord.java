package model;

import using.BloodType;
import using.Gender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a medical record for a patient, containing personal details, medical history, and appointments.
 *
 * @author Eldon Lim Kai Jie
 * @version 4.4
 * @since 2024-10-27
 */
public class MedicalRecord {

    /**
     * The patient associated with this medical record.
     */
    private final Patient patient;

    /**
     * The patient's unique ID.
     */
    private final String patientID;

    /**
     * The patient's name.
     */
    private final String name;

    /**
     * The patient's date of birth.
     */
    private final String dateOfBirth;

    /**
     * The patient's gender.
     */
    private final Gender gender;

    /**
     * The patient's blood type.
     */
    private final BloodType bloodType;

    /**
     * The patient's phone number.
     */
    private final String phoneNo;

    /**
     * The patient's email address.
     */
    private final String emailAddress;

    /**
     * A list of diagnoses associated with the patient.
     */
    private final List<String> diagnosis;

    /**
     * A list of treatments associated with the patient.
     */
    private final List<String> treatments;

    /**
     * A map of appointment outcomes, identified by their unique appointment outcome ID.
     */
    private final HashMap<String, AppointmentOutcome> appointmentOutcomes;

    /**
     * Constructs a new MedicalRecord with the specified patient, diagnoses, and treatments.
     *
     * @param patient    the patient associated with this medical record
     * @param diagnosis  the initial list of diagnoses
     * @param treatments the initial list of treatments
     */
    public MedicalRecord(Patient patient, List<String> diagnosis, List<String> treatments) {
        this.patient = patient;
        this.patientID = patient.getID();
        this.name = patient.getName();
        this.dateOfBirth = patient.getDateOfBirth();
        this.gender = patient.getGender();
        this.phoneNo = patient.getPhoneNo();
        this.emailAddress = patient.getEmailAddress();
        this.bloodType = patient.getBloodType();
        this.diagnosis = new ArrayList<>(diagnosis);
        this.treatments = new ArrayList<>(treatments);
        this.appointmentOutcomes = new HashMap<>();
    }

    /**
     * Gets the patient associated with this medical record.
     *
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Gets a list of diagnoses for the patient.
     *
     * @return a list of diagnoses
     */
    public List<String> getDiagnosis() {
        return new ArrayList<>(diagnosis);
    }

    /**
     * Gets a list of treatments for the patient.
     *
     * @return a list of treatments
     */
    public List<String> getTreatments() {
        return new ArrayList<>(treatments);
    }

    /**
     * Gets the gender of the patient.
     *
     * @return the patient's gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Gets the name of the patient.
     *
     * @return the patient's name
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a new diagnosis to the patient's medical record.
     *
     * @param diagnosis_ the diagnosis to add
     */
    public void addDiagnosis(String diagnosis_) {
        diagnosis.add(diagnosis_);
    }

    /**
     * Adds a new treatment to the patient's medical record.
     *
     * @param treatment the treatment to add
     */
    public void addTreatment(String treatment) {
        treatments.add(treatment);
    }

    /**
     * Adds an appointment outcome to the patient's medical record.
     *
     * @param appointmentOutcome    the appointment outcome to add
     * @param appointmentOutcomeID the unique ID of the appointment outcome
     */
    public void addAppointmentOutcomes(AppointmentOutcome appointmentOutcome, String appointmentOutcomeID) {
        appointmentOutcomes.put(appointmentOutcomeID, appointmentOutcome);
    }

    /**
     * Gets the map of appointment outcomes for the patient.
     *
     * @return a map of appointment outcomes by ID
     */
    public HashMap<String, AppointmentOutcome> getAppointmentOutcomes() {
        return appointmentOutcomes;
    }
}
