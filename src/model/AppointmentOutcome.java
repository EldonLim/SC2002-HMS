package model;

import using.MedicationStatus;
import using.Service;

public class AppointmentOutcome {

    private String date;
    private final Service service;
    private final String consultationNotes;
    private final String appointmentOutcomeID; //Should be the same with appointmentOutcomeID
    private final String medicine;
    private MedicationStatus medicationStatus;

    public AppointmentOutcome(String date, Service service, String consultationNotes, String appointmentOutcomeID, String medicine, MedicationStatus medicationStatus) {
        this.date = date;
        this.service = service;
        this.consultationNotes = consultationNotes;
        this.appointmentOutcomeID = appointmentOutcomeID;
        this.medicationStatus = medicationStatus;
        this.medicine = medicine;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getMedicine() {
        return medicine;
    }

    public MedicationStatus getMedicationStatus() {
        return medicationStatus;
    }

    public void setMedicationStatus(MedicationStatus medicationStatus) {
        this.medicationStatus = medicationStatus;
    }
}
