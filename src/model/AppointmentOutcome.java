package model;

import java.util.ArrayList;

import using.Service;

public class AppointmentOutcome {

    private String date;
    private ArrayList<Service> service;
    private String consultationNotes;
    private String appointmentOutcomeID; // Should be the same with appointmentOutcomeID

    public AppointmentOutcome(String date, String consultationNotes, String appointmentOutcomeID) {
        this.date = date;
        service = new ArrayList<Service>();
        this.consultationNotes = consultationNotes;
        this.appointmentOutcomeID = appointmentOutcomeID;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Service> getService() {
        return service;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public String getAppointmentOutcomeID() {
        return appointmentOutcomeID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setService(Service service) {
        this.service.add(service);
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    public void setAppointmentOutcomeID(String appointmentOutcomeID) {
        this.appointmentOutcomeID = appointmentOutcomeID;
    }

}
