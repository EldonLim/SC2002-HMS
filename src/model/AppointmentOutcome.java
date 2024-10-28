package model;

import using.Service;

public class AppointmentOutcome {

    private String date;
    private Service service;
    private String medicine;        // added medicine
    private boolean medicine_prescribed;        // added medicine_prescribed
    private String consultationNotes;
    private String appointmentOutcomeID; //Should be the same with appointmentOutcomeID

    public AppointmentOutcome(String date, Service service, String medicine, String consultationNotes, String appointmentOutcomeID) {
        this.date = date;
        this.service = service;
        this.medicine = medicine;
        this.medicine_prescribed = false;       // default is false
        this.consultationNotes = consultationNotes;
        this.appointmentOutcomeID = appointmentOutcomeID;
    }

    public String getDate() { return date; }
    public Service getService() { return service; }
    public String getMedicine() { return medicine; }        // added getMedicine
    public boolean isMedicinePrescribed(){ return medicine_prescribed; }    // if prescribed return true
    public String getConsultationNotes() { return consultationNotes; }
    public String getAppointmentOutcomeID() { return appointmentOutcomeID;}

    public void setDate(String date) { this.date = date; }
    public void setService(Service service) { this.service = service; }
    public void setMedicine(String medicine) { this.medicine = medicine; }      // added setMedicine
    public void setIsMedicinePrescribed(boolean medicine_prescribed) {this.medicine_prescribed = medicine_prescribed; }
    // to set status of medicine_prescribed
    public void setConsultationNotes(String consultationNotes) { this.consultationNotes = consultationNotes; }
    public void setAppointmentOutcomeID(String appointmentOutcomeID) { this.appointmentOutcomeID = appointmentOutcomeID; }

}
