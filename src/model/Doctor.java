package model;
import using.*;

import java.util.ArrayList;

public class Doctor extends Staff{

    private Schedule schedule;
    private ArrayList<Patient> patientList;

    public Doctor(String name, String id, String password, Role role, Gender gender, int age) {
        super(name, id, password, gender, role, age);
    }

    public Schedule getSchedule() { return schedule; }
    public void setSchedule(Schedule schedule) { this.schedule = schedule; }
    public ArrayList<Patient> getPatientList() { return patientList; }

}
