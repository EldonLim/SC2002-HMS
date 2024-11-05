package model;

import using.Gender;
import using.Role;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends Staff {

    private Schedule schedule;
    private final List<Patient> patientList;
    private final List<Appointment> appointments;

    // For first time reading the file
    public Doctor(String name, String id, String password, Role role, Gender gender, int age) {
        super(name, id, password, gender, role, age);
        patientList = new ArrayList<>();
        appointments = new ArrayList<>();
        schedule = new Schedule(this);
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void addPatient(Patient patient) {
        patientList.add(patient);
    }

    public void removePatient(Patient patient) {
        patientList.remove(patient);
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }
}
