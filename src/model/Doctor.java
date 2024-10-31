package model;
import using.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.Map;
import java.util.List;


import java.time.LocalDateTime;

public class Doctor extends Staff{

    private Schedule schedule;
    private ArrayList<Patient> patientList;
    private List<Appointment> appointments;
    // private HashMap<LocalDateTime, DoctorScheduleManager.Availability> schedule;

    public Doctor(String name, String id, String password, Role role, Gender gender, int age) {
        super(name, id, password, gender, role, age);
        patientList = new ArrayList<>();
        appointments = new ArrayList<>();
    }

    public Schedule getSchedule() { return schedule; }
    public void setSchedule(Schedule schedule) { this.schedule = schedule; }
    public List<Patient> getPatientList() { return patientList; }

    public void addPatient(Patient patient) { patientList.add(patient); }
    public void removePatient(Patient patient) { patientList.remove(patient); }

    public void addAppointment(Appointment appointment) { appointments.add(appointment); }
    public void removeAppointment(Appointment appointment) { appointments.remove(appointment); }

}


