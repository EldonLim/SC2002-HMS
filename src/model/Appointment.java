package model;

import using.AppointmentStatus;

public class Appointment {

    private String date;
    private int timeSlot;
    private AppointmentStatus appointmentStatus;
    private String appointmentID; //PatientID|DATE|TIME
    private Patient patient;
    private Doctor doctor;

    public Appointment(String date, int timeSlot, Patient patient, Doctor doctor) {
        this.date = date;
        this.timeSlot = timeSlot;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentStatus = AppointmentStatus.PENDING;
        this.appointmentID = patient.getID() + date.replace("/", "") + Integer.toString(timeSlot);
    }

    public AppointmentStatus getAppointmentStatus() { return appointmentStatus; }
    public String getDate() { return date; }
    public int getTimeSlot() { return timeSlot; }
    public String getAppointmentID() { return appointmentID; }
    public Doctor getDoctor() { return doctor; }
    public Patient getPatient() { return patient; }

    public void setAppointmentID(String appointmentID) { this.appointmentID = appointmentID; }
    public void setDate(String date) { this.date = date; }
    public void setTimeSlot(int timeSlot) { this.timeSlot = timeSlot; }
    public void setAppointmentStatus(AppointmentStatus appointmentStatus) { this.appointmentStatus = appointmentStatus; }
}
