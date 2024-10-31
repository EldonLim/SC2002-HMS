package model;

public class Appointment {

    private String date;
    private int timeSlot;
    private boolean appointmentStatus = false;
    private String appointmentID; //PatientID|DATE|TIME
    private Patient patient;
    private Doctor doctor;

    public Appointment(String date, int timeSlot, Patient patient, Doctor doctor) {

    }

}
