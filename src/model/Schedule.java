package model;

import using.Availability;

import java.util.HashMap;

public class Schedule {

    private Doctor doctor;
    private String doctorID;
    private HashMap<Integer, Availability> slots;

    public static final int START_TIME = 9;
    public static final int END_TIME = 17;
    public static final int TOTAL_SLOTS = START_TIME - END_TIME;

    public Schedule() {
        slots = new HashMap<>();
        this.initializeSchedule();
    }

    public void initializeSchedule() {
        for (int i = START_TIME; i < END_TIME; i++)
            slots.put(i, Availability.AVAILABLE);
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        doctorID = doctor.getID();
    }

    public Doctor getDoctor() { return doctor; }

    public void setDoctorID(String doctorID) { this.doctorID = doctorID; }
    public String getDoctorID() { return doctorID; }

    public HashMap<Integer, Availability> getSlots() { return slots; }
}
