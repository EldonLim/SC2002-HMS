package model;

import using.BloodType;
import using.Gender;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {

    private final Patient patient; // Composition with patient
    private final String dateOfVisit;
    private final String patientID;
    private final String name;
    private final String dateOfBirth;
    private final Gender gender;
    private String phoneNo;
    private String emailAddress;
    private final BloodType bloodType;

    private List<String> diagnosis;
    private List<String> treatments;

    public MedicalRecord(Patient patient, List<String> diagnosis, List<String> treatments) {
        this.dateOfVisit = AppointmentOutcome.getDate();
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
    }

    public MedicalRecord(Patient patient) {
        this.patient = patient;
        this.patientID = patient.getID();
        this.name = patient.getName();
        this.dateOfBirth = patient.getDateOfBirth();
        this.gender = patient.getGender();
        this.phoneNo = patient.getPhoneNo();
        this.emailAddress = patient.getEmailAddress();
        this.bloodType = patient.getBloodType();
        diagnosis = null;
        treatments = null;
    }

    public String getDateOfVisit() {
        return dateOfVisit;
    }

    public Patient getPatient() {
        return patient;
    }

    public List<String> getDiagnosis() {
        return new ArrayList<>(diagnosis);
    }

    public String getPatientID() {
        return patientID;
    }

    public List<String> getTreatments() {
        return new ArrayList<>(treatments);
    }

    public Gender getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void addDiagnosis(String diagnosis_) {
        if (diagnosis == null)
            diagnosis = new ArrayList<>();
        diagnosis.add(diagnosis_);
    }

    public void addTreatment(String treatment) {
        if (treatments == null)
            treatments = new ArrayList<>();
        treatments.add(treatment);
    }

}