package model;

import using.Gender;
import using.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a doctor with personal and professional details such as schedule, list of patients,
 * and scheduled appointments.
 * 
 * @author Chew En Zee
 * @version 4.5
 * @since 2024-10-27
 */
public class Doctor extends Staff {

    /**
     * The schedule of the doctor, containing available and booked time slots.
     */
    private Schedule schedule;

    /**
     * The list of patients assigned to the doctor.
     */
    private final List<Patient> patientList;

    /**
     * The list of appointments scheduled with the doctor.
     */
    private final List<Appointment> appointments;

    /**
     * Constructs a new Doctor with the specified details.
     *
     * @param name     the doctor's name
     * @param id       the doctor's unique identifier
     * @param password the doctor's password
     * @param role     the role of the doctor within the organization
     * @param gender   the gender of the doctor
     * @param age      the age of the doctor
     */
    public Doctor(String name, String id, String password, Role role, Gender gender, int age) {
        super(name, id, password, gender, role, age);
        patientList = new ArrayList<>();
        appointments = new ArrayList<>();
        schedule = new Schedule(this);
    }

    /**
     * Gets the doctor's schedule.
     *
     * @return the doctor's schedule
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * Gets the list of patients assigned to the doctor.
     *
     * @return the list of patients
     */
    public List<Patient> getPatientList() {
        return patientList;
    }

    /**
     * Adds a patient to the doctor's patient list.
     *
     * @param patient the patient to add
     */
    public void addPatient(Patient patient) {
        patientList.add(patient);
    }

    /**
     * Removes a patient from the doctor's patient list.
     *
     * @param patient the patient to remove
     */
    public void removePatient(Patient patient) {
        patientList.remove(patient);
    }

    /**
     * Gets the list of appointments scheduled with the doctor.
     *
     * @return the list of appointments
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Adds an appointment to the doctor's appointment list.
     *
     * @param appointment the appointment to add
     */
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * Removes an appointment from the doctor's appointment list.
     *
     * @param appointment the appointment to remove
     */
    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }
}
