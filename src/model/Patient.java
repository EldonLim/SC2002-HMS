package model;

import using.BloodType;
import using.Gender;
import using.Role;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User {

    private final String dateOfBirth;
    private final BloodType bloodType;
    private String phoneNo;
    private String emailAddress;
    private final MedicalRecord medicalRecord; // Composition
    private final List<Appointment> appointments;

    public Patient(String name, String id, String password, Role role, Gender gender, BloodType bloodType, String phoneNo, String emailAddress, String dateOfBirth, List<String> treatments, List<String> diagnosis) {
        super(name, id, password, role, gender);
        this.bloodType = bloodType;
        this.phoneNo = phoneNo;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
        appointments = new ArrayList<>();
        medicalRecord = new MedicalRecord(this, diagnosis, treatments);
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
}
