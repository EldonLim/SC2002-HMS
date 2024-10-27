package model;

import using.Service;

public class AppointmentOutcome {

    private String date;
    private Service service;
    private String consultationNotes;
    private String appointmentOutcomeID; //Should be the same with appointmentOutcomeID

    public AppointmentOutcome(String date, Service service, String consultationNotes, String appointmentOutcomeID) {
        this.date = date;
        this.service = service;
        this.consultationNotes = consultationNotes;
        this.appointmentOutcomeID = appointmentOutcomeID;
    }

    public String getDate() {
        return date;
    }

    public Service getService() {
        return service;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public String getAppointmentOutcomeID() {
        return appointmentOutcomeID;
    }

}
