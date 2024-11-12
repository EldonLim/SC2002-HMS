package model;

import using.BloodType;
import using.Gender;
import using.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a patient in the medical system, with personal information, 
 * contact details, medical records, and a list of appointments.
 * 
 * @author Chin Linn Sheng
 * @version 4.7
 * @since 2024-10-27
 */
public class Patient extends User {

    /**
     * The patient's date of birth.
     */
    private final String dateOfBirth;
    
    /**
     * The patient's blood type.
     */
    private final BloodType bloodType;
    
    /**
     * The patient's phone number.
     */
    private String phoneNo;
    
    /**
     * The patient's email address.
     */
    private String emailAddress;
    
    /**
     * The patient's medical record, containing diagnosis and treatment history.
     */
    private final MedicalRecord medicalRecord; // Composition
    
    /**
     * The list of appointments for the patient.
     */
    private final List<Appointment> appointments;

    /**
     * Constructs a Patient object with specified personal and contact details, 
     * as well as an initial diagnosis and treatment history.
     *
     * @param name         the patient's name
     * @param id           the patient's unique identifier
     * @param password     the patient's password
     * @param role         the role of the user (should be PATIENT)
     * @param gender       the patient's gender
     * @param bloodType    the patient's blood type
     * @param phoneNo      the patient's phone number
     * @param emailAddress the patient's email address
     * @param dateOfBirth  the patient's date of birth
     * @param treatments   initial treatments in the patient's medical record
     * @param diagnosis    initial diagnosis in the patient's medical record
     */
    public Patient(String name, String id, String password, Role role, Gender gender, BloodType bloodType, String phoneNo, String emailAddress, String dateOfBirth, List<String> treatments, List<String> diagnosis) {
        super(name, id, password, role, gender);
        this.bloodType = bloodType;
        this.phoneNo = phoneNo;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
        appointments = new ArrayList<>();
        medicalRecord = new MedicalRecord(this, diagnosis, treatments);
    }

    /**
     * Gets the patient's date of birth.
     *
     * @return the date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Gets the patient's phone number.
     *
     * @return the phone number
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * Sets the patient's phone number.
     *
     * @param phoneNo the new phone number
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Gets the patient's email address.
     *
     * @return the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the patient's email address.
     *
     * @param emailAddress the new email address
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Gets the patient's blood type.
     *
     * @return the blood type
     */
    public BloodType getBloodType() {
        return bloodType;
    }

    /**
     * Gets the patient's medical record.
     *
     * @return the medical record
     */
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    /**
     * Adds an appointment to the patient's list of appointments.
     *
     * @param appointment the appointment to add
     */
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * Gets the list of appointments for the patient.
     *
     * @return the list of appointments
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }
}
