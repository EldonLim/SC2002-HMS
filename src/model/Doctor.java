package model;

import using.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.Map;

import controller.DocScheduleManager;
import controller.DocScheduleManager.Availability;

import java.time.LocalDateTime;

public class Doctor extends Staff {

    private ArrayList<Patient> patientList;
    private HashMap<LocalDateTime, DocScheduleManager.Availability> schedule;

    public Doctor(String name, String id, String password, Role role, Gender gender, int age,
            ArrayList<Patient> patientList) {
        super(name, id, password, gender, role, age);
        this.patientList = patientList;
        this.schedule = new HashMap<LocalDateTime, DocScheduleManager.Availability>();
    }

    public HashMap<LocalDateTime, DocScheduleManager.Availability> getSchedule() {
        return this.schedule;
    }

    public void setSchedule(HashMap<LocalDateTime, DocScheduleManager.Availability> schedule) {
        this.schedule = schedule;
    }

    public String serealizeSchedule() {
        StringBuilder sb = new StringBuilder();
        LocalDate currentDate = null;

        for (Map.Entry<LocalDateTime, Availability> entry : schedule.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .toList()) {

            LocalDateTime dateTime = entry.getKey();

            if (currentDate == null || !currentDate.equals(dateTime.toLocalDate())) {
                currentDate = dateTime.toLocalDate();
                sb.append("\n=== ").append(currentDate).append(" ===\n");
            }

            sb.append(dateTime.toLocalTime())
                    .append(" - ")
                    .append(entry.getValue())
                    .append("\n");
        }

        return sb.toString();
    }

}
