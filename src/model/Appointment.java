package model;

import using.AppointmentStatus;

public class Appointment {

    private String date;
    private int timeSlot;
    private AppointmentStatus appointmentStatus;
    private String appointmentID; //PatientID|DATE|TIME
    private final Patient patient;
    private final Doctor doctor;
    private AppointmentOutcome appointmentOutcome;

    public Appointment(String date, int timeSlot, Patient patient, Doctor doctor) {
        this.date = date;
        this.timeSlot = timeSlot;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentStatus = AppointmentStatus.PENDING;
        this.appointmentID = patient.getID() + date.replace("/", "") + timeSlot;
        appointmentOutcome = null;
    }

    public Appointment(String appointmentID, Doctor doctor, AppointmentStatus appointmentStatus, Patient patient, String date, int timeSlot) {
        this.appointmentID = appointmentID;
        this.doctor = doctor;
        this.appointmentStatus = appointmentStatus;
        this.patient = patient;
        this.date = date;
        this.timeSlot = timeSlot;
        appointmentOutcome = null;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public AppointmentOutcome getAppointmentOutcome() {
        return appointmentOutcome;
    }

    public void setAppointmentOutcome(AppointmentOutcome appointmentOutcome) {
        this.appointmentOutcome = appointmentOutcome;
        appointmentStatus = AppointmentStatus.COMPLETED;
    }
}
