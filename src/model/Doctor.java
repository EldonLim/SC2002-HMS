package model;
import using.*;

public class Doctor extends Staff{

    private Schedule schedule;

    public Doctor(String name, String id, String password, Role role, Gender gender, int age) {
        super(name, id, password, gender, role, age);
    }

    public Schedule getSchedule() { return schedule; }
    public void setSchedule(Schedule schedule) { this.schedule = schedule; }
}
