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
    private String phoneNo;
    private String emailAddress;
    private final BloodType bloodType;

    private List<String> diagnosis;
    private List<String> treatments;

    //private List<AppointmentOutcome> appointmentOutcomes;
    private HashMap<String, AppointmentOutcome> appointmentOutcomes;

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
//        this.appointmentOutcomes = new ArrayList<>();
        this.appointmentOutcomes = new HashMap<>();
    }

    // First time reading the file
    public MedicalRecord(Patient patient) {
        this.patient = patient;
        this.patientID = patient.getID();
        this.name = patient.getName();
        this.dateOfBirth = patient.getDateOfBirth();
        this.gender = patient.getGender();
        this.phoneNo = patient.getPhoneNo();
        this.emailAddress = patient.getEmailAddress();
        this.bloodType = patient.getBloodType();
        this.diagnosis = new ArrayList<>();
        this.treatments = new ArrayList<>();
//        this.appointmentOutcomes = new ArrayList<>();
        this.appointmentOutcomes = new HashMap<>();
    }

    public Patient getPatient() { return patient; }
    public List<String> getDiagnosis() { return new ArrayList<>(diagnosis); }
    public String getPatientID() { return patientID; }
    public List<String> getTreatments() { return new ArrayList<>(treatments); }

    public Gender getGender() { return gender; }
    public String getName() { return name; }
    public BloodType getBloodType() { return bloodType; }
    public String getEmailAddress() { return emailAddress; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getPhoneNo() { return phoneNo; }

    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

    public void addDiagnosis(String diagnosis_) { diagnosis.add(diagnosis_); }
    public void addTreatment(String treatment) { treatments.add(treatment); }

//    public void addAppointmentOutcomes(AppointmentOutcome appointmentOutcome) { appointmentOutcomes.add(appointmentOutcome); };
//    public List<AppointmentOutcome> getAppointmentOutcomes() { return appointmentOutcomes; }
    public void addAppointmentOutcomes(AppointmentOutcome appointmentOutcome, String appointmentOutcomeID) { appointmentOutcomes.put(appointmentOutcomeID, appointmentOutcome); }
    public HashMap<String, AppointmentOutcome> getAppointmentOutcomes() { return appointmentOutcomes; }
}
