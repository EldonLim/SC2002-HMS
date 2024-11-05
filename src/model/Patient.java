package model;
import using.*;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User{

    private String dateOfBirth;
    private BloodType bloodType;
    private String phoneNo;
    private String emailAddress;
    private MedicalRecord medicalRecord; // Composition
    private List<Appointment> appointments;

    public Patient(String name, String id, String password, Role role, Gender gender, BloodType bloodType, String phoneNo, String emailAddress, String dateOfBirth, List<String> treatments, List<String> diagnosis) {
        super(name, id, password, role, gender);
        this.bloodType = bloodType;
        this.phoneNo = phoneNo;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
        appointments = new ArrayList<>();
        medicalRecord = new MedicalRecord(this, diagnosis, treatments);
    }

    public String getDateOfBirth() { return dateOfBirth; }
    public String getPhoneNo() { return phoneNo; }
    public String getEmailAddress() { return emailAddress; }
    public BloodType getBloodType() { return bloodType; }

    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public MedicalRecord getMedicalRecord() { return medicalRecord; }

    public void addAppointment(Appointment appointment) { appointments.add(appointment); }

    public List<Appointment> getAppointments() { return appointments; }
}
