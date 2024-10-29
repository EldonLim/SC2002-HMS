package model;

import java.util.HashMap;

public class Schedule {

    private Doctor doctor;
    private HashMap<String, Boolean> schedule;

    public static final int START_TIME = 9;
    public static final int END_TIME = 17;
    public static final int TOTAL_SLOTS = START_TIME - END_TIME;

    public Schedule() {
        schedule = new HashMap<>();
        this.initializeSchedule();
    }

    public void initializeSchedule() {
        for (int i = START_TIME; i < END_TIME; i++) {
            String timeSlot = String.format("%d:00", i);
            schedule.put(timeSlot, true);
        }
    }

}
