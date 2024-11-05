package model;

import using.BloodType;
import using.Gender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MedicalRecord {

    private final Patient patient; // Composition with patient
    private final String patientID;
    private final String name;
    private final String dateOfBirth;
    private final Gender gender;
    private final BloodType bloodType;
    private final String phoneNo;
    private final String emailAddress;
    private final List<String> diagnosis;
    private final List<String> treatments;

    private final HashMap<String, AppointmentOutcome> appointmentOutcomes;

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

    public Patient getPatient() {
        return patient;
    }

    public List<String> getDiagnosis() {
        return new ArrayList<>(diagnosis);
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

    public void addDiagnosis(String diagnosis_) {
        diagnosis.add(diagnosis_);
    }

    public void addTreatment(String treatment) {
        treatments.add(treatment);
    }

    public void addAppointmentOutcomes(AppointmentOutcome appointmentOutcome, String appointmentOutcomeID) {
        appointmentOutcomes.put(appointmentOutcomeID, appointmentOutcome);
    }

    public HashMap<String, AppointmentOutcome> getAppointmentOutcomes() {
        return appointmentOutcomes;
    }
}
